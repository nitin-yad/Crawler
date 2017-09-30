/**
 *@author nitin.yadav
 */

package mp.crawler.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CrawlStat implements Serializable {

    private static final long serialVersionUID = 7724481522894152957L;

    private Long totalProcessedPages = 0L;
    private Long totalLinks = 0L;
    private Long totalValidMatchIds = 0L;
    private List<Long> matchIds = new ArrayList<Long>();

    public Long getTotalProcessedPages() {
        return totalProcessedPages;
    }

    public void setTotalProcessedPages(Long totalProcessedPages) {
        this.totalProcessedPages = totalProcessedPages;
    }

    public Long getTotalLinks() {
        return totalLinks;
    }

    public void setTotalLinks(Long totalLinks) {
        this.totalLinks = totalLinks;
    }

    public Long getTotalValidMatchIds() {
        return totalValidMatchIds;
    }

    public void setTotalValidMatchIds(Long totalValidMatchIds) {
        this.totalValidMatchIds = totalValidMatchIds;
    }

    public List<Long> getMatchIds() {
        return matchIds;
    }

    public void setMatchIds(List<Long> matchIds) {
        this.matchIds = matchIds;
    }

    public void incTotalLinks(int count) {
        this.totalLinks += count;
    }

    public void incTotalProcessedPages() {
        this.totalProcessedPages++;
    }

    public void incTotalValidMatchIds() {
        this.totalValidMatchIds++;
    }

    public void addToMatchIdList(Long matchId) {
        this.matchIds.add(matchId);
    }

}
