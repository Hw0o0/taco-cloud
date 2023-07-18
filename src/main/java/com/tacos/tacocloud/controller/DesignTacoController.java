package com.tacos.tacocloud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.tacos.tacocloud.Order;
import com.tacos.tacocloud.repository.IngredientRepository;
import com.tacos.tacocloud.repository.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.tacos.tacocloud.Ingredient;
import com.tacos.tacocloud.Ingredient.Type;
import com.tacos.tacocloud.Taco;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
    }


    @GetMapping
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("taco",new Taco());

        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x->x.getType().equals(type)).collect(Collectors.toList());
    }

    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order){
        if(errors.hasErrors()){
            return "design";
        }
        Taco saved = tacoRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }
}

