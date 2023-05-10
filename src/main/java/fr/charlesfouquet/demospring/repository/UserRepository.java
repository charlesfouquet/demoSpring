package fr.charlesfouquet.demospring.repository;

import fr.charlesfouquet.demospring.beans.Employee;
import fr.charlesfouquet.demospring.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
