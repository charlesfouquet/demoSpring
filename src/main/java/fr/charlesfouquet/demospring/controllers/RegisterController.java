package fr.charlesfouquet.demospring.controllers;

import fr.charlesfouquet.demospring.beans.User;
import fr.charlesfouquet.demospring.repository.RoleRepository;
import fr.charlesfouquet.demospring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        Categories.getCategoriesList(model);
        return ("/register");
    }

    @PostMapping("/register")
    public String register(@Validated User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Categories.getCategoriesList(model);
            return ("/register");
        } else if (userService.findByEmail(user.getEmail()) != null) {
            bindingResult.addError(new FieldError("user","email","Le mail existe deja !"));
            Categories.getCategoriesList(model);
            return ("/register");
        }
        userService.createUser(user);
        return ("redirect:/");
    }

}
