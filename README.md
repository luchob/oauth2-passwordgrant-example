# Introduction

This gradle multiproject demonstrates a very minimalistic REST API, access to which is controlled with OAuth 2.0 password grant. 
This might be useful in cases in which the client collects the user name and the password of the resource owners. The project consists of two independent spring-boot applications which communicate over HTTP:

* An authorization server - it generates and verifies authentication tokens for users that attempt to access a REST resource. The user data is stored in H2 database currently. This may be easily adopted to PostgreSQL, MySQL or other relational DB. The token storage is in-memory.
* Resource server - serves REST resources protected by access tokens.

In the current setup the client may request its token from the authorization server. Then it may try to use the token at the resource server. The resource server verifies the access tokens received from the clients against the authorization server. To get started simply clone the project and read on.

# Running

To test the samples the authorization and resource servers should be started in parallel. The project is shipped with gradle wrapper so this should be fairly easy. No additional software is needed.

## Starting and using the authorization server

Navigate to the project root, e.g. `oauth2-passwordgrant-example` and execute `gradlew :auth:bootRun`. The auth server should be up and running at [http://localhost:8080](http://localhost:8080) by default. A REST client like Postman can be used for testing. The server is shipped with two demo users:

* user `user1` with password `password1`, the user account is not locked;
* user `user2` with password `password2`, the user account is locked;
* a trusted client with id `foo` which can access grant access to user data;

The endpoint for retrieving access tokens via POST request is [http://localhost:8080/oauth/token](http://localhost:8080/oauth/token). All access to the OAuth endpoints require basic authentication in which the password is empty and the client id is foo. This may sound strange at first but it is something in the Spring implementation details. For more information see [here](https://github.com/spring-projects/spring-security-oauth/issues/1058). The authentication header will look like this:

`Authorization: Basic Zm9vOg==`

### Get an access token for user1

The following parameters should be supplied:

```
grant_type=password
username=user1
password=password1
```
The following HTTP header should be present:

`Authorization: Basic Zm9vOg==`

The HTTP request is POST.

A sample successful response would be:

```
{
  "access_token": "1318beef-95c1-4fe0-b9ad-dedcb9dad7e4",
  "token_type": "bearer",
  "refresh_token": "5e5f511f-8065-4ec6-9a86-200b666f7538",
  "expires_in": 1799,
  "scope": "read write"
}
```

You may check the validity of the token at the following endpont: [http://localhost:8080/oauth/check_token](http://localhost:8080/oauth/check_token). A parameter with name `token` and value the token itself should be provided. Authorization header is required, like in the call above. The request is POST. A sample response would be:

```
{
  "exp": 1493574609,
  "user_name": "user1",
  "client_id": "foo",
  "scope": [
    "read",
    "write"
  ]
}
```

### Try to get an access token for user2

The following parameters should be supplied:

```
grant_type=password
username=user2
password=password2
```

The following HTTP header should be present:

`Authorization: Basic Zm9vOg==`

The HTTP request is POST.

A sample response would be:

```
{
  "error": "invalid_grant",
  "error_description": "User account is locked"
}
```

### Try to get an access token for a user that does not exist

The following parameters may be supplied:

```
grant_type=password
username=luchob
password=top_secret
```

The following HTTP header should be present:

`Authorization: Basic Zm9vOg==`

The HTTP request is POST.

A sample response would be:

```
{
  "error": "invalid_grant",
  "error_description": "Bad credentials"
}
```

## Starting and using the resource server

Navigate to the project root, e.g. `oauth2-passwordgrant-example` and execute `gradlew :res:bootRun`. The resource server should be up and running at [http://localhost:9999](http://localhost:9999). A REST client like Postman can be used for testing. 

### Getting a resource without token

Execute a GET request to the following URL: [http://localhost:9999/books/122](http://localhost:9999/books/122). Similar response may be expected:

```
{
  "error": "unauthorized",
  "error_description": "Full authentication is required to access this resource"
}
```

### Getting a resource with token

Acquire a valid access token form the authorization server as described above. If the token is `XYZ`, for example, execute a GET request to the following URL: [http://localhost:9999/books/122](http://localhost:9999/books/122), providing the following HTTP header:

`Authorization: bearer XYZ`

Similar output is expected:

```
{
  "title": "Various thoughts about the ID 122",
  "id": "122"
}
```

# Explore the project in Eclipse

The Eclipse plugin for gradle is enabled in the build. To import the projects into Eclipse:

* Navigate to the project root, e.g. `springboot-oauth2-example`
* Execute `gradlew eclipse`
* Open your Eclipse IDE and import the projects as existing Java projects
