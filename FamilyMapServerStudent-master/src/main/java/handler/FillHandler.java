package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import dao.DataAccessException;
import model.LocationArray;
import request.FillRequest;
import result.FillResult;
import service.FillService;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler extends SuperHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                String currUri = exchange.getRequestURI().toString();
                String userName = "";
                int generationNum = 0;
                int numSlashes = 0;
                for (int i = 0; i < currUri.length(); ++i) {
                    if (currUri.charAt(i) == '/') {
                        ++numSlashes;
                    }
                }
                if (numSlashes == 1) {
                    userName = currUri.substring(currUri.indexOf("/"));
                    generationNum = 4;
                }
                else if (numSlashes == 2) {
                    userName = currUri.substring(currUri.indexOf("/"), currUri.lastIndexOf("/"));
                    generationNum = Integer.parseInt(currUri.substring(currUri.lastIndexOf("/")));
                }
                Gson gson = new Gson();
                FillService fServe = new FillService();

                FillRequest fillRequest = new FillRequest(userName, generationNum);
                FillResult result = fServe.fill(fillRequest);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream responseBody = exchange.getResponseBody();
                String toWrite = gson.toJson(result);
                writeString(toWrite, responseBody);
                exchange.getResponseBody().close();
                success = true;
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException except) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            exchange.getResponseBody().close();
            except.printStackTrace();
        }
    }

}
