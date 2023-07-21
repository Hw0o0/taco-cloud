package com.tacos.tacocloud.web;

import com.tacos.tacocloud.domain.Order;
import com.tacos.tacocloud.domain.User;
import com.tacos.tacocloud.data.OrderRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/orders")
public class OrderController {
    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo){
        this.orderRepo=orderRepo;
    }
    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user, @ModelAttribute Order order){//Model model
        //model.addAttribute("order", new Order());
        if(order.getDeliveryName()==null){
            order.setDeliveryName(user.getFullname());
        }
        if(order.getDeliveryStreet()==null){
            order.setDeliveryStreet(user.getStreet());
        }
        if(order.getDeliveryCity()==null){
            order.setDeliveryCity(user.getCity());
        }
        if(order.getDeliveryState()==null){
            order.setDeliveryState(user.getState());
        }
        if(order.getDeliveryZip()==null){
            order.setDeliveryZip(user.getZip());
        }
        return "orderForm";
    }
    @PostMapping
    public String processOrder(Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return "orderForm";
        }
        order.setUser(user);

        // order 객체 저장 -> order 객체도 세션도 보존 되어야 한다
        orderRepo.save(order);

        // DB 에 저장 완료 되면 세션을 제거한다
        sessionStatus.setComplete();
        return "redirect:/";
    }
}