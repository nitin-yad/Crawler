/**
 *@author nitin.yadav
 */

package mp.crawler.tests;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParserTest {

    private static final String HTML_COMM_CLASS = "commentary-event";
    private static final String HTML_COMM_OVER_CLASS = "commentary-overs";
    private static final String HTML_COMM_TEXT_CLASS = "commentary-text";

    public static void main(String[] args) {
        String htmlString = "<html><body></body> </html>";
        Document document = Jsoup.parse(htmlString);
        Elements elms = document.getElementsByClass(HTML_COMM_CLASS);
        String prevOver = null;
        for (Element elm : elms) {
            Elements over = elm.getElementsByClass(HTML_COMM_OVER_CLASS);
            Elements text = elm.getElementsByClass(HTML_COMM_TEXT_CLASS);
            if (over.size() > 0)
                System.out.println(over.text());
            else
                System.out.println(prevOver);
            System.out.println(text.text());
            prevOver = over.text();
        }
    }
}
