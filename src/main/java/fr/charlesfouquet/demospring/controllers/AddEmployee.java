package fr.charlesfouquet.demospring.controllers;

import fr.charlesfouquet.demospring.beans.Employee;
import fr.charlesfouquet.demospring.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AddEmployee {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/addEmployee")
    public String get(Model model, Employee employee){
        loadPage(model);
        return "/addEmployee";
    }

    @PostMapping("/addEmployee")
    public String post(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            loadPage(model);
            return "/addEmployee";
        } else {
            if (employeeService.create(employee)) {
                return "redirect:/addEmployee";
            } else {
                loadPage(model);
                bindingResult.addError(new FieldError("employee", "email", "Un compte existe déjà avec cet email"));
                return "/addEmployee";
            }
        }
    }

    @GetMapping ("/addEmployee/update/{id}")
    public String getEmployeeToUpdate(@PathVariable("id") int id, Model model) {
        Optional<Employee> employeeFound = employeeService.findByID(id);
        if (employeeFound.isPresent()) {
            model.addAttribute("employee", employeeFound.get());
        }
        loadPage(model);
        return "/addEmployee";
    }

    @PostMapping ("/addEmployee/update/{id}")
    public String updateEmployee(@PathVariable(value = "id") int id, @Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {
        employeeService.update(id, employee);
        return "redirect:/addEmployee";
    }

    @GetMapping ("/addEmployee/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") int id) {
        Optional<Employee> employeeFound = employeeService.findByID(id);
        if (employeeFound.isPresent()) {
            employeeService.delete(employeeFound.get());
        }
        return "redirect:/addEmployee";
    }

    public void getEmployeesList(Model model) {
        if (employeeService.read().size() > 0) {
            model.addAttribute("employeesList", employeeService.read());
        }
    }

    public void loadPage(Model model) {
        Categories.getCategoriesList(model);
        getEmployeesList(model);
    }
}
