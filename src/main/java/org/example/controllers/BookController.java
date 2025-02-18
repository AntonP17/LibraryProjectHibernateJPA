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
@RequestMapping("/books")
public class BookController {

    private final PersonService personService;
    private final BookService bookService;

    public BookController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    //+ отображение всех
    // books?page=1&booksPerPage=10&sortByYear=true  вот пример запроса
    // страницы и количество отображения на странице , sortByYear если true то год сортируем , если нет то стандарт
    @GetMapping()
    public String index(@RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "30" ) int booksPerPage,
            @RequestParam (defaultValue = "false") boolean sortByYear,
            Model model) {

        List<Book> books = bookService.index(page, booksPerPage, sortByYear);
        model.addAttribute("books", books);
        return "book/index";
    }

    //+ отображение конкретной
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        Book book = bookService.show(id);
        Person owner = bookService.getOwnerByBookId(id);
        List<Person> people = personService.findAll();

        model.addAttribute("book", book);
        model.addAttribute("owner", owner);
        model.addAttribute("people", people);
        return "book/show";

    }

    // освобождение книги
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books";
    }

    // назначение книги
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @RequestParam("person_id") int personId) {
        bookService.assign(id, personId);
        return "redirect:/books";
    }

    // добавление
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book")Book book) {
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new";

        bookService.save(book);
        return "redirect:/books";
    }

    // редактирование
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.show(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "book/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    // удаление
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
