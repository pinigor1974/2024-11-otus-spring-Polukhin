package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;
import ru.otus.hw.services.AuthorService;


@Slf4j
@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final GenreService genreService;

    private final AuthorService authorService;

    @GetMapping("/")
    public String listPage(Model model) {
        var books = bookService.findAll();

        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id,
                           Model model) {

        var book = bookService.findById(id);
        var genres = genreService.findAll();
        var authors = authorService.findAll();

        model.addAttribute("book", BookDto.fromDomainObject(book));
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return "edit";
    }

    @PostMapping("/edit")
    public String savePerson(@Valid @ModelAttribute("book") BookDto book,

                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            var genres = genreService.findAll();
            var authors = authorService.findAll();
            model.addAttribute("genres", genres);
            model.addAttribute("authors", authors);
            return "edit";
        }

        if (book.getId() > 0) {
            bookService.update(book.getId(), book.getTitle(), book.getAuthor(), book.getGenres());
        } else {
            bookService.insert(book.getTitle(), book.getAuthor(), book.getGenres());
        }
        return "redirect:/";
    }
}
