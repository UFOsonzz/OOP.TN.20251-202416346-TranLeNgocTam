package com.hust.kstn.models;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private static int nbBooks = 0;
    private int id;
    private String title;
    private String category;
    private List<BookAuthor> authors = new ArrayList<>();
    private double cost;
    private int numOfTokens;
    public Book(String title, String category, double cost, int numOfTokens, List<BookAuthor> authors) {
        this.title = title;
        this.category = category;
        this.cost = cost;
        this.numOfTokens = numOfTokens;
        this.authors = authors;
        this.id = ++nbBooks;
    }
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getCategory() {
        return category;
    }
    public List<BookAuthor> getAuthors() {
        return authors;
    }
    public double getCost() {
        return cost;
    }
    public int numOfTokens() {
        return numOfTokens;
    }

    @Override
    public String toString() {
        return "Book[" + this.id + "]["
                + this.title + "]["
                + this.cost + "]["
                + this.category + "]["
                + this.numOfTokens + "]"
                + "\nAuthors: " + this.authors;
    }
}
