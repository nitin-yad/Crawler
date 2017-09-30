/**
 *@author nitin.yadav
 */

package mp.crawler.tests;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlFetcherTest {
    // Test:http://www.espncricinfo.com/ci/engine/match/946857.html?innings=3;view=commentary
    // ODI:http://www.espncricinfo.com/ci/engine/match/932851.html?innings=2;view=commentary
    private static final String WEBSITE_URL = "http://www.espncricinfo.com";
    private static final String MATCH_URI = "/ci/engine/match/";
    private static final String INNING = ".html?innings=";
    private static final String VIEW = ";view=";
    private static final String COMMENTARY = "commentary";
    private static int inning = 1;

    private static final String HTML_COMM_CLASS = "commentary-event";
    private static final String HTML_COMM_OVER_CLASS = "commentary-overs";
    private static final String HTML_COMM_TEXT_CLASS = "commentary-text";

    public static void main(String[] args) {
        String matchId = "932851";
        Document document;
        try {
            do {
                String urlString = getNextUrl(matchId, inning, COMMENTARY);
                document = Jsoup.connect(urlString).get();
                Elements elms = document.getElementsByClass(HTML_COMM_CLASS);
                String prevOver = null;
                for (Element elm : elms) {
                    Elements over = elm.getElementsByClass(HTML_COMM_OVER_CLASS);
                    Elements text = elm.getElementsByClass(HTML_COMM_TEXT_CLASS);
                    // in the case when an extra comment is there after a
                    // ball.
                    if (over.size() > 0)
                        System.out.println(over.text());
                    else
                        System.out.println(prevOver);
                    System.out.println(text.text());
                    prevOver = over.text();
                }
                ++inning;
            } while (document.getElementsByClass(HTML_COMM_CLASS).size() > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getNextUrl(String matchId, int inn, String view) {
        StringBuilder sb = new StringBuilder();
        sb.append(WEBSITE_URL);
        sb.append(MATCH_URI);
        sb.append(matchId);
        sb.append(INNING);
        sb.append(inn);
        sb.append(VIEW);
        sb.append(view);
        return sb.toString();
    }
}
