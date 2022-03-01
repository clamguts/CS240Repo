package service;

import dao.DataAccessException;
import result.ClearResult;
import dao.Database;

public class ClearService {

    /** clears the family tree/database
     * @return the result of the clearing
     */
    public ClearResult clear() throws DataAccessException {
        Database db = new Database();
        String respMessage;
        boolean success = false;

        try {
            db.openConnection();
            db.clearTables();
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
