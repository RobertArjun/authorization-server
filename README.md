# Authorization-server
Auth Server using Oauth2

## OAuth defines four roles:

  ### resource owner
      An entity capable of granting access to a protected resource.
      When the resource owner is a person, it is referred to as an
      end-user.

   ### resource server
      The server hosting the protected resources, capable of accepting
      and responding to protected resource requests using access tokens.

   ### client
      An application making protected resource requests on behalf of the
      resource owner and with its authorization.  The term "client" does
      not imply any particular implementation characteristics (e.g.,
      whether the application executes on a server, a desktop, or other
      devices).

   ### authorization server
      The server issuing access tokens to the client after successfully
      authenticating the resource owner and obtaining authorization.

   The interaction between the authorization server and resource server
   is beyond the scope of this specification.  The authorization server
   may be the same server as the resource server or a separate entity.
   A single authorization server may issue access tokens accepted by
   multiple resource servers.

#### pom.xml
``` 
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.security.experimental</groupId>
			<artifactId>spring-security-oauth2-authorization-server</artifactId>
			<version>0.0.1</version>
		</dependency>
	</dependencies> 
  ```

#### AuthorizationServerConfig
##### 1. import Oauth2
```
@Configuration
@EnableWebSecurity
@Import(OAuth2AuthorizationServerConfiguration.class)
public class AuthorizationServerConfig {
```
This is intial version of Oauth2 so we have to import the class 

##### 1. create userdetails service
```
@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager();
		UserDetails user = User.withUsername("robert").password("rob!1").authorities("read").build();

		uds.createUser(user);

		return uds;
	}
```
##### 3. Create password encoder
```
@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
```
we are using NoOpPasswordEncoder but in real-time we should not use the the NoOpPasswordEncoder

##### 4. Create RegisteredClientRepository to register client
```
@Bean
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient client = RegisteredClient.withId("client").clientId("client").clientSecret("secret")
				.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.redirectUri("http://localhost:8080/authorized").scope("read").build();

		return new InMemoryRegisteredClientRepository(client);
	}
```
##### 5. Create keyManager to generate random key
```
@Bean
	public KeyManager keyManager() {
		return new StaticKeyGeneratingKeyManager();

	}
```

#### Testing
##### 1. Get Authorize Code
1. Open chrome run the following url
```
http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=read
```
2. It will redirect to the spring security login page
   then we will provide credenctial 
   ``` Username("robert") & password("rob!1") ```
3. After succcessful login it will redirect to the url which one we have configured in registeredClientRepository bean i.e http://localhost:8080/authorized
   and append the code end of the url 
   ```
   http://localhost:8080/authorized?code=sy-fJYFhgkSwxN3WViCp8jbwxI5UyAyawmRd2oedU3o%3D
   ```
##### 2. Get Auth Token
1. Open the postman
```
http://localhost:8080/oauth2/token?grant_type=authorization_code&code=sy-fJYFhgkSwxN3WViCp8jbwxI5UyAyawmRd2oedU3o%3D&scope=read
```
Granttype and code which are set in registeredClientRepository authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
code parameter we should pass the access code which one got from get Authorize code section

2. we  set as basic authentication so should be set in Autherization tab
``` 
Username
client
Password
secret 
```
Sample Output
{
    "access_token": "eyJraWQiOiJmMWQ1OGE5Mi01ZjE0LTQ5ZDUtYjIwZS00YzQ5MWIyMWE2Y2EiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJyb2JlcnQiLCJhdWQiOiJjbGllbnQiLCJuYmYiOjE2MTU1MzIzNzYsInNjb3BlIjpbInJlYWQiXSwiaXNzIjoiaHR0cHM6XC9cL29hdXRoMi5wcm92aWRlci5jb20iLCJleHAiOjE2MTU1MzU5NzYsImlhdCI6MTYxNTUzMjM3NiwianRpIjoiZWRlODljZWQtNTI3MC00ZTRkLTkzZmUtZTQ0ZTExNTFkM2NlIn0.nJN80bSSSNaOksdZEdznREjtraHRSHZRYU3VtGdAapTQS95-xMyzZHMvwAujtv0MGC44fE7dgyFtDnA8h0xtkZ7abVW9hThaeEG2tkEsetBjgcv8MW_feKmWe16YMI_e0tXnOZcl8CXnUZ2uyyUm0DJlUP5c0hJ4UsPGdL8URnGgyNnlVbWIm33KaBQiclvuRBK_8BPSY3EITxrE4lsGmtZzo4vfN5kDoaEUQ56zIopWPWtxuKugBPwT4aMNE2eWqIOqJuFGcF1kc8MwfIhhFQ-OvfcXnwA6T8-G69xKJKtBWJV7XXi48RboZJPRZHMi04I7N1mfoT_eRWpbUlzpRQ",
    "scope": "read",
    "token_type": "Bearer",
    "expires_in": "3600"
}

to validate the jwt token paste the token in jwt.io

##### 3. Get Auth Jwks
```
http://localhost:8080/oauth2/jwks
```
