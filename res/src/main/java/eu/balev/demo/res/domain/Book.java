package eu.balev.demo.res.domain;

/**
 * Represents a book.
 * 
 * @author LBalev
 *
 */
public class Book {

	private String title;
	private String id;

	public String getId() {
		return id;
	}

	public Book setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Book setTitle(String title) {
		this.title = title;
		return this;
	}

}
