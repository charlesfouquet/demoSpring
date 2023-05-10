package fr.charlesfouquet.demospring.controllers;

import org.springframework.ui.Model;

public class Categories {
    public static void getCategoriesList(Model model) {
        model.addAttribute("catTable", new String[]{"Employés", "Cadres", "Techniciens", "Intermittents"});
    }
}
