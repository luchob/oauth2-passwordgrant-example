package eu.balev.demo.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * Configures the authorization server.
 * 
 * @author LBalev
 */
@Configuration
@EnableWebSecurity
@EnableAuthorizationServer 
class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	@Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		endpoints.authenticationManager(this.authenticationManager);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception {

		ClientDetails clientDetails = getClientDetails();
		ClientDetailsServiceBuilder<InMemoryClientDetailsServiceBuilder> serviceBuilder = new InMemoryClientDetailsServiceBuilder()
		{
			public ClientDetailsServiceBuilder<InMemoryClientDetailsServiceBuilder> addClientDetails(String clientId, ClientDetails value) {
				super.addClient(clientId, value);
				return this;
			}
		}.addClientDetails(clientDetails.getClientId(), clientDetails);
		
		clients.setBuilder(serviceBuilder);	
	}
	
	@Bean
    @ConfigurationProperties("clientdetails")
	BaseClientDetails getClientDetails()
	{
		return new BaseClientDetails();
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer)
			throws Exception {
		oauthServer.
			checkTokenAccess("permitAll()").
			allowFormAuthenticationForClients();
	}

}