package fr.charlesfouquet.demospring;

import fr.charlesfouquet.demospring.beans.User;
import fr.charlesfouquet.demospring.services.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTest {
	@Autowired
	private UserService userService;

	@Test
	@Order(1)
	public void testRegister() {
		// Arrange
		User userForTest = new User();
		userForTest.setNom("Test");
		userForTest.setPrenom("Compte");
		userForTest.setEmail("test@test.fr");
		userForTest.setPassword("test");

		// Act
		userService.createUser(userForTest);

		// Assert
		assertNotNull(userService.findByEmail(userForTest.getEmail()));
	}

	@Test
	@Order(2)
	public void testLogin() {
		// Arrange
		User userForTest = new User();
		userForTest.setEmail("test@test.fr");
		userForTest.setPassword("test");
		
		// Act
		User result = userService.findByEmail(userForTest.getEmail());
		boolean foundState = false;
		if (BCrypt.checkpw(userForTest.getPassword(), result.getPassword())) {
			foundState = true;
		}
		
		// Assert
		assertNotNull(result);
		assertTrue(foundState);
	}

	@Test
	@Order(3)
	public void testDelete() {
		// Arrange
		User userForTest = new User();
		userForTest.setEmail("test@test.fr");
		userForTest.setPassword("test");

		// Act
		User result = userService.findByEmail(userForTest.getEmail());
		boolean foundState = false;
		if (BCrypt.checkpw(userForTest.getPassword(), result.getPassword())) {
			userService.delete(result);
			foundState = true;
		}

		User result2 = userService.findByEmail(userForTest.getEmail());

		// Assert
		assertNull(result2);
		assertTrue(foundState);
	}
}
