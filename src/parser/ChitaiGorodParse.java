package parser;
import book.MyBook;
import parser.WebPageParser;
public class ChitaiGorodParse {
    WebPageParser parser;
    public  ChitaiGorodParse(){
        parser = new WebPageParser("https://www.chitai-gorod.ru/search?phrase=");
        parser.SetTags(
                new String[]{"<div class=\"product-title__head\">","</div>"},
                new String[]{"<div class=\"product-title__author\">","</div>"},
                new String[]{"data-chg-product-old-price=\"","\""},
                new String[]{"class=\"product-card product-card product\"><a href=\"","\""});
    }
    public MyBook[] getBooks(String request){
        parser.setWebPage(request);
        return parser.getBooksFromPage();
    }

    public MyBook[] getBooks(String request, String productTag){
        parser.setWebPage(request);

        parser.FixPage(productTag);
        return parser.getBooksFromPage();
    }
}
