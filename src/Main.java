import book.MyBook;
import parser.ChitaiGorodParse;
import parser.LabirintParse;
import parser.WebPageParser;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("введите книгу для поиска");
        Scanner scanner = new Scanner(System.in);
/*
        WebPageParser parse = new WebPageParser("https://www.labirint.ru/search/");
        parse.SetTags(new String[]{"data-name=\"", "\""},
                new String[]{"<div class=\"product-author\">","</span></a>"},
                new String[]{"data-price=\"", "\""});
        parse.SetUrlEnd("/?id_genre=-1&nrd=1");
        parse.setWebPage(scanner.nextLine());
        parse.FixPage("<div class=\"genres-carousel__item\">");
*/
        System.out.println("__________________________ЧИТАЙ ГОРОД___________________________");
        ChitaiGorodParse chit = new ChitaiGorodParse();
        MyBook[] pp = chit.getBooks(scanner.nextLine(), "<article");
        for (MyBook b: pp){
            System.out.println(b);
        }

        System.out.println("__________________________ЛАБИРИНТ______________________________");
        LabirintParse labirint = new LabirintParse();
        pp = labirint.getBooks(scanner.nextLine(),"<div class=\"genres-carousel__item\">");
        for (MyBook b: pp){
            System.out.println(b);
        }
    }
}