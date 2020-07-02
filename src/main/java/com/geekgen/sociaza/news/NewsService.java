package com.geekgen.sociaza.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NewsService {

    @Value("${spring.newsapi.api.url}")
    private String apiUrl;

    @Autowired
    RestTemplate restTemplate;

    public NewsApi getAllNews(){

        return restTemplate.getForObject(apiUrl, NewsApi.class);

     //   NewsApi call= restTemplate.getForObject(apiUrl, NewsApi.class);
     //   return call;
        //System.out.println(call.getBody())
       // return this.restTemplate.getForObject(apiUrl, NewsApi.class);
    }

    public List<Article> getArticles(){
        NewsApi news = getAllNews();
        if (news != null) return news.getArticles();
        return null;
    }

}
