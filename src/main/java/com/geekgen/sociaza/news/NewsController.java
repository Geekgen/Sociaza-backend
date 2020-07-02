package com.geekgen.sociaza.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;




    @GetMapping(path = "/news",
            produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Article>> getNews(){
        List<Article> news = newsService.getArticles();
        if (news != null) {
            return new ResponseEntity<List<Article>>(news, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Article>>(HttpStatus.NOT_FOUND);
        }
    }

}
