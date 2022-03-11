package serviceTests;

import dao.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.PersonRequest;
import request.RegisterRequest;
import result.ClearResult;
import result.PersonResult;
import result.RegisterResult;
import service.ClearService;
import model.*;
import service.PersonService;
import service.RegisterService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private PersonService service;
    private PersonDAO pDAO;
    private EventDAO eDAO;
    private UserDAO uDAO;
    private AuthTokenDAO aDAO;
    private PersonResult result;
    private Event event;
    private Person person;
    private User user;
    private AuthToken token;
    private RegisterResult registerResult;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException, DataAccessException {
        connection = DriverManager.getConnection("jdbc:sqlite:famDB.db");

        service = new PersonService();
        eDAO = new EventDAO(connection);
        pDAO = new PersonDAO(connection);
        uDAO = new UserDAO(connection);
        aDAO = new AuthTokenDAO(connection);

        ClearService clearService = new ClearService();
        clearService.clear();
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "email", "first", "last", "g");
        RegisterService registerService = new RegisterService();
        registerResult = registerService.registerUser(registerRequest);

    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void personPass() throws DataAccessException {
        result = service.person(registerResult.getAuthtoken(), registerResult.getPersonID());
        assertTrue(result.isSuccess());
        assertNotNull(pDAO.find(registerResult.getPersonID()));
        assertEquals((pDAO.find(registerResult.getPersonID())).getAssociatedUsername(), result.getUsername());
    }

    @Test
    public void personFailBadAuth() throws DataAccessException {
        result = service.person("werido", registerResult.getPersonID());
        assertFalse(result.isSuccess());
        String message = "Error: Authtoken invalid";
        assertEquals(message, result.getMessage());
    }

    @Test
    public void personFailBadPerson() throws DataAccessException {
        result = service.person(registerResult.getAuthtoken(), "id");
        assertFalse(result.isSuccess());
        String message = "Error: Person with specified id not found in database";
        assertEquals(message, result.getMessage());
    }
}
