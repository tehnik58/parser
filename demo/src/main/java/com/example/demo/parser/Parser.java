package com.example.demo.parser;

import com.example.demo.entity.Book;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
    private BooksAtribute beforeStringCheck(String string)
    {
        if (string.contains("product-card__text"))
            return BooksAtribute.lable;
        if (string.contains("product-title__author"))
            return BooksAtribute.author;
        if (string.contains("favorite-button"))
            return BooksAtribute.price;
        if (string.contains("href=\"/product/"))
            return BooksAtribute.url;
        return BooksAtribute.none;
    }

    public ArrayList<Book> getWebPage(String _url, String request){
        String page = "";
        boolean fl = false;
        ArrayList<Book> books = new ArrayList<Book>();

        try {
            _url = (_url+ URLEncoder.encode(request, "UTF-8")+"\n");
            System.out.println(_url);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        try {
            URL url = new URL(_url);

            try {
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
                String string = reader.readLine();

                String URL = null;
                String label = null;
                String author = null;
                int price = 0;

                while (string != null) {
                    //page += string + '\n';
                    BooksAtribute attribute = beforeStringCheck(string);
                    //if(attribute == BooksAtribute.url)

                    string = reader.readLine();

                    switch(attribute)
                    {
                        case lable:
                            label = string.trim();
                            break;
                        case url:
                            break;
                        case price:
                            //System.out.println(string.replace(" ₽", "").replaceAll("\\s+", ""));
                            string = string.replaceAll("\\s+", "").replace("₽", "").replace(" ","");
                            if(isNumeric(string))
                                price = Integer.parseInt(string);
                            break;
                        case author:
                            author = string.trim();
                        default:
                            break;
                    }
                    if (label!=null && url != null && price!=0 && author!=null)
                    {
                        if(!page.contains("lable-"+label + "\nprice-" + price + "\nР. author" + author + "\n\n"))
                        {
                            page += "lable-"+label + "\nprice-" + price + "\nР. author " + author + "\n\n";
                            System.out.println(page);
                            books.add(new Book(author,
                                    label,
                                    price));
                        }
                        URL = null;
                        label = null;
                        author = null;
                        price = 0;
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return books;
    }
}
