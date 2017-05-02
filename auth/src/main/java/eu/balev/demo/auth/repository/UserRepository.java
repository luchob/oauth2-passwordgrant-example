package eu.balev.demo.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import eu.balev.demo.auth.domain.User;

/**
 * A CRUD repository for user storage and manipulation.
 * 
 * @author LBalev
 */
public interface UserRepository extends CrudRepository<User, Long> {
	
	/**
	 * Finds a user by name.
	 * 
	 * @param name the user name
	 * 
	 * @return a user if found
	 */
	Optional<User> findOneByName(String name);
	
}