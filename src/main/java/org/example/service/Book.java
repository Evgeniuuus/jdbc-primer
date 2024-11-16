package org.example.service;

import java.util.Objects;

public class Book {
    private String name;
    private String author;
    private int publishingYear;
    private String isbn;
    private String publisher;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn); // Предполагаем, что ISBN уникален
    }


    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return name + " by " + author + " (" + publishingYear + ")";
    }
}
