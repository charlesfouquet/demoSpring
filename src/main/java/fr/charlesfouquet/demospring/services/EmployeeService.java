package fr.charlesfouquet.demospring.services;

import fr.charlesfouquet.demospring.beans.Employee;
import fr.charlesfouquet.demospring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean create(Employee employee) {
        if (employeeRepository.findByEmail(employee.getEmail()) == null) {
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    public List<Employee> read() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findByID(int id) {
        return employeeRepository.findById(id);
    }

    public void update(int id, Employee employee) {
        Optional<Employee> request = employeeRepository.findById(id);
        if (request.isPresent()) {
            Employee baseEmployee = request.get();
            baseEmployee.setFirstName(employee.getFirstName());
            baseEmployee.setLastName(employee.getLastName());
            baseEmployee.setEmail(employee.getEmail());
            employeeRepository.save(baseEmployee);
        }
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}