package com.tacos.tacocloud.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.tacos.tacocloud.domain.Order;
import com.tacos.tacocloud.domain.User;
import com.tacos.tacocloud.data.IngredientRepository;
import com.tacos.tacocloud.data.TacoRepository;
import com.tacos.tacocloud.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.tacos.tacocloud.domain.Ingredient;
import com.tacos.tacocloud.domain.Ingredient.Type;
import com.tacos.tacocloud.domain.Taco;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order") // 세션에 저장해둘 모델객체
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository tacoRepo;
    private UserRepository userRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo, UserRepository userRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
        this.userRepo = userRepo;
    }


    @GetMapping
    public String showDesignForm(Model model, Principal principle){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        //model.addAttribute("taco",new Taco());
        String username = principle.getName();
        User user = userRepo.findByUsername(username);
        model.addAttribute("user",user);

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

        return "redirect:/orders/current";  //현재 주문 페이지 orders/current 로 리다이렉트
    }
}

