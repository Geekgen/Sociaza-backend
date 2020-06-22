package com.geekgen.sociaza.imagehandling;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.geekgen.sociaza.authentication.AuthService;
import com.geekgen.sociaza.registration.User;
import com.geekgen.sociaza.registration.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {

        User user = userService.findUserByEmail(authService.getLoggedInUserEmail());

        Image imageFile = imageService.storeFile(file);
        imageFile.setUser(user);
        user.setImage(imageFile);
        imageService.saveImage(imageFile);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
                .path(imageFile.getId()).toUriString();

        return new UploadFileResponse(imageFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    public static Logger getLogger() {
        return logger;
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile() {

        User user = userService.findUserByEmail(authService.getLoggedInUserEmail());
        Image imageFile = imageService.getImageByUser(user);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageFile.getFileName() + "\"")
                .body(new ByteArrayResource(imageFile.getData()));
    }

    @GetMapping(path = { "/getImage" })
    public Image getImage() throws IOException {
        User user = userService.findUserByEmail(authService.getLoggedInUserEmail());
        return imageService.getImageByUser(user);
    }

    @GetMapping(path = { "/{id}/getImage" })
    public Image getServiceProviderImage(@PathVariable Long postId) throws IOException {
        return imageService.getImageForPost(postId);
    }


}




