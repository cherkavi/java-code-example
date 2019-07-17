import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.net.MalformedURLException;
// IMPORTANT: slf4j-nop-1.5.5 - must be added
public class FirstExample{
  public static void main(String[] args) throws MalformedURLException, SolrServerException {
    SolrServer solr = new CommonsHttpSolrServer("http://localhost:8983/solr");

    // http://localhost:8983/solr/select?q=name:*G900*&sort=id%20asc&rows=6&start=5
    ModifiableSolrParams params = new ModifiableSolrParams();
    
    params.set("q", "name:*G900*");
    params.set("sort", "id asc");
    params.set("rows", "6");
    params.set("start", "5");

    QueryResponse response = solr.query(params);
    System.out.println("response = " + response);
  }
}