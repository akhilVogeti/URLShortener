# URL shortener
A Spring Boot application that takes in a long URL and returns a randomized short URL or a hash-based short URL as per a user-configurable strategy. It also returns the actual URL when the generated short URL is provided. The application utilizes MySQL for persistence, Redis for caching, and Docker for containerization.

## Pre-requisites
- [Docker](https://www.docker.com/) to run the Dockerized application
- [Postman](https://www.postman.com/) for using the APIs

## Running the application

First, clone the repository and navigate to the project directory:

```bash
git clone https://github.com/your-username/url-shortener.git
cd url-shortener
```

To start the application, run the following command:

```bash
docker-compose up
```

## APIs

### 1. Shorten a URL

Use the following curl command to shorten a URL:

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"actualUrl": "https://www.example.com/"}' \
  http://localhost:8080/api/v1/shortener/shorten
```

Replace `https://www.example.com/` with your desired long URL.

### 2. Expand a short URL

To expand a short URL, use the following curl command:

```bash
curl -X GET \
  http://localhost:8080/api/v1/shortener/expand/{shortUrl}
```

Replace `{shortUrl}` with the generated short URL.

### Example responses

A successful response for shortening a URL looks like this:

```json
{
  "id": 1,
  "shortUrl": "a1b2c3d4e5f6g7h8i9j0k1l2m3",
  "actualUrl": "https://www.example.com/",
  "createdTime": "2022-01-01T12:00:00Z",
  "expiry": "2022-01-02T12:00:00Z"
}
```

An example response for expanding a short URL:

```json
{
  "id": 1,
  "actualUrl": "https://www.example.com/",
  "shortUrl": "a1b2c3d4e5f6g7h8i9j0k1l2m3",
  "createdTime": "2022-01-01T12:00:00Z",
  "expiry": "2022-01-02T12:00:00Z"
}
```

## Contributing

To contribute to the project, follow these steps:

1. Fork the repository
2. Create a new branch
3. Make your changes
4. Commit and push your changes
5. Create a pull request

## License

This project is licensed under the MIT license.
