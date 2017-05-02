package eu.balev.demo.res.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eu.balev.demo.res.domain.Book;

@RestController
public class BookControler {

	@GetMapping(value = "/books/{id}")
	public Book getBook(@PathVariable("id") String id) {
	
		Book result = new Book().
				setId(id);
	
	
		result.setTitle("Various thoughts about the ID " + id);
		
		return result;
	}

}
