package com.geekgen.sociaza.news;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;

@Service
public class NewsService {

    @Value("${spring.newscatcher.api.url}")
    private String apiUrl;
    private RestOperations restTemplate;

    public News getAllNews(){
        return this.restTemplate.getForObject(apiUrl, News.class);
    }

    public List<Article> getArticles(){
        News news = getAllNews();
        if (news != null) return news.getArticles();
        return null;
    }

}
