package fr.charlesfouquet.demospring.controllers;

import fr.charlesfouquet.demospring.beans.Article;
import fr.charlesfouquet.demospring.beans.Employee;
import fr.charlesfouquet.demospring.beans.User;
import fr.charlesfouquet.demospring.beans.UserLogin;
import fr.charlesfouquet.demospring.services.ArticleService;
import fr.charlesfouquet.demospring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class Articles {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping("/articles")
    public String get(Model model){
        UserLogin userLogin = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userLogin.getUsername();
        User user = userService.findByEmail(userName);
        model.addAttribute("currentUser", user);
        if (articleService.readDateDesc().size() > 0) {
            model.addAttribute("articlesList", articleService.readDateDesc());
        }
        Categories.getCategoriesList(model);
        return "/articles";
    }

    @GetMapping ("/articles/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") int id) {
        Optional<Article> articleFound = articleService.findByID(id);
        if (articleFound.isPresent()) {
            UserLogin currentUserLogin = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            if ((articleFound.get().getUser().getEmail().equals(currentUserLogin.getUsername())) || (currentUserLogin.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))) {
                articleService.delete(articleFound.get());
            } else {
                return "redirect:/accessDenied";
            }
        }
        return "redirect:/articles";
    }
}
