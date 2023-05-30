package parser;

import book.MyBook;

public class LabirintParse extends ChitaiGorodParse {
    public LabirintParse(){
        parser = new WebPageParser("https://www.labirint.ru/search/");
        parser.SetTags(
                new String[]{"data-name=\"", "\""},
                new String[]{"<div class=\"product-author\">","</span></a>"},
                new String[]{"data-price=\"", "\""},
                new String[]{"class=\"product-title-link\" href=\"", "\""});
        parser.SetUrlEnd("/?id_genre=-1&nrd=1");
    }

    public MyBook[] getBooks(String request, String productTag){
        parser.setWebPage(request);

        parser.FixPageLabirint(productTag);
        return parser.getBooksFromPageLabirint();
    }
}
