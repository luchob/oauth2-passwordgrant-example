package eu.balev.demo.auth.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;

/**
 * Defines a client details service builder for the in memory storage.
 * 
 * @author LBalev
 */
class InMemoryClientDetailsServiceBuilder
		extends
		ClientDetailsServiceBuilder<org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder> {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private Map<String, ClientDetails> clientDetailsStore = new HashMap<String, ClientDetails>();

	/**
	 * Adds a client in the in memory store.
	 * 
	 * @param clientDetails
	 *            the client details.
	 */
	InMemoryClientDetailsServiceBuilder addClient(ClientDetails clientDetails) {

		String clientId = Objects.requireNonNull(clientDetails.getClientId(),
				"Client id can't be null!");

		if (clientDetailsStore.containsKey(clientId)) {
			LOGGER.error(
					"The client with ID [{}] is already defined in the client store, we will not override it...",
					clientId);
		} else {
			LOGGER.debug("Registering client with id [{}].", clientId);
			clientDetailsStore.put(clientId, clientDetails);
		}
		
		return this;
	}

	/**
	 * Add all client details to the store one by one. 
	 * 
	 * @param clientDetails the client details that should be added.
	 */
	InMemoryClientDetailsServiceBuilder addClients(ClientDetails... clientDetails) {
		Stream.of(clientDetails).forEach(cd -> addClient(cd));
		return this;
	}

	@Override
	protected ClientDetailsService performBuild() {
		InMemoryClientDetailsService clientDetailsService = new InMemoryClientDetailsService();
		clientDetailsService.setClientDetailsStore(clientDetailsStore);
		return clientDetailsService;
	}

}
