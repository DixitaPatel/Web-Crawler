import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
	
	private final static Pattern FILTERS = Pattern.compile(
	        ".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v" +
	        "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	static BufferedWriter task_1_writer = null;
	static BufferedWriter task_2_writer = null;
	static BufferedWriter task_3_writer = null;
	
	AtomicInteger unique_urls_within_website = new AtomicInteger(0);
	AtomicInteger total_urls = new AtomicInteger(0);
	AtomicInteger unique_urls_outside_website = new AtomicInteger(0);
	
	AtomicInteger totalPagesAttempted = new AtomicInteger(0);
	AtomicInteger totalPagesFetched= new AtomicInteger(0);
	AtomicInteger totalPagesAborted = new AtomicInteger(0);
	AtomicInteger totalPagesFailed = new AtomicInteger(0);
	
	static Map<Integer, Integer> statusCode = new HashMap<Integer, Integer>();
	
	
	static{
		
		try {
			if(task_1_writer == null){
				task_1_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("fetch_cspan.csv")));
				task_1_writer.write("URL,Status Code\n");
			}
			if(task_2_writer == null){
				task_2_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("visit_cspan.csv")));
				task_2_writer.write("URL,Size,Outlinks_size,content-type\n");
			}
			if(task_3_writer == null){
				task_3_writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("urls_cspan.csv")));
				task_3_writer.write("URL,OK_Or_N_OK\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized void writer_1(String url, int statusCode) {
		try {
			task_1_writer.write(url+","+statusCode+"\n");
		} catch (IOException e) {
			System.out.println("Writer for task 1 failed");
		}
	}
	
	public static synchronized void writer_2(String url, int link_size, int size, String contentType) {
		try {
			task_2_writer.write(url+","+size+","+link_size+","+contentType+"\n");
		} catch (IOException e) {
			System.out.println("Writer for task 2 failed");
		}
	}
	
	public static synchronized void writer_3(String url, String s) {
		try {
			task_3_writer.write(url+","+s+"\n");
		} catch (IOException e) {
			System.out.println("Writer for task 3 failed");
		}
	}
	
	@Override
	protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
			
		totalPagesAttempted.incrementAndGet();
		writer_1(webUrl.getURL(),statusCode);
		
		if(statusCode >= 200 && statusCode < 300){
			if(statusCode == 200){
				//pages_200.incrementAndGet();
			}
			totalPagesFetched.incrementAndGet();
		}else if(statusCode >=300 && statusCode < 400 ){
			totalPagesAborted.incrementAndGet();
		}else{
			totalPagesFailed.incrementAndGet();
		}
		
		
	}
	
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		
		
		String href = url.getURL().toLowerCase();
		String s = "N_OK";
		if(href.startsWith("http://www.c-span.org/") || href.startsWith("https://www.c-span.org/")){
			s = "OK";
		}
		
		writer_3(url.getURL(),s);
		return !FILTERS.matcher(href).matches() && (href.startsWith("http://www.c-span.org/") || href.startsWith("https://www.c-span.org/"));
		}
	        
	@Override
	public void visit(Page page) {
		
		
		
		String url = page.getWebURL().getURL(); 
		System.out.println("URL: " + url);
		
		int link_size = 0;
		
		if (page.getParseData() instanceof HtmlParseData) { 
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData(); 
			String text = htmlParseData.getText(); 
			String html = htmlParseData.getHtml(); 
			Set<WebURL> links = htmlParseData.getOutgoingUrls(); 
			System.out.println("Text length: " + text.length()); 
			System.out.println("Html length: " + html.length()); 
			System.out.println("Number of outgoing links: " + links.size()); 
			link_size = links.size();
			}
		
		writer_2(page.getWebURL().getURL(),link_size,page.getContentData().length,page.getContentType());
	}
	
//	@Override
//	 public void onBeforeExit() {
//		try {
//			task_1_writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
