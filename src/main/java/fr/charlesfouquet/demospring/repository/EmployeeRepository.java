package fr.charlesfouquet.demospring.repository;

import fr.charlesfouquet.demospring.beans.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByEmail(String email);
}
