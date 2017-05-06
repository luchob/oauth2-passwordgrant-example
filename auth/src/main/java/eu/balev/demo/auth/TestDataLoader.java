package eu.balev.demo.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import eu.balev.demo.auth.domain.User;
import eu.balev.demo.auth.repository.UserRepository;

/**
 * Loads some test data in the user repository.
 * 
 * @author LBalev
 *
 */
@Component
public class TestDataLoader  implements ApplicationRunner {

    private UserRepository userRepository;

    @Autowired
    public TestDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
    	
    	User user1 = new User(), user2 = new User(), user3 = new User();
    	
    	user1.setName("user1");
    	user1.setPasswordHash(new BCryptPasswordEncoder().encode("password1"));
    	user1.setAccountLocked(false);
    	user1.setPermissions(Arrays.asList("ROLE_READ_BOOK"));
    	
    	user2.setName("user2");
    	user2.setPasswordHash(new BCryptPasswordEncoder().encode("password2"));
    	user2.setAccountLocked(false);
    	user2.setPermissions(Arrays.asList("ROLE_READ_BOOK", "ROLE_WRITE_BOOK"));
    	
    	user3.setName("user3");
    	user3.setPasswordHash(new BCryptPasswordEncoder().encode("password3"));
    	user3.setAccountLocked(true);
    	
        userRepository.save(Arrays.asList(user1, user2, user3));
    }
}
