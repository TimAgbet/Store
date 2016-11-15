package service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import domain.Product;
import domain.ProductDetails;
import domain.Result;

import java.text.DecimalFormat;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;


public class Scraper {

    private double total = 0.00;
    private DecimalFormat df = new DecimalFormat("#.00");
    private PageScrapperHelper helper;

    public Scraper(PageScrapperHelper helper) {
        this.helper = helper;
    }

    public void scrapePage(String url){
        //Get all elements on the page.
        UserAgent userAgent = getHeadLessBrowser(url);
        Elements pageItems = helper.scrape(userAgent);

        List<Product> products = newArrayList();
        Result result = new Result();
        setProductDetails(pageItems, products);
        result.setResults(products);
        result.setTotal(df.format(total));
        printProduct(result);
    }

    protected UserAgent getHeadLessBrowser(String url) {
        UserAgent userAgent = new UserAgent(); //create new userAgent (headless browser).
        try {
            userAgent.visit(url);  //visit a url
        } catch (ResponseException e) {
            System.out.println(e);
        }
        return userAgent;
    }

    private void setProductDetails(Elements pageItems, List<Product> products) {
        if(pageItems!=null){
            for (Element pageItem : pageItems) {
                Product product = new Product();

                ProductDetails productDetails = helper.getProductDetails(pageItem);
                product.setDescription(productDetails.getProductText());
                product.setTitle(productDetails.getTitle());
                product.setUnit_price(productDetails.getPrice());
                product.setSize(productDetails.getSize());
                total += Double.parseDouble(productDetails.getPrice()); // Adding to total amount.
                products.add(product);
            }
        }
    }
    protected static void printProduct(Result result){
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        System.out.println(gson.toJson(result));
    }

}
