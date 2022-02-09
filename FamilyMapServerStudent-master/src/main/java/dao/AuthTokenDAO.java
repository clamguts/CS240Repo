package dao;

import model.AuthToken;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AuthTokenDAO {
    private final Connection accessCon;

    public AuthTokenDAO(Connection con) {this.accessCon = con;}

    /** method used to insert an authtoken object into the database table using sql commands as string
     * @param token is the event object to be inserted
     * @return nothing
     */
    public void insert(AuthToken token) throws DataAccessException {
    }

    /** method used to find an authoken in the database based on the token string.
     * These commands will also be in SQL
     * @param token is a unique key used to authenticate a user
     * @return the token matching this token string
     */
    public AuthToken find(String token) throws DataAccessException {
        return null;
    }

    /** this method removes a token from the database table
     * @param token token to be removed
     * @return nothing
     */
    public void remove(AuthToken token) throws DataAccessException {
    }

    /** this method searches the database for the token that belongs with this username (if it exists)
     * @param username the current user's username
     * @param token the current user's authtoken
     * @return the confimred user
     */
    public User matchToken(String username, String token) {
        return null;
    }
}
