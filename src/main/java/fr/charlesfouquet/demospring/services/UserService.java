package fr.charlesfouquet.demospring.services;

import fr.charlesfouquet.demospring.beans.User;
import fr.charlesfouquet.demospring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> read() {
        return userRepository.findAll();
    }

    public void createUser(User user) {
        user.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
