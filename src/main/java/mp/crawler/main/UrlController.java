/**
 *@author nitin.yadav
 */

package mp.crawler.main;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class UrlController {
    private static final int CRAWL_DEPTH = 5;

    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "D:/data/url/crawl/root";
        int numberOfCrawlers = 3;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(CRAWL_DEPTH);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("http://www.espncricinfo.com/ci/engine/series/index.html");
        // controller.addSeed("http://www.ics.uci.edu/~welling/");
        // controller.addSeed("http://www.ics.uci.edu/");

        controller.start(UrlFetcher.class, numberOfCrawlers);
    }
}