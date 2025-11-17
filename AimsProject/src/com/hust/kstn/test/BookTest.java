package com.hust.kstn.test;

import com.hust.kstn.models.Book;
import com.hust.kstn.models.BookAuthor;
import java.util.ArrayList;
import java.util.List;

public class BookTest {
    public static void main(String[] args) {
        List<BookAuthor> authors = new ArrayList<>();
        authors.add(new BookAuthor("Tam", 2006, "An experienced author"));
        authors.add(new BookAuthor("Ngoc", 2005, "A leecher"));
        Book book = new Book("Java Programming", "Sci-fi", 500.0, 10, authors);
        System.out.println(book);
    }
}
