/**
 *@author nitin.yadav
 */

package mp.crawler.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import mp.crawler.dtos.CrawlStat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class UrlFetcher extends WebCrawler {

    private final static String SEED_URL1 = "http://www.espncricinfo.com/";
    private final static String SEED_URL2 = "http://www.espncricinfo.com/ci/engine/series/index.html";
    private final static String PATTERN = "/engine/match/";
    private final static String MATCH_FILE = "D:/data/url/match_ids.txt";
    private final static int BATCH_SIZE = 100;
    private static final Logger logger = LoggerFactory.getLogger(UrlFetcher.class);

    private CrawlStat myCrawlStat;

    public UrlFetcher() {
        myCrawlStat = new CrawlStat();
    }

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp3|zip|gz))$");

    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches() && href.startsWith(SEED_URL1);
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        // logger.info("URL: " + url);
        myCrawlStat.incTotalProcessedPages();
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            List<WebURL> links = htmlParseData.getOutgoingUrls();
            myCrawlStat.incTotalLinks(links.size());

        }
        if (checkValidUrl(url)) {
            try {
                Long matchId = Long.parseLong(url.substring(url.indexOf(PATTERN) + PATTERN.length(),
                        url.indexOf(".html")));
                myCrawlStat.getMatchIds().add(matchId);
                myCrawlStat.incTotalValidMatchIds();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.info("Invalid url: " + url);
        }
        if (myCrawlStat.getTotalValidMatchIds() > 0 && (myCrawlStat.getTotalValidMatchIds() % BATCH_SIZE) == 0) {
            dumpMyData();
            myCrawlStat.setTotalLinks(0L);
            myCrawlStat.setTotalProcessedPages(0L);
            myCrawlStat.setTotalValidMatchIds(0L);
            myCrawlStat.getMatchIds().clear();
        }
    }

    private Boolean isAlreadyProcessed(Long matchId) {
        return false;
    }

    private Boolean checkValidUrl(String url) {
        if (url.contains("/engine/match/")) {
            try {
                Long matchId = Long.parseLong(url.substring(url.indexOf(PATTERN) + PATTERN.length(),
                        url.indexOf(".html")));
                if (isAlreadyProcessed(matchId)) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public Object getMyLocalData() {
        return myCrawlStat;
    }

    @Override
    public void onBeforeExit() {
        dumpMyData();
        logger.info("Crawling finished!");
    }

    public void dumpMyData() {
        File file = null;
        BufferedWriter bufferWriter = null;
        try {
            file = new File(MATCH_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getPath(), true);
            bufferWriter = new BufferedWriter(fileWriter);
            logger.info("Ready to dump matchIds!");
            for (Long matchId : myCrawlStat.getMatchIds()) {
                bufferWriter.write(String.valueOf(matchId));
                bufferWriter.newLine();
                bufferWriter.flush();
            }
            bufferWriter.close();
            fileWriter.close();
            logger.info("MatchIds are saved!");
        } catch (IOException e) {
            logger.error("Failed to dump crawled data!", e);
        }
    }
}