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

    @Value("${spring.newsapi.api.key}")
    private String apiKey;

    @Autowired
    RestTemplate restTemplate;

    public NewsApi getAllNews(String newsType){
        String urlApiFinal = new String();
        switch(newsType) {
            case "general":
                urlApiFinal = apiUrl +  "country=br&category=general&" + apiKey;
                break;
            case "sport":
                urlApiFinal = apiUrl +  "country=br&category=sport&" + apiKey;
                break;
            case "health":
                urlApiFinal = apiUrl +  "country=br&category=health&" + apiKey;
                break;
            case "entertainment":
                urlApiFinal = apiUrl +  "country=br&category=entertainment&" + apiKey;
                break;
            case "technology":
                urlApiFinal = apiUrl +  "country=br&category=technology&" + apiKey;
                break;
            case "se":
                urlApiFinal = apiUrl +  "country=se&category=general&" + apiKey;
                break;
            default:
                urlApiFinal = apiUrl +  "country=br&category=general&" + apiKey;

        }

        return restTemplate.getForObject(urlApiFinal, NewsApi.class);
    }


    public List<Article> getArticles(String newsType){
        NewsApi news = getAllNews(newsType);
        if (news != null) return news.getArticles();
        return null;
    }

}
