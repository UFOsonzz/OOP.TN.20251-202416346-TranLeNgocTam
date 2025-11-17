package com.hust.kstn.models;

import java.util.ArrayList;
import java.util.List;

public class Book extends Media {
    private List<BookAuthor> authors = new ArrayList<>();
    private int numOfTokens;
    public Book(String title, String category, double cost, int numOfTokens, List<BookAuthor> authors) {
        super(title, category, cost);
        this.numOfTokens = numOfTokens;
        this.authors = authors;
    }

    public List<BookAuthor> getAuthors() {
        return authors;
    }

    public int numOfTokens() {
        return numOfTokens;
    }

    @Override
    public String toString() {
        return super.toString() 
                + "[" + this.numOfTokens + "]"
                + "\nAuthors: " + this.authors;
    }
}
