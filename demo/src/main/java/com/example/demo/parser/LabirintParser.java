package com.example.demo.parser;

public class LabirintParser extends  ChitaiGorodParser
{
    public LabirintParser(){
        parser = new WebPageParser("https://www.labirint.ru/search/");
        parser.SetTags(
                new String[]{"data-name=\"", "\""},
                new String[]{"<div class=\"product-author\">","</span></a>"},
                new String[]{"data-price=\"", "\""},
                new String[]{"class=\"product-title-link\" href=\"", "\""});
        parser.SetUrlEnd("/?id_genre=-1&nrd=1");
    }

}
