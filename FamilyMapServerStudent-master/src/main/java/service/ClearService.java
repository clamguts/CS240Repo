package service;

import dao.*;
import result.ClearResult;

import java.sql.Connection;

public class ClearService {

    /** clears the family tree/database
     * @return the result of the clearing
     */
    public ClearResult clear() throws DataAccessException {
        Database db = new Database();
        String respMessage;
        boolean success = false;

        try {
            Connection connection = db.openConnection();
            UserDAO uDAO = new UserDAO(connection);
            EventDAO eDAO = new EventDAO(connection);
            PersonDAO pDAO = new PersonDAO(connection);
            AuthTokenDAO aDAO = new AuthTokenDAO(connection);

            uDAO.clearUsers();
            eDAO.clearEvents();
            pDAO.clearPeople();
            aDAO.clearTokens();

            db.closeConnection(true);

            respMessage = "Clear succeeded.";
            success = true;

        }
        catch (DataAccessException d) {
            d.printStackTrace();
            respMessage = "Error: " + d.getMessage();
            success = false;
            db.closeConnection(false);
        }

        ClearResult clearResult = new ClearResult(respMessage, success);
        return clearResult;
    }
}
