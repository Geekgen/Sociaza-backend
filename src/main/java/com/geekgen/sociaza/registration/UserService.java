package com.geekgen.sociaza.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.geekgen.sociaza.configuration.EmailServiceImpl;

@Service()
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    EmailServiceImpl emailService;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void register(User user) {
        String encryptedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPass);
        userRepository.save(user);

        String mailFromUser = user.getEmail();
        //emailService.sendSimpleMessage(mailFromUser, "Sociaza registration" ,
                //"You have been successfully registered to Sociaza.");

    }
}
