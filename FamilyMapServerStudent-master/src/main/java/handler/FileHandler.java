package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.File;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {

    public FileHandler() {}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                String url = exchange.getRequestURI().toString();
                if (url == null || url.equals("/")) {
                    url = "/index.html";
                }
                url = "web" + url;
                File currFile = new File(url);
                if (currFile.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream body = exchange.getResponseBody();
                    Files.copy(currFile.toPath(), body);
                    body.close();
                    success = true;
                }
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                exchange.getResponseBody().close();
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException except) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            exchange.getResponseBody().close();
            except.printStackTrace();
        }
    }
}
