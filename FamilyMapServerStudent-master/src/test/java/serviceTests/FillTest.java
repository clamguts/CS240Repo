package serviceTests;

import dao.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.FillRequest;
import result.FillResult;
import service.FillService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class FillTest {
    private FillService service;
    private PersonDAO pDAO;
    private EventDAO eDAO;
    private UserDAO uDAO;
    private FillResult result;
    private FillRequest request;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:famDB.db");

        service = new FillService();

        eDAO = new EventDAO(connection);
        pDAO = new PersonDAO(connection);
        uDAO = new UserDAO(connection);

        request = new FillRequest("username", 4);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void fillPass() throws DataAccessException {
        result = service.fill(request);
        assertTrue(result.isSuccess());
        int peopleNum = 31;
        int eventNum = 91;
        assertEquals(peopleNum, (pDAO.getFamily(request.getUsername())).size());
        assertEquals(eventNum, (eDAO.getUserEvents(request.getUsername())).size());
    }

    @Test
    public void fillFail() throws DataAccessException {
        FillRequest fillRequest = new FillRequest(null, 0);
        result = service.fill(fillRequest);
        assertFalse(result.isSuccess());
        String message = "Error: User does not exist in database";
        assertEquals(message, result.getMessage());
    }
}
