package dao;

import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class EventDAO {
    private final Connection accessCon;

    public EventDAO(Connection con) {this.accessCon = con;}

    /** method used to insert an event object into the database table using sql commands as string
     * @param event is the event object to be inserted
     * @return nothing
     */
    public void insert(Event event) throws DataAccessException {
    }

    /** method used to find an event in the database based on event ID.
     * These commands will also be in SQL
     * @param eventID is a stirng representing eventID
     * @return the event matching this eventID
     */
    public Event find(String eventID) throws DataAccessException {
        return null;
    }

    /** this method removes an event from the database table
     * @param event event to be removed
     * @return nothing
     */
    public void remove(Event event) throws DataAccessException {
    }

    /** method gets the events in a user's family tree based on their username
     * @param username the string representing the user's username
     * @return list of user's events
     */
    public List<Event> getUserEvents(String username) {
        return null;
    }

    /** method gets the events for a given person in the tree
     * @param personID string identifying the person we're finding events for
     * @return a list of the person's life events
     */
    public List<Event> getPersonEvents(String personID) {
        return null;
    }

    /** method gets events that happened in a given country
     * @param country the name of the country
     * @return a list of events that took place in country
     */
    public List<Event> eventsByCountry(String country) {
        return null;
    }

    /** same as the country event, except with
     * @param city
     * @return list of events in a city
     */
    public List<Event> getEventsByCity(String city) {
        return null;
    }

    /** this time, we're returning events taking place in a specific location
     * @param latitude
     * @param longitude
     * @return list of events by location
     */
    public List<Event> getEventsByLocation(float latitude, float longitude) {
        return null;
    }

    /** @return list of events of a given type
     * @param eventType the type of event to be returned
     * @return a lsit of evetns that match the type
     */
    public List<Event> getEventsByType(String eventType) {
        return null;
    }

    /** @return list of events that happened in a given year
     * @param year the year to look for
     * @return a list of events by year
     */
    public List<Event> getEventsByYear(int year) {
        return null;
    }

}

