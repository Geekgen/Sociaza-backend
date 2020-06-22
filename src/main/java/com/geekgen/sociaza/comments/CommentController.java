package com.geekgen.sociaza.comments;

import java.util.List;

import javax.validation.Valid;

import com.geekgen.sociaza.authentication.AuthService;
import com.geekgen.sociaza.posts.Post;
import com.geekgen.sociaza.posts.PostService;
import com.geekgen.sociaza.registration.User;
import com.geekgen.sociaza.registration.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;


    @GetMapping("posts/{postId}/comments")
    public List<Comment> getAllCommentsByPostId(@PathVariable Long postId) {
        return commentService.getAllCommentsByPostId(postId);
    }

    @GetMapping("/posts/comment/email")
    public String getEmail(){
        return authService.getLoggedInUserEmail();
    }

    @GetMapping("posts/{postId}/comments/{id}")
    public Comment getCommentsById(@PathVariable Long id) {
       return commentService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Could not find post with id " + id.toString()));
    }

    @DeleteMapping("posts/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
    }

    @PostMapping("posts/{postId}/comments/{email}")
    public Comment postComment(@RequestBody Comment comment, @PathVariable Long postId, @PathVariable String email) {
        Post post = postService.getByID(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find post with id " + postId.toString()));
        User user = userService.findUserByEmail(email);
        comment.setUser(user);
        comment.setPost(post);
        return commentService.create(comment);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public Comment updateComment(@PathVariable (value = "postId") Long postId,
                                 @PathVariable (value = "id") Long id,
                                 @Valid @RequestBody Comment updatedComment) throws Exception {
        return commentService.updateComment(postId,id,updatedComment);
    }
}
