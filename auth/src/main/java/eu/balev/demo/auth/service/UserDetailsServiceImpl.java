package eu.balev.demo.auth.service;

import static java.util.stream.Collectors.toList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eu.balev.demo.auth.repository.UserRepository;

/**
 * A user service backed up by a CRUD user repository.
 * 
 * @author LBalev
 */
@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		LOGGER.debug("Looking for user by name [{}]... {} ", username);
		
		eu.balev.demo.auth.domain.User user = 
				userRepo.
				findOneByName(username).
				orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
		
		LOGGER.debug("User with name [{}] was found. Is Locked? {} ", username, user.isAccountLocked());
		
		return new User(username, 
				user.getPasswordHash(), 
				true, 
				true, 
				true, 
				!user.isAccountLocked(), 
				user.
					getPermissions().
					stream().
					map(this::map).
					collect(toList()));
	}
	
	private GrantedAuthority map(String permission)
	{
		return new SimpleGrantedAuthority(permission);
	}
}