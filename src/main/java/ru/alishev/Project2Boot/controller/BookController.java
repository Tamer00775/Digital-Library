package ru.alishev.Project2Boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.Project2Boot.models.Book;
import ru.alishev.Project2Boot.models.Person;
import ru.alishev.Project2Boot.service.BookService;
import ru.alishev.Project2Boot.service.PeopleService;

import javax.validation.Valid;


@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PeopleService peopleService;
    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false)Integer page,
                        @RequestParam(value = "books_per_page", required = false)Integer books_per_page,
                        @RequestParam(value = "sort_by_year", required = false) boolean sort){
        if(page == null || books_per_page == null)
            model.addAttribute("books", bookService.findAll(sort));
        else
            model.addAttribute("books", bookService.findAll(page, books_per_page));
        return "books/index";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "/books/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute(bookService.find(id));
        return "/books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book")Book book, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "books/edit";
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        Person bperson = bookService.findOwner(id);
        Person person = new Person();
        model.addAttribute("book", bookService.find(id));
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("person", person);
        model.addAttribute("bperson", bperson);
        return "/books/show";
    }



    @PatchMapping ("{id}/add")
    public String makePerson(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        System.out.println("person id after submit: " + person.getId() + "BOOK ID: " + id);
        bookService.savePerson(person, id);
        return "redirect:/books";
    }

    @DeleteMapping("{id}/delete")
    public String deletePerson(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookService.deletePerson(id);
        return "redirect:/books";
    }
    @GetMapping("/search")
    public String search(){
        return "/books/search";
    }

    @PostMapping("/search")
    public String searchBook(Model model, @RequestParam("query") String bookName){
        model.addAttribute("books", bookService.search(bookName));
        Book book = new Book();
        model.addAttribute("book",book);
        return "books/search";
    }
}