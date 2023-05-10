package fr.charlesfouquet.demospring.controllers;

import fr.charlesfouquet.demospring.beans.Article;
import fr.charlesfouquet.demospring.beans.Employee;
import fr.charlesfouquet.demospring.beans.User;
import fr.charlesfouquet.demospring.beans.UserLogin;
import fr.charlesfouquet.demospring.services.ArticleService;
import fr.charlesfouquet.demospring.services.EmployeeService;
import fr.charlesfouquet.demospring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

@Controller
public class AddArticle {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping("/articles/addArticle")
    public String get(Model model, Article article){
        Categories.getCategoriesList(model);
        return "/addArticle";
    }

    @GetMapping("/articles/addArticle/{id}")
    public String get(Model model, Article article, @PathVariable("id") int id){
        Categories.getCategoriesList(model);
        if (articleService.findByID(id).isPresent()) {
            article = articleService.findByID(id).get();
            UserLogin currentUserLogin = ((UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            if ((article.getUser().getEmail().equals(currentUserLogin.getUsername())) || (currentUserLogin.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))) {
                model.addAttribute("article", article);
            } else {
                return "redirect:/accessDenied";
            }
        }
        return "/addArticle";
    }

    @PostMapping("/articles/addArticle")
    public String post(@Valid @ModelAttribute("article") Article article, BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile multipartFile){
        if (bindingResult.hasErrors()) {
            Categories.getCategoriesList(model);
            return "/addArticle";
        } else {
            UserLogin userLogin = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userName = userLogin.getUsername();
            User user = userService.findByEmail(userName);
            article.setUser(user);
            if (!checkPhotoInputIfExists(user, article, multipartFile)) {
                bindingResult.addError(new FieldError("article","photo","Vous devez joindre une photo à votre article !"));
                Categories.getCategoriesList(model);
                return "/addArticle";
            }
            article.setDate(new Timestamp(new java.util.Date().getTime()));
            articleService.create(article);
            return "redirect:/articles";
        }
    }

    @PostMapping("/articles/addArticle/{id}")
    public String post(@Valid @ModelAttribute("article") Article article, BindingResult bindingResult, Model model, @PathVariable("id") int id, @RequestParam("image") MultipartFile multipartFile){
        UserLogin userLogin = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userLogin.getUsername();
        User user = userService.findByEmail(userName);
        article.setUser(user);
        article.setDate(new Timestamp(new java.util.Date().getTime()));
        checkPhotoInputIfExists(user, article, multipartFile);
        articleService.update(id, article);
        return "redirect:/articles";
    }

    public boolean checkPhotoInputIfExists(User user, Article article, MultipartFile multipartFile) {
        if ((user != null) && !multipartFile.isEmpty()) {
            String imgFilename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            article.setPhoto(imgFilename);
            try {
                articleService.uploadImage(new ClassPathResource("/src/main/resources/static/uploads").getPath(), imgFilename, multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}
