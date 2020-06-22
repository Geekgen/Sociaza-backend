package com.geekgen.sociaza.registration;

import com.geekgen.sociaza.registration.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface    UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
