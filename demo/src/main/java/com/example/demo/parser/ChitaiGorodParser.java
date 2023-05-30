package com.example.demo.parser;

import com.example.demo.entity.Book;

public class ChitaiGorodParser {
    WebPageParser parser;
    public  ChitaiGorodParser(){
        parser = new WebPageParser("https://www.chitai-gorod.ru/search?phrase=");
        parser.SetTags(
                new String[]{"<div class=\"product-title__head\">","</div>"},
                new String[]{"<div class=\"product-title__author\">","</div>"},
                new String[]{"data-chg-product-old-price=\"","\""},
                new String[]{"class=\"product-card product-card product\"><a href=\"","\""});
    }
    public Book[] getBooks(String request){
        parser.setWebPage(request);
        return parser.getBooksFromPage();
    }

    public Book[] getBooks(String request, String productTag){
        parser.setWebPage(request);

        parser.FixPageLabirint(productTag);
        return parser.getBooksFromPageLabirint();
    }
}

