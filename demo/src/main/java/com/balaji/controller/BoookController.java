package com.balaji.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balaji.Model.BookModel;
import com.balaji.repo.BookRepoInterface;

@RestController
@RequestMapping("/user")
public class BoookController {
	@Autowired
	BookRepoInterface bookRepo;
	
    @GetMapping("/search/{query}/{Id}")
    public String search(@PathVariable final String query,@PathVariable final int Id) throws IOException, JSONException {
    	TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    	SearchResponse response = client.prepareSearch("software_index")
    	        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
    	        .setQuery(QueryBuilders.multiMatchQuery(query, "title","summary")) // Query
    	        .setFetchSource(new String[]{"title", "summary", "publisher"}, null)
    	        .get();
    	
    	XContentBuilder builder = XContentFactory.jsonBuilder();
    	response.toXContent(builder, ToXContent.EMPTY_PARAMS);
    	JSONObject json = new JSONObject(builder.string());
//    	json.getJSONArray("")
    	  System.out.println("json data"+
    	  		json);
    	/*  SearchHits hits = null;
    	  if (response != null) {
              hits = response.getHits();
          }
          if (hits != null) {

              while (hits.iterator().hasNext()) {
                  hits.iterator().next();
              }
          }*/
//   	SearchResponse  response1 = client.prepareSearch("software_index").get();
////        System.out.println(searchResponse.getResult().toString());
////        return searchResponse.getResult().toString();
          
          for (SearchHit searchHit : response.getHits().getHits()) {
        	 // searchHit.getInnerHits().get("summary");
        	//  Map<String, SearchHitField> fields = searchHit.getFields();
            //  Object docDate = fields.get("doc_date").value();
             // System.out.println(docDate);
        	  BookModel bModel= new BookModel();
        	  bModel.setUserId(Id);
        	  bModel.setQuery(query);
              Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
              bModel.setSummary(sourceAsMap.get("summary").toString());
              bModel.setPublisher(sourceAsMap.get("summary").toString());
              bModel.setTitle(sourceAsMap.get("summary").toString());
              System.out.println(bModel.toString());
              bookRepo.save(bModel);
      	}
          
         
	return response.toString();
   }
    
    
  //read
  		@RequestMapping(value="/read/{id}")
  		public BookModel read(@PathVariable String id) {
  			System.out.println("my result"+bookRepo.findOne(id));
  			return bookRepo.findOne(id);
  		}
}
