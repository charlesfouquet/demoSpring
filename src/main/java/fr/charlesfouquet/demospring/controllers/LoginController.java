package fr.charlesfouquet.demospring.controllers;

import fr.charlesfouquet.demospring.beans.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model) {
        Categories.getCategoriesList(model);
        return "loginPage";
    }
}
