package com.example.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PersonController {

    private final PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping("/")
    public String showAllEmployee(Model model) {
        List<Person> persons = this.personRepository.findAll();
        model.addAttribute("persons", persons);
        return "persons";
    }

    @RequestMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("person", person);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") String id, @ModelAttribute Person updatedPerson) {
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
        return "redirect:/";
    }

    @RequestMapping("/add-person")
    public String showSignUpForm(Model model) {
        model.addAttribute("person", new Person());
        return "add";
    }

    @PostMapping("/addperson")
    public String addUser(Person person, @ModelAttribute Person newPerson) {
        personRepository.save(person);
        return "redirect:/";
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) {
        Person user = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        personRepository.delete(user);

        return "redirect:/";
    }
}
