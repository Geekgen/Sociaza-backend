package com.geekgen.sociaza.posts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class PostArchive {

    @Autowired
    PostService postService;

    @Scheduled(fixedDelay = 30 * 1000)
    public void archivePastPosts() {
        //List<Post> databasePosts = postService.getAll();
        List<Post> databasePosts = new ArrayList<Post>() ;
        LocalDateTime currentDateTime = LocalDateTime.now();


        for (Post post : databasePosts) {

            if (post.getStatus() != Post.Status.ARCHIVED) {
                // date needs to be cut, since it contains also default (incorrect) value for
                // time
                String postDate = post.getDate().substring(0, 10);
                LocalDate postDatePart = LocalDate.parse(postDate);

                // time needs to be cut, since there is from-to information in database. Using
                // just from value.
                String completePostTime = post.getTime();
                int hyphenIndex = completePostTime.indexOf("-");
                String postTime = completePostTime.substring(0, hyphenIndex);

                DateTimeFormatter parser = DateTimeFormatter.ofPattern("h[:mm]a");
                LocalTime postTimePart = LocalTime.parse(postTime, parser);

                LocalDateTime postDateTime = LocalDateTime.of(postDatePart, postTimePart);

                if (currentDateTime.isAfter(postDateTime)) {
                    post.setStatus(Post.Status.ARCHIVED);
                    postService.save(post);
                }
            }
        }
    }
}
