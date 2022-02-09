package dao;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonDAO {

    private final Connection accessCon;

    public PersonDAO(Connection con) {this.accessCon = con;}

    /** method used to insert a person object into the database table using sql commands as string
     * @param person is the event object to be inserted
     * @return nothing
     */
    public void insert(Person person) throws DataAccessException {
    }

    /** method used to find a person in the database based on person ID.
     * These commands will also be in SQL
     * @param personID is a string representing eventID
     * @return the person matching this personID
     */
    public Person find(String personID) throws DataAccessException {
        return null;
    }

    /** this method removes a person from the database table
     * @param person person to be removed
     */
    public void remove(Person person) throws DataAccessException {
    }

    /** method gets a person's family members based on their respective ids
     * @param personID person's id
     * @param motherID, fatherID, spouseID ids of the person's family members
     * @return list of people in the person's family
     */
    public List<Person> getFamily(String personID, String motherID, String fatherID, String spouseID) {
        return null;
    }

}
