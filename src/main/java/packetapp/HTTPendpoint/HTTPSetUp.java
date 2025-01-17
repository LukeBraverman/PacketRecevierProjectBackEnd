package packetapp.HTTPendpoint;

import com.google.gson.Gson;
import packetapp.DataAccessLayer.LogDataBase;

import static spark.Spark.*;

public class HTTPSetUp {

    private Gson gson;

    public HTTPSetUp(int portToOpenOn) {
        gson = new Gson();
        setUpHTTPEndpoints(portToOpenOn);
    }

    private void setUpHTTPEndpoints(int portToOpenOn)
    {

        System.out.println("Starting HTTP endpoint setup...");

        port(portToOpenOn);

        // ✅ Add CORS Configuration Here
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");  // Allows requests from any origin
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

        get("/hello", (req, res) -> "Hello from Spark Java!");

        get("/health", (req, res) -> {
            res.status(200);
            return "OK";
        });

        get("/logs", (req, res) -> {
            res.status(200);
            return gson.toJson(LogDataBase.getLogDatabase());  // Convert ArrayList to JSON
        });

        System.out.println("HTTP endpoint setup complete! HTTP port is opened on port: " + portToOpenOn);
    }
}
