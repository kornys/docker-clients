package clients_listener;


import io.enmasse.systemtest.executor.Executor;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;

public class HttpClientsListener extends AbstractVerticle {
    private HttpServer httpServer = null;
    private Logger logger = LoggerFactory.getLogger(HttpClientsListener.class);
    private HashMap<String, Executor> executors = new HashMap<>();

    @Override
    public void start() {
        vertx = Vertx.vertx();
        httpServer = vertx.createHttpServer();
        httpServer.requestHandler(request -> {
            switch (request.method()) {
                case POST:
                    postHandler(request);
                    break;
                case GET:
                    getHandler(request);
                    break;
                case DELETE:
                    deleteHandler(request);
                    break;
            }
        });
        int port = 4242;
        httpServer.listen(port);
        logger.info("Client listener listening on port: {}", port);
    }

    private void deleteHandler(HttpServerRequest request) {
        request.bodyHandler(handler -> {
            JsonObject json = handler.toJsonObject();
            logger.info("Incoming DELETE request: {}", json);
            String clientUUID = json.getString("id");

            Executor executor = executors.get(clientUUID);
            executor.stop();
            executors.remove(clientUUID);

            HttpServerResponse response = successfulResponse(request);
            JsonObject responseData = new JsonObject();
            responseData.put("ecode", executor.getRetCode());
            responseData.put("stdOut", executor.getStdOut());
            responseData.put("stdErr", executor.getStdErr());
            responseData.put("isRunning", executor.isRunning());
            response.end(responseData.toString());

        });
    }

    private void getHandler(HttpServerRequest request) {
        request.bodyHandler(handler -> {
            JsonObject json = handler.toJsonObject();
            logger.info("Incoming GET request: {}", json);
            String clientUUID = json.getString("id");

            Executor executor = executors.get(clientUUID);

            HttpServerResponse response = successfulResponse(request);
            JsonObject responseData = new JsonObject();
            responseData.put("ecode", executor.getRetCode());
            responseData.put("stdOut", executor.getStdOut());
            responseData.put("stdErr", executor.getStdErr());
            responseData.put("isRunning", executor.isRunning());
            response.end(responseData.toString());
        });
    }

    private void postHandler(HttpServerRequest request) {
        request.bodyHandler(handler -> {
            JsonObject json = handler.toJsonObject();
            logger.info("Incoming POST request: {}", json);
            Executor executor = new Executor();
            UUID uuid = UUID.randomUUID();

            JsonArray command = json.getJsonArray("command");
            int count = json.getInteger("count");

            JsonArray clientsIDs = new JsonArray();
            for (int i = 0; i < count; i++) {
                try {
                    Executors.newSingleThreadExecutor().submit(() -> executor.execute(command.getList()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                executors.put(uuid.toString(), executor);
                clientsIDs.add(uuid.toString());
            }

            HttpServerResponse response = successfulResponse(request);
            JsonObject responseData = new JsonObject();
            responseData.put("clients", clientsIDs);
            response.end(responseData.toString());
        });
    }

    private HttpServerResponse successfulResponse(HttpServerRequest request) {
        HttpServerResponse response = request.response();
        response.setStatusCode(200);
        response.headers().add("Content-Type", "application/json");
        return response;
    }
}
