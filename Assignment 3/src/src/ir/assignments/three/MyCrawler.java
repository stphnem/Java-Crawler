package src.ir.assignments.three;

import java.util.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

	private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpeg|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz|ico|pfm|c|h|o))$");
	private HashMap<String, Set<String>> subdomains = new HashMap<String, Set<String>>();
	
	public void addSubdomain(String subdomain, String url) {
		if (subdomains.containsKey(subdomain)) {
			subdomains.get(subdomain).add(url);
		}
		else {
			Set<String> uniqueUrl = new HashSet<String>();
			uniqueUrl.add(url);
			subdomains.put(subdomain, uniqueUrl);
		}
	}

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
         // Ignore the url if it has an extension that matches our defined set of image extensions.
         if (IMAGE_EXTENSIONS.matcher(href).matches()) {
           return false;
         }

         // Only accept the url if it is in the "www.ics.uci.edu" domain and protocol is "http".
         return href.matches("(http://).*(.ics.uci.edu/).*");
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
         
         addSubdomain(subDomain, url);

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();
             Set<WebURL> links = htmlParseData.getOutgoingUrls();
             
             PrintWriter writer;
			 try {
				 writer = new PrintWriter("content/" + String.valueOf(docid) + ".txt", "UTF-8");
				 writer.println(html);
			 } catch (FileNotFoundException e) {
				 e.printStackTrace();
			 } catch (UnsupportedEncodingException e) {
				 e.printStackTrace();
			 }
             
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
