package book;

import java.util.ArrayList;

public class MyBook {
    ArrayList<String> URL;
    private String lable;
    private String author;
    private int price;
    private String link;
    public MyBook(String _lable, String _author, int _price, String _link){
        //if (!URL.contains(_url))
            //URL.add(_url);
        lable = _lable;
        price = _price;
        author = _author;
        link = _link;

        if (author.contains("<span>")){
            int start = author.indexOf("<span>");
            author = author.substring(start+6);
        }
    }

    public MyBook(String _lable, String _author, int _price){
        //if (!URL.contains(_url))
        //URL.add(_url);
        lable = _lable;
        price = _price;
        author = _author;

        if (author.contains("<span>")){
            int start = author.indexOf("<span>");
            author = author.substring(start+6);
        }
    }

    @Override
    public boolean equals(Object _book) {
        if(_book instanceof MyBook)
        {
            if (URL == ((MyBook)_book).URL && lable == ((MyBook)_book).lable && price == ((MyBook)_book).price)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return lable + '\n' + price + "Р. \n" + author+'\n';
    }
}
