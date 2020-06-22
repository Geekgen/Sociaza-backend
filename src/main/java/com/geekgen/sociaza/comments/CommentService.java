package com.geekgen.sociaza.comments;

import java.util.List;
import java.util.Optional;

import com.geekgen.sociaza.posts.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;


    public List<Comment> getAllComment() {
       return commentRepository.findAll();
    }

    public List<Comment> getAllCommentsByPostId(Long postId){
        return  commentRepository.findAllByPostId(postId);
    }

    public Optional<Comment> getById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }


    public Comment updateComment( Long postId,Long id,Comment newComment) throws Exception {
        if(!postRepository.existsById(postId)) {
            throw new Exception("PostId " + postId + " not found");
        }

        return commentRepository.findById(id).map(comment -> {
            comment.setBody(newComment.getBody());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new Exception("id " + id + "not found"));

    }

}
