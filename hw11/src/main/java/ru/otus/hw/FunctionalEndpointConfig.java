package ru.otus.hw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;
import java.util.Map;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class FunctionalEndpointConfig {
    @Bean
    public RouterFunction<ServerResponse> composedRoutes(
            BookService bookService,
            AuthorService authorService,
            GenreService genreService) {
        return route()
                .GET("/", request -> ok().render("list", Map.of()))
                .GET("/add", accept(APPLICATION_JSON), request -> {
                    var authors = authorService.findAll().toStream().toList();
                    var genres = genreService.findAll().toStream().toList();
                    return ok().render("add", Map.of(
                            "authors", authors,
                            "genres", genres));
                })
                .GET("/api/books", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(bookService.findAll(), BookDto.class))
                .DELETE("/api/books/{id}",
                        request -> bookService.deleteById(Long.valueOf(request.pathVariable("id")))
                                .flatMap(book -> ok().build()))
                .GET("/api/books/{id}",
                        request -> bookService.findById(Long.parseLong(request.pathVariable("id")))
                                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book))))
                .PUT("/api/books", accept(APPLICATION_JSON),
                        request -> {
                            return request.bodyToMono(BookDto.class).flatMap(
                                    b -> bookService.save(b)
                            ).flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromValue(book)));
                        }).build();
    }
}