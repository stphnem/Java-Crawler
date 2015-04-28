package src.ir.assignments.three;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/data/crawl/root";
        String userAgentString = "UCI Inf141-CS121 crawler 33819914";
        int maxDepthOfCrawling = 1;
        int politenessDelay = 750;
        int numberOfCrawlers = 7;
        boolean resumableCrawling = false;

        CrawlConfig config = new CrawlConfig();
        
        /*
         * crawlStorageFolder is a folder where intermediate crawl data is
         * stored.
         */
        config.setCrawlStorageFolder(crawlStorageFolder);        
        
        /*
         * Be polite: Make sure that we don't send more than 1 request per
         * second (1000 milliseconds between requests).
         */
        config.setPolitenessDelay(politenessDelay);
        
        /*
         * You can set the maximum crawl depth here. The default value is -1 for
         * unlimited depth
         */
        config.setMaxDepthOfCrawling(maxDepthOfCrawling);

        /*
         * This config parameter can be used to set your crawl to be resumable
         * (meaning that you can resume the crawl from a previously
         * interrupted/crashed crawl). Note: if you enable resuming feature and
         * want to start a fresh crawl, you need to delete the contents of
         * rootFolder manually.
         */
        config.setResumableCrawling(resumableCrawling);
        
        /*
         * Set this for credit
         */
        config.setUserAgentString(userAgentString);


        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("http://www.ics.uci.edu/");
        
        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MyCrawler.class, numberOfCrawlers);
    }
}
