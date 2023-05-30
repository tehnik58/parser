package com.example.demo.entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Book {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;



    @Column(nullable = false)
    private final String author;
    @Column(nullable = false)
    private final String name;

    public float getPrice() {
        return price;
    }

    private final float price;
    private String[] links;
    private Book()
    {

        name="";
        author="";
        price = 0;
    }
@Autowired
    public Book( String author, String name, float price) {
        this.name = name;
        this.price = price;
    if (author.contains("<span>")) {
        int start = author.indexOf("<span>");
        this.author = author.substring(start + 6);
    }
        else{
            this.author = author;
        }
    }

    public long getId() {
        return id;
    }
    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }
}
