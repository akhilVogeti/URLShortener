# URL shortener
A spring boot application that will take in a long URL and returns a randomised short URL or a hash-based short URL as per user-configurable strategy. It also returns the actual URL when the generated short URL is given. It utilizes MySQL for persistence, Redis for caching, & Docker for containerization.

## APIs
1. Method: POST  
   Path: https://localhost:8080/api/v1/shortener/shorten
    Body: { "actualUrl" : " "} JSON body with actual long URL as input.   
   Response: a JSON with id, generated short URL, actual URL, created time, and expiry.

2. Method: GET  
   Path: https://localhost:8080/api/v1/shortener/expand/{shortUrl}  
   Body: No body.   
   Response: a JSON with id, actual URL fetched, short URL, created time, expiry.

### Pre-requisites
Docker to run the dockerised application, & Postman for using the APIs.

#### Run
````
mvn clean install  
docker compose up
````




