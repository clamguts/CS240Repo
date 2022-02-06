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

    public void insert(Event event) throws DataAccessException {
        /** method used to insert an event object into the database table using sql commands as string
         * @param event is the event object to be inserted
         * @return nothing
         */
    }

    public Event find(String EventID) throws DataAccessException {
        /** method used to find an event in the database based on event ID.
         * These commands will also be in SQL
         * @param eventID is a stirng representing eventID
         * @return the event matching this eventID
         */
        return null;
    }

    public void remove(Event event) throws DataAccessException {
        /** this method removes an event from the database table
         * @param event event to be removed
         * @return nothing
         */
    }

    public List<Event> getUserEvents(String username) {
        /** method gets the events in a user's family tree based on their username
         * @param username the string representing the user's username
         * @return list of user's events
         */
        return null;
    }

    public List<Event> getPersonEvents(String personID) {
        /** method gets the events for a given person in the tree
         * @param personID string identifying the person we're finding events for
         * @return a list of the person's life events
         */
        return null;
    }

    public List<Event> eventsByCountry(String country) {
        /** method gets events that happened in a given country
         * @param country the name of the country
         * @return a list of events that took place in country
         */
        return null;
    }

    public List<Event> getEventsByCity(String city) {
        /** same as the country event, except with @param city
         */
        return null;
    }

    public List<Event> getEventsByLocation(float latitude, float longitude) {
        /** this time, we're returning events taking place in a specific location
         */
        return null;
    }

    public List<Event> getEventsByType(String eventType) {
        /** @return list of events of a given type
         * @param eventType the type of event to be returned
         */
        return null;
    }

    public List<Event> getEventsByYear(int year) {
        /** @return list of events that happened in a given year
         * @paramt year the year to look for
         */
        return null;
    }

}

