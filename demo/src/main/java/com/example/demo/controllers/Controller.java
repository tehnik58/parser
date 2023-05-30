package com.example.demo.controllers;
import com.example.demo.entity.Book;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.parser.*;
import com.example.demo.services.AuthService;
import com.example.demo.services.BookService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@org.springframework.stereotype.Controller
public class Controller {
    private final BookService bookService;
    private final AuthService authService;
    private final UserService userService;
    @Autowired
    public Controller(BookService bookService, AuthService authService, UserService userService)
    {
        this.bookService = bookService;
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndexPage(Model model)  {
        User user = authService.getCurrentUser();
        model.addAttribute("user", user!=null?user.getUsername():"Вы не авторизованы");
        return "index";
    }

    @GetMapping(value = "/books")
    public String getAllBooks(Model model)
    {
        User current = authService.getCurrentUser();
        if (current.getRoles().contains(Role.Admin)) {
            List<Book> result = new ArrayList<>();
            result.addAll(bookService.getEntities());
            return "books";
        }
        else {
            return permisssionDeniedView(model, current);
        }

    }



    @GetMapping(value = "/books/save")
    public String saveBook(@RequestParam("name") String name, @RequestParam("author") String author, @RequestParam("price") float price, Model model)
    {
        bookService.saveBook(name,author, price);
        return getAllBooks(model);
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam("name") String name, Model model)
    {
        var chitaiGorodParser = new ChitaiGorodParser();
        var labirintParser = new LabirintParser();
        var chitaiGorodBooks = chitaiGorodParser.getBooks(name, "<article");
        var labinintBooks = labirintParser.getBooks(name, "<div class=\"genres-carousel__item\">");
        model.addAttribute("cbooks", chitaiGorodBooks);
        model.addAttribute("lbooks", labinintBooks);
        return "search-books";
    }
    @GetMapping(value = "/books/filter")
    public String filterBookByAuthor(@RequestParam ("author") String author, Model model)
    {
        var books = bookService.getBooksByAuthor(author);
        model.addAttribute("books", books);
        return "books";
    }
    @GetMapping(value = "/books/filterByName")
    public String filterBookByName(@RequestParam ("name") String name, Model model)
    {
        var books = bookService.getBooksByName(name);
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/favourites/")
    public String getFavourites(Model model)
    {
        User user = authService.getCurrentUser();
       model.addAttribute("books", user.getFavourites());
       return "favourites";
    }
    @ResponseBody
    @GetMapping("/favourites/add_by_id")
    public String addToFavouriteById(@RequestParam("id") int id, Model model)
    {
        User user = authService.getCurrentUser();
        Book book = bookService.getEntityById(id);
        userService.addToFavourites(book, user);
        return getFavourites(model);
    }
    @ResponseBody
    @GetMapping("/favourites/add")
    public Book addBookToFavourite(@RequestParam("name") String name, @RequestParam("author") String author, @RequestParam("price") float price)
    {
        User user = authService.getCurrentUser();
        var book = bookService.saveBook(name, author, price);
        userService.addToFavourites(book, user);
        return book;
    }
    @GetMapping("/book/{id}")
    public String getBookByid(@PathVariable("id") long id, Model model)
    {
        var book = bookService.getEntityById(id);
        model.addAttribute("book", book);
        return "book_by_id";
    }

    private static String permisssionDeniedView(Model model, User current) {
        model.addAttribute("info", "Permission denied for"+ current.getUsername());
        return "permission_denied";
    }
}
