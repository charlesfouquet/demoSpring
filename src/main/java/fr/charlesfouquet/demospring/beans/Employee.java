package fr.charlesfouquet.demospring.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.NotFound;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Le nom ne peut pas être nul !")
    @Column(name = "lastname", length = 50)
    @Size(min = 5, max = 50, message = "Le nom n''a pas la longueur requise ({min} à {max} caractères)")
    private String lastName;
    @NotBlank(message = "Le prénom ne peut pas être nul !")
    @Column(name = "firstname", length = 50)
    @Size(min = 5, max = 50, message = "Le prénom n''a pas la longueur requise ({min} à {max} caractères)")
    private String firstName;
    @Email
    @NotBlank(message = "L''email ne peut pas être nul !")
    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;

    public Employee() {
    }

    public Employee(String lastName, String firstName, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public Employee(int id, String lastName, String firstName, String email) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
