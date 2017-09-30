/**
 *@author nitin.yadav
 */

package mp.crawler.dtos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

@Entity(name = "preprocessing")
@Table(name = "preprocessing.preprocessing")
@NamedQuery(name = "getMatchIdByMatchStatus", query = "select d.batchId from facPurchase d where d.orderId = :orderId and d.isActive = :isActive")
public class PreProcessingDTO implements Serializable {

    private static final long serialVersionUID = -1440664772444629556L;
    private Long id;
    private Integer match_status;
    private Integer processing_status;
    private Integer match_type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMatch_status() {
        return match_status;
    }

    public void setMatch_status(Integer match_status) {
        this.match_status = match_status;
    }

    public Integer getProcessing_status() {
        return processing_status;
    }

    public void setProcessing_status(Integer processing_status) {
        this.processing_status = processing_status;
    }

    public Integer getMatch_type() {
        return match_type;
    }

    public void setMatch_type(Integer match_type) {
        this.match_type = match_type;
    }

}
