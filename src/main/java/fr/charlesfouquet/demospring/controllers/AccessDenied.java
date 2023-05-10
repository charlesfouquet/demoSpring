package fr.charlesfouquet.demospring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDenied {
    @GetMapping("/accessDenied")
    public String get(Model model){
        Categories.getCategoriesList(model);
        return "/accessDenied";
    }
}
