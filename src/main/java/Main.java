import service.PageScrapperHelper;
import service.Scraper;
import util.Urls;

import static util.Urls.*;

/**
 * Name : Timothy A
 * <p>
 * Execution: java Main
 */
public class Main {

    public static void main(String args[]) {

        System.out.println("******************Sainsburys******************");

        PageScrapperHelper helper = new PageScrapperHelper();
        Scraper scraper = new Scraper(helper);
        scraper.scrapePage(WEBPAGE);

    }

}
