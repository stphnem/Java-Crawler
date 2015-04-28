package src.ir.assignments.three;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && href.startsWith("http://www.ics.uci.edu/");
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
         int docid = page.getWebURL().getDocid();
         String url = page.getWebURL().getURL();
         String domain = page.getWebURL().getDomain();
         String path = page.getWebURL().getPath();
         String subDomain = page.getWebURL().getSubDomain();
         String parentUrl = page.getWebURL().getParentUrl();
         String anchor = page.getWebURL().getAnchor();
         System.out.println("URL: " + url);
         
         // Log all the information I just received from the webpage
         logger.debug("Docid: {}", docid);
         logger.info("URL: {}", url);
         logger.debug("Domain: '{}'", domain);
         logger.debug("Sub-domain: '{}'", subDomain);
         logger.debug("Path: '{}'", path);
         logger.debug("Parent page: {}", parentUrl);
         logger.debug("Anchor text: {}", anchor);
         

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();
             Set<WebURL> links = htmlParseData.getOutgoingUrls();

             // Log the parsed data
             logger.debug("Text length: {}", text.length());
             logger.debug("Html length: {}", html.length());
             logger.debug("Number of outgoing links: {}", links.size());
             
             System.out.println("Text length: " + text.length());
             System.out.println("Html length: " + html.length());
             System.out.println("Number of outgoing links: " + links.size());
         }
         
         Header[] responseHeaders = page.getFetchResponseHeaders();
         if (responseHeaders != null) {
           logger.debug("Response headers:");
           for (Header header : responseHeaders) {
             logger.debug("\t{}: {}", header.getName(), header.getValue());
           }
         }

         logger.debug("=============");
    }
}
