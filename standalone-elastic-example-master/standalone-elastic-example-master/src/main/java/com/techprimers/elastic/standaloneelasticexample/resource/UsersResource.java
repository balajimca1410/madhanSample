package com.techprimers.elastic.standaloneelasticexample.resource;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/users")
public class UsersResource {

 

  

    @GetMapping("/insert/{id}")
    public String insert(@PathVariable final String id) throws IOException {
    	TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

        IndexResponse response = client.prepareIndex("employee", "id", id)
                .setSource(jsonBuilder()
                        .startObject()
                        .field("name", "Ajay")
                        .field("salary", 1200)
                        .field("teamName", "Development")
                        .endObject()
                )
                .get();
        return response.getResult().toString();
    }


    @GetMapping("/view/{id}")
    public Map<String, Object> view(@PathVariable final String id) throws UnknownHostException {
    	TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        GetResponse getResponse = client.prepareGet("employee", "id", id).get();
        System.out.println(getResponse.getSource());


        return getResponse.getSource();
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable final String id) throws IOException {
    	TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("employee")
                .type("id")
                .id(id)
                .doc(jsonBuilder()
                        .startObject()
                        .field("gender", "male")
                        .endObject());
        try {
            UpdateResponse updateResponse = client.update(updateRequest).get();
            System.out.println(updateResponse.status());
            return updateResponse.status().toString();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }
        return "Exception";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable final String id) throws UnknownHostException {
    	TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

        DeleteResponse deleteResponse = client.prepareDelete("employee", "id", id).get();

        System.out.println(deleteResponse.getResult().toString());
        return deleteResponse.getResult().toString();
    }
    @GetMapping("/search/{query}")
    public String search(@PathVariable final String query) throws UnknownHostException {
    	TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    	SearchResponse response = client.prepareSearch("software_index")
    	        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
    	        .setQuery(QueryBuilders.multiMatchQuery(query, "title","summary")) // Query
    	        .setFetchSource(new String[]{"title", "summary", "publisher"}, null)
    	        .get();
//   	SearchResponse  response1 = client.prepareSearch("software_index").get();
////        System.out.println(searchResponse.getResult().toString());
////        return searchResponse.getResult().toString();
	return response.toString();
   }
    
}
