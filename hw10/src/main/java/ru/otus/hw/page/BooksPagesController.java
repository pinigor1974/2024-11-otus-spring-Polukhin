package ru.otus.hw.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.GenreService;

@Controller
@RequiredArgsConstructor
public class BooksPagesController {


    private final GenreService genreService;

    private final AuthorService authorService;


    @GetMapping("/")
    public String listBooksPage(Model model) {
        model.addAttribute("keywords", "list users in Omsk, omsk, list users, list users free");
        return "list";
    }

    @GetMapping("/add")
    public String addPBookPage(@RequestParam("id") long id,  Model model) {
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("authors", authorService.findAll());
        return "add";
    }
}
