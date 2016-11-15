package service;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import domain.ProductDetails;
import domain.QueryType;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import static domain.QueryType.FIND_ALL;
import static domain.QueryType.FIND_FIRST;

public class PageScrapperHelper extends AbstractHelper{

    public Elements scrape(UserAgent userAgent){
        Elements pageItems = null;
        try {
            Element ul = getFirst("<ul class=\"productLister listView\">", userAgent); //find first div who's class matches 'product'
            pageItems = ul.findEvery("<a href>"); //find search result links
        } catch (JauntException e) {//if an HTTP/connection error occurs, handle JauntException.
            System.err.println(e);
        }
        return pageItems;
    }

    public ProductDetails getProductDetails(Element element){

        ProductDetails productDetails = new ProductDetails();
        //Get Title
        productDetails.setTitle(getTitle(element));
        //Get Price
        String subPrice = getLinkData(element, "<p class=\"pricePerUnit\">", FIND_FIRST).substring(8).trim();
        productDetails.setSubPrice(subPrice); // removing the Pound Sign.
        //Get Description
        String description = getLinkData(element, "<div class=\"productText\">", FIND_ALL);
        productDetails.setProductText(description);
        //Get size
        productDetails.setSize(getLinkedHTMlSize(getLink(element)) + " kb");

        return productDetails;
    }

    public String getLinkData(Element element, String findData, QueryType queryType){
        UserAgent linkAgent = new UserAgent();
        try {
            linkAgent.visit(getLink(element));
            switch (queryType){
                case FIND_FIRST: return getFirst(findData, linkAgent).getText();
                case FIND_ALL: return linkAgent.doc.findEvery(findData).findFirst("<p>").getText();
            }
        } catch (ResponseException | NotFound notFound) {
            System.out.println(notFound);
        }
        return null;
    }


    public int getLinkedHTMlSize(String url) {
        URLConnection con;
        // its in a try and catch in case the url given is wrong or invalid
        try {

            con = new URL(url).openConnection(); // we open the stream
            int size = con.getContentLength() / (1024); //url size in kbs
            return size;

        } catch (IOException e) {
            System.err.println(e);
            // this is returned if the connection went invalid or failed.
            return 0;
        }
    }

    private String getLink(Element element){
        String link = null;
        try {
            link = element.getAt("href"); // getting the link of products.
        } catch (NotFound ex) {
            System.err.println(ex);
        }
        return link;
    }
}
