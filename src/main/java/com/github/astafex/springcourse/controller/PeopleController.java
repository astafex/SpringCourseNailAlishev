package com.github.astafex.springcourse.controller;

import com.github.astafex.springcourse.dao.PersonDAO;
import com.github.astafex.springcourse.model.Person;
import com.github.astafex.springcourse.util.PersonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @GetMapping()
    public String showAllPeople(Model model) {
        model.addAttribute("people", personDAO.index());
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "/people/show";
    }

    @GetMapping("/new")
    public String getFormCreatePerson(@ModelAttribute("person") Person person) {
        return "/people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person newPerson, BindingResult bindingResult) {
        personValidator.validate(newPerson, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/people/new";
        }
        personDAO.save(newPerson);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String getFormEditPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person updatePerson, BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(updatePerson, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/people/edit";
        }
        personDAO.update(id, updatePerson);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
