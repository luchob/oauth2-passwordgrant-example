package eu.balev.demo.res.web;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.balev.demo.res.domain.Book;

@RestController
public class BookControler {

	// just a dummy "repo"
	private final AtomicInteger idGen = new AtomicInteger();
	private final Map<String, Book> books = new ConcurrentHashMap<>();

	@PreAuthorize("hasRole('READ_BOOK')")
	@GetMapping(value = "/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") String id) {

		Book book = books.get(id);
		if (book == null) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Book>(book, HttpStatus.OK);
		}
	}

	@PreAuthorize("hasRole('WRITE_BOOK')")
	@PostMapping(value = "/books")
	public Book createBook(@RequestParam("title") String title) {

		Book newBook = new Book();

		newBook.setId(String.valueOf(idGen.incrementAndGet()));
		newBook.setTitle(title);

		books.put(newBook.getId(), newBook);

		return newBook;
	}

}
