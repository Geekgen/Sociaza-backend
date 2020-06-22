package com.geekgen.sociaza.imagehandling;


import com.geekgen.sociaza.posts.PostService;
import com.geekgen.sociaza.registration.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.geekgen.sociaza.posts.Post;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PostService postService;

    public Image storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Image imageFile = new Image(fileName, file.getContentType(), file.getBytes());

            return imageRepository.save(imageFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Image getFile(String fileId) {
        return imageRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    public Image saveImage(Image image){
        return imageRepository.save(image);
    }

    public Image getImageByUser(User user) {
        return getFile(user.getImage().getId());
    }

    public Image getImageForPost(long postId) {
        Post post = postService.getByID(postId).get();
        return getFile(post.getUser().getImage().getId());
    }
}


