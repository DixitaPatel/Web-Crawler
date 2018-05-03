import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller{
	
	public static void main(String a[]) throws Exception{
		String crawlStorageFolder = "data/crawl";
		int numberOfCrawlers = 7;
		int maxDepthOfCrawling = 16;
		int maxPagesToFetch = 20000;
		CrawlConfig config = new CrawlConfig();
		config.setMaxDepthOfCrawling(maxDepthOfCrawling);
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxPagesToFetch(maxPagesToFetch);
		config.setPolitenessDelay(220);
		config.setUserAgentString("Befriend");
		config.setIncludeHttpsPages(true);
		config.setFollowRedirects(true);
		config.setIncludeBinaryContentInCrawling(true);
		
		/* * Instantiate the controller for this crawl. */ 
		PageFetcher pageFetcher = new PageFetcher(config); 
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig(); 
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		
		/* * For each crawl, you need to add some seed urls. 
		 * These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages */ 
		
		controller.addSeed("https://www.c-span.org/");
		/* Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished. */ 
		controller.start(MyCrawler.class, numberOfCrawlers); 
		MyCrawler.task_1_writer.close();
		MyCrawler.task_2_writer.close();
		MyCrawler.task_3_writer.close();
//		System.out.println(MyCrawler.uniqueUrlSet.size());
	}
}