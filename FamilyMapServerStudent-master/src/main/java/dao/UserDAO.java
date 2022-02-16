package dao;

import model.Event;
import model.User;
import model.Person;

import java.sql.*;

public class UserDAO {
    private final Connection accessCon;

    public UserDAO(Connection con) {this.accessCon = con;}

    /** method used to insert a user object into the database table using sql commands as string
     * @param user is the user object to be inserted
     */
    public void insert(User user) throws DataAccessException {
        String sqlString = "INSERT INTO User (Username, Password, Email, FirstName, LastName, " +
                "Gender, PersonID) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = accessCon.prepareStatement(sqlString)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /** method used to find a user in the database based on username.
     * These commands will also be in SQL
     * @param username is a string representing username
     * @return the user with this username
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE Username = ?;";
        try (PreparedStatement stmt = accessCon.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("PersonID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /** this method removes a user from the database table
     * @param user user to be removed
     * @return nothing
     */
    public void remove(User user) throws DataAccessException {
        String sqlString = "DELETE FROM User WHERE Username = " + user.getUsername();

        try (PreparedStatement stmt = accessCon.prepareStatement(sqlString)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setNull(1, Types.VARCHAR);
            stmt.setNull(2, Types.VARCHAR);
            stmt.setNull(3, Types.VARCHAR);
            stmt.setNull(4, Types.VARCHAR);
            stmt.setNull(5, Types.VARCHAR);
            stmt.setNull(6, Types.VARCHAR);
            stmt.setNull(7, Types.VARCHAR);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while removing from the database");
        }
    }

    public void clearUsers() throws DataAccessException {
        String sqlString = "DELETE FROM User";

        try (PreparedStatement stmt = accessCon.prepareStatement(sqlString)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }

}
