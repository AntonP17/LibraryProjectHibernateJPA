package org.example.controllers;

import jakarta.validation.Valid;
import org.example.model.Book;
import org.example.model.Person;
import org.example.services.BookService;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;
    private final BookService bookService;

    public PeopleController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    // отображаем всех
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/index";
    }

    // отображаем одного
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        Person person = personService.showById(id);
        List<Book> books = bookService.getBooksByPersonId(id);

        // Проверяем просроченность книг и устанавливаем статус для каждой книги
        boolean hasOverdueBooks = bookService.isOverdue(person, books);

        model.addAttribute("person", person);
        model.addAttribute("books", books);
        model.addAttribute("isOverdue", hasOverdueBooks);

        return "people/show";
    }

    // создаем
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        personService.save(person);
        return "redirect:/people";
    }

    // редактируем
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.showById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personService.update(id, person);
        return "redirect:/people";
    }

    // удаляем
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }
}
