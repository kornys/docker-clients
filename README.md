# docker-clients


### Create image
```bash
make image
```

### Deploy image
```bash
docker run -d -p 4242:4242 --name messaging-clients docker-clients:latest
```

### Working with docker-clients

1. Create json data for request
    ```json
    {
       "command": ["cli-rhea-sender", "--address", "example", "--msg-content", "ahoj", "--broker", "admin:admin@server.fqdn:5672"],
       "count": 1
    }
    ```
2. Send data to docker-clients
    ```bash
    $ curl -X POST http://localhost:4242 -H "content-type: application/json" --data-binary @file.json
    # {"clients": ["uuid-of-client"]}
    ```
3. Get result of client
    ```bash
    curl -X GET http://localhost:4242 -H "content-type: application/json" --data-urlencode json="{\"id\": \"uuid-of-client\"}"
    ```
