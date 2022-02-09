package request;

import result.FillResult;

public class FillRequest {

    private String username;
    private int generations;

    /** a constructor with just the username, which sets the generation number to 4 by default
     * @param username : the registered username
     */
    FillRequest(String username) {
        this.username = username;
        this.generations = 4;
    }

    /** a constructor with a specified number of generations to fill
     * @param username
     * @param generations
     */
    FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }
}
