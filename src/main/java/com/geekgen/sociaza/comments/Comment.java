package com.geekgen.sociaza.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekgen.sociaza.audit.AuditModel;
import com.geekgen.sociaza.posts.Post;
import com.geekgen.sociaza.registration.User;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment extends AuditModel {
    /**
     *
     */
    private static final long serialVersionUID = -2646321401733102105L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "body")
    private String body;

    @ManyToOne
    @JsonIgnore
    private Post post;

    @ManyToOne
    private User user;

    public Comment() {
    }

    public Comment(Long id, String body, Post post,User user) {
        this.id = id;
        this.body = body;
        this.post = post;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {this.id = id;}

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
}
