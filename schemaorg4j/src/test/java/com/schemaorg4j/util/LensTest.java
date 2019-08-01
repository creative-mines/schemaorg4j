package com.schemaorg4j.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class LensTest {

    @Test
    public void basicGetLensingWorks() {
        assertEquals(Book.Author.andThen(Author.Name).get(bookWithAuthor()), "Robert M. Pirsig");
    }

    @Test
    public void basicSetLensingWorks() {
        Book book = bookWithAuthor();
        Book.Author.andThen(Author.Name).set(book, "Some other author");
        assertEquals(Book.Author.andThen(Author.Name).get(book), "Some other author");
    }

    @Test
    public void mapLensingWorksWHenTheChainIsInvalid() {
        CreativeWork book = bookWithAuthor();
        assertNull(Lens.oAs(Author.class).oAndThen(Author.Name).oGet(book) );
    }

    @Test(expected = LensException.class)
    public void mapLensingFailsWhenTheChainIsInvalidAndOptimisticLensingNotUsed() {
        CreativeWork book = bookWithAuthor();
        assertNull(Lens.as(Author.class).andThen(Author.Name).get(book) );
    }

    @Test
    public void setLensingWhenTheChainIsInvalidWorks() {
        // Should not throw but should not do any modification either
        Book b = new Book();
        Book.Author.oAndThen(Author.Name).oSet(b, "some author");
        assertNull(b.getAuthor());
    }

    @Test(expected = LensException.class)
    public void setLensingFailsWhenTheChainIsInvalidAnNonOpimisticLensingChosen() {
        Book b = new Book();
        Book.Author.andThen(Author.Name).set(b, "some author");
        assertNull(b.getAuthor());
    }

    @Test
    public void getLensingWhenTheChainIsInvalidWorks() {
        Book b = new Book();
        assertNull(Book.Author.oAndThen(Author.Name).oGet(b));
    }

    @Test(expected = LensException.class)
    public void getLensingWhenTheChainIsInvalidFailsWhenNoOptimisticLensing() {
        Book b = new Book();
        assertNull(Book.Author.andThen(Author.Name).get(b));
    }

    @Test
    public void mapLensingWithGetWorks() {
        CreativeWork book = bookWithAuthor();
        assertEquals(Lens.as(Book.class).andThen(Book.Author).andThen(Author.Name).get(book), "Robert M. Pirsig");
    }

    @Test
    public void mapLensingWithSetWorks() {
        CreativeWork book = bookWithAuthor();
        Lens.as(Book.class).andThen(Book.Author).andThen(Author.Name).set(book, "Some other author");
        assertEquals(Lens.as(Book.class).andThen(Book.Author).andThen(Author.Name).get(book), "Some other author");
    }

    @Test(expected = LensException.class)
    public void mapLensingWithSetShouldThrowIfTheClassMismatchesAndNoOptimisticLensing() {
        CreativeWork book = bookWithAuthor();
        Lens.as(Author.class).andThen(Author.Name).set(book, "Some other author");
        assertEquals(Lens.as(Book.class).andThen(Book.Author).andThen(Author.Name).get(book), "Robert M. Pirsig");
    }

    @Test
    public void mapLensingWithSetShouldNotThrowIfTHeClassMismatches() {
        CreativeWork book = bookWithAuthor();
        Lens.oAs(Author.class).oAndThen(Author.Name).oSet(book, "Some other author");
        assertEquals(Lens.as(Book.class).andThen(Book.Author).andThen(Author.Name).get(book), "Robert M. Pirsig");
    }

    private static class CreativeWork {

        private static Lens<CreativeWork, String> Title = new Lens<>(CreativeWork::getTitle,
            (creativeWork, title) -> {
                creativeWork.setTitle(title);
                return creativeWork;
            });

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    private static class Book extends CreativeWork {

        private static Lens<Book, String> Isbn = new Lens<>(Book::getIsbn, (book, isbn) -> {
            book.setIsbn(isbn);
            return book;
        });
        private static Lens<Book, Author> Author = new Lens<>(Book::getAuthor, (book, author) -> {
            book.setAuthor(author);
            return book;
        });

        private String isbn;
        private Author author;

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public Author getAuthor() {
            return author;
        }

        public void setAuthor(Author author) {
            this.author = author;
        }
    }

    private static class Author {

        private static Lens<Author, String> Name = new Lens<>(Author::getName, (author, name) -> {
            author.setName(name);
            return author;
        });

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private Book bookWithAuthor() {
        Author a = new Author();
        a.setName("Robert M. Pirsig");
        Book b = new Book();
        b.setAuthor(a);
        b.setIsbn("0060589469");
        b.setTitle("Zen and the Art of Motorcycle Maintenance");
        return b;

    }

}
