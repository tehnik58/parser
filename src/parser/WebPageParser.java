package parser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import book.MyBook;

public class WebPageParser {
    public String page;
    String url;
    String[] KeyTitle;
    String[] KeyAuthor;
    String[] KeyPrice;
    String[] KeyLink;
    public String urldEnd ="";

    public WebPageParser(String _url){
        url = _url;
    }

    public void FixPage(String startBlock){
        String block;
        String _page = page;
        while (!_page.isEmpty()){
            if (_page.contains(startBlock)){
                int start = _page.indexOf(startBlock)+startBlock.length()+1;
                int end = _page.indexOf(startBlock,start);
                if (end == -1){
                    end = _page.length()-1;
                }
                block = _page.substring(start-1, end);

                if(!(block.contains(KeyAuthor[0]) &&
                        block.contains(KeyPrice[0])&&
                        block.contains(KeyTitle[0]) &&
                        block.contains(KeyLink[0]))){

                    _page.replace(block, "");
                    page = page.substring(0, page.indexOf(block)) + _page.substring(end, _page.length()-1);
                }
                else{
                    _page.replace(block, "");
                }
                _page = _page.substring(end, _page.length()-1);
            }else{
                break;
            }
        }
    }

    public void FixPageLabirint(String startBlock){
        String block;
        String _page = page;
        while (!_page.isEmpty()){
            if (_page.contains(startBlock)){
                int start = _page.indexOf(startBlock)+startBlock.length()+1;
                int end = _page.indexOf(startBlock,start);
                if (end == -1){
                    end = _page.length()-1;
                }
                block = _page.substring(start-1, end);

                if(!(block.contains(KeyAuthor[0]) &&
                        block.contains(KeyPrice[0])&&
                        block.contains(KeyTitle[0]))){

                    _page.replace(block, "");
                    page = page.substring(0, page.indexOf(block)) + _page.substring(end, _page.length()-1);
                }
                else{
                    _page.replace(block, "");
                }
                _page = _page.substring(end, _page.length()-1);
            }else{
                break;
            }
        }
    }

    public void SetTags(String[] keyTitle, String[] keyAuthor, String[] keyPrice, String[] keyLink){
        KeyTitle = keyTitle;
        KeyAuthor = keyAuthor;
        KeyPrice = keyPrice;
        KeyLink = keyLink;
    }
    public void SetUrlEnd(String urlFiltr){
        urldEnd = urlFiltr;
    }

    public MyBook[] getBooksFromPage(){
        List<MyBook> books = new ArrayList<>();
        //String[] titles = getAttributes(page.substring(0), "<div class=\"product-title__head\">", '<');
        String[] titles = getAttributes(page.substring(0), KeyTitle[0], KeyTitle[1]);
        //String[] authors = getAttributes(page.substring(0), "<div class=\"product-title__author\">", '<');
        String[] authors = getAttributes(page.substring(0), KeyAuthor[0], KeyAuthor[1]);
        //String[] prices = getAttributes(page.substring(0), "data-chg-product-price=\"", '\"');
        String[] prices = getAttributes(page.substring(0), KeyPrice[0], KeyPrice[1]);

        String[] links = getAttributes(page.substring(0), KeyLink[0], KeyLink[1]);

        System.out.println(titles.length);
        System.out.println(authors.length);
        System.out.println(prices.length);

        for(int i = 0; i < titles.length; i++){
            books.add(new MyBook(titles[i], authors[i],Integer.parseInt(prices[i]),links[i]));
        }
        return books.toArray(MyBook[]::new);
    }

    public MyBook[] getBooksFromPageLabirint(){
        List<MyBook> books = new ArrayList<>();
        //String[] titles = getAttributes(page.substring(0), "<div class=\"product-title__head\">", '<');
        String[] titles = getAttributes(page.substring(0), KeyTitle[0], KeyTitle[1]);
        //String[] authors = getAttributes(page.substring(0), "<div class=\"product-title__author\">", '<');
        String[] authors = getAttributes(page.substring(0), KeyAuthor[0], KeyAuthor[1]);
        //String[] prices = getAttributes(page.substring(0), "data-chg-product-price=\"", '\"');
        String[] prices = getAttributes(page.substring(0), KeyPrice[0], KeyPrice[1]);


        System.out.println(titles.length);
        System.out.println(authors.length);
        System.out.println(prices.length);

        for(int i = 0; i < titles.length; i++){
            books.add(new MyBook(titles[i], authors[i],Integer.parseInt(prices[i])));
        }
        return books.toArray(MyBook[]::new);
    }

    private String[] getAttributes(String page, String tag, String endChar){
        List<String> line = new ArrayList<>();;
        while (!page.isEmpty()){
            if (page.contains(tag)){
                int start = page.indexOf(tag)+tag.length()+1;
                int end = page.indexOf(endChar,start);
                line.add(page.substring(start-1, end).trim());
                page = page.substring(end, page.length()-1);
            }else{
                return line.toArray(String[]::new);
            }
        }
        return null;
    }

    public void setWebPage(String request){
        String _page = "";
        boolean fl = false;
        ArrayList<MyBook> books = new ArrayList<MyBook>();

        try {
            url = (url+ URLEncoder.encode(request, "UTF-8")+urldEnd+"\n").replace("+","%20");
            System.out.println(url);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        try {
            URL _url = new URL(url);

            try {
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(_url.openStream()));
                String string = reader.readLine();

                String URL = null;
                String lable = null;
                String author = null;
                int price = 0;

                while (string != null) {
                    string = reader.readLine();
                    _page += string+'\n';

                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        page = _page;
    }
}
