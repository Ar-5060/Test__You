package com.Crud.an.Controller;

import com.Crud.an.Model.Book;
import com.Crud.an.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    // View all books
    @GetMapping("/get")
    public String getBooks(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "book/index"; // Thymeleaf template
    }

    // Show the form to create a new book
    @GetMapping("/create")
    public String createBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/create"; // Thymeleaf template
    }

    // Create a new book
    @PostMapping("/create")
    public String createBook(@ModelAttribute Book book) {
        bookService.createBook(book);
        return "redirect:/book/get"; // Redirect to book list
    }

    // Show the form to update an existing book
    @GetMapping("/update/{id}")
    public String updateBookForm(@PathVariable int id, Model model) {
        Optional<Book> book = bookService.findById(id);
        book.ifPresent(b -> model.addAttribute("book", b));
        return "book/update"; // Thymeleaf template
    }

    // Update an existing book
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute Book book) {
        bookService.updateBook(id, book);
        return "redirect:/book/get"; // Redirect to book list
    }

    // Delete a book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "redirect:/book/get"; // Redirect to book list
    }

    // View a book by ID
    @GetMapping("/{id}")
    public String viewBook(@PathVariable int id, Model model) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "book/view"; // Thymeleaf template for view
        } else {
            // Handle the case when the book is not found
            model.addAttribute("error", "Book not found");
            return "error"; // You can create an error.html page if you want
        }
    }
}
