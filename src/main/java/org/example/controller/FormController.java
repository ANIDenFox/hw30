package org.example.controller;

import org.example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FormController {

    private static final Map<String, String> OPERATORS = new HashMap<>() {{
        put("067", "Kyivstar");
        put("068", "Kyivstar");
        put("096", "Kyivstar");
        put("097", "Kyivstar");
        put("098", "Kyivstar");
        put("050", "Vodafone Україна");
        put("066", "Vodafone Україна");
        put("095", "Vodafone Україна");
        put("099", "Vodafone Україна");
        put("063", "lifecell");
        put("073", "lifecell");
        put("093", "lifecell");
        put("091", "ТриМоб");
        put("092", "PEOPLEnet");
        put("089", "Інтертелеком");
        put("094", "Інтертелеком");
    }};

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(User user, Model model) {
        String phone = user.getPhone().replaceAll("\\D", "");
        String operator = getOperator(phone);
        model.addAttribute("user", user);
        model.addAttribute("operator", operator);
        return "result";
    }

    private String getOperator(String phone) {
        phone = phone.replaceAll("\\D", "");

        if (phone.startsWith("380") && phone.length() == 12) {
            String code = phone.substring(2, 5);
            return OPERATORS.getOrDefault(code, "Невідомий оператор");
        } else if (phone.startsWith("0") && phone.length() == 10) {
            String code = phone.substring(0, 3);
            return OPERATORS.getOrDefault(code, "Невідомий оператор");
        } else if (phone.startsWith("+") && phone.length() == 13 && phone.startsWith("+380")) {
            String code = phone.substring(4, 7);
            return OPERATORS.getOrDefault(code, "Невідомий оператор");
        }

        return "Невірний формат номера";
    }
}