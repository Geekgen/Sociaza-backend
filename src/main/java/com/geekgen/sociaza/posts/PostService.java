package com.geekgen.sociaza.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geekgen.sociaza.configuration.EmailServiceImpl;
import com.geekgen.sociaza.registration.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
    @TODO Autowire the PostRepository and use it to implement all the service methods.
 */
@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    EmailServiceImpl emailService;

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public List<Post> getActiveAndFullPosts() {
        List<Post> posts = postRepository.findAll();
        List<Post> userPosts = posts.stream()
                .filter(p -> p.getStatus()!= Post.Status.ARCHIVED)
                .collect(Collectors.toList());
        return userPosts;
    }

    public List<Post> getPostsByServiceProviderEmail(String email) {
        List<Post> posts = postRepository.findAll();
        List<Post> userPosts = posts.stream()
                .filter(p -> p.getUser().getEmail().equals(email))
                .collect(Collectors.toList());
        return userPosts;
    }

    public Optional<Post> getByID(Long id) {
        return postRepository.findById(id);
    }

    public Post save(Post post) {
        if (post.getStatus() != Post.Status.ARCHIVED) {
            post.setStatus(Post.Status.ACTIVE);
        }
        return postRepository.save(post);
    }

    public Post update(Long id, Post postRequest) throws Exception {
        return postRepository.findById(id).map(post -> {
            post.setAttendeesLimit(postRequest.getAttendeesLimit());
            post.setDescription(postRequest.getDescription());
            return postRepository.save(post);
        }).orElseThrow(() -> new Exception("PostId " + id + " not found"));
    }

    public void deleteById(Long id) {
        // @TODO delete the post by id
        Optional<Post> postToDelete = postRepository.findById(id);
        Post post = postToDelete.get();

        List<User> attendees = post.getAttendees();
        String postName = post.getName();
        String postDate = post.getDate().substring(0, 10);
        String postTime = post.getTime();

        for(User attendee : attendees) {
            String userEmail = attendee.getEmail();
            emailService.sendSimpleMessage(userEmail, "event cancellation" ,
                    "We are sorry to inform you that event " + postName
                            + " that should take place on " + postDate
                            +  " " + postTime + " has been cancelled.");

            List<Post> services = attendee.getBookedServices();
            services.remove(post);
            attendee.setBookedServices(services);
        }

        postRepository.deleteById(id);
    }

    public Post updatePostWithAttendeeInfo(Post post, User attendee) {
        if (post.getStatus() == Post.Status.ACTIVE) {
            List<User> attendees = post.getAttendees();
            attendees.add(attendee);
            post.setAttendees(attendees);
            updateStatus(post);
        }
        return postRepository.save(post);
    }

    public User updateUserWithBookedService(User user, Post post) {
        if (post.getStatus() == Post.Status.ACTIVE) {
            List<Post> services = user.getBookedServices();
            services.add(post);
            user.setBookedServices(services);
        }
        return user;
    }

    public void updateStatus(Post post) {
        if (post.getAttendees().size() == post.getAttendeesLimit()) {
            post.setStatus(Post.Status.FULL);
        }
    }
}
