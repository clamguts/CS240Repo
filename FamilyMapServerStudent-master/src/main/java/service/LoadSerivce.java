package service;

import dao.*;
import model.Person;
import request.LoadRequest;
import result.LoadResult;
import model.User;
import model.Event;

import java.sql.Connection;

public class LoadSerivce {

    /** method clears data from database and loads specified user, person, and event data from request
     * @param l request containing info to load
     * @return result of the load
     */
    public LoadResult load(LoadRequest l) {
        Database db = new Database();
        String respMessage;

        boolean success = false;

        try {
            Connection connect = db.openConnection();
            UserDAO uDAO = new UserDAO(connect);
            EventDAO eDAO = new EventDAO(connect);
            PersonDAO pDAO = new PersonDAO(connect);

            uDAO.clearUsers();
            eDAO.clearEvents();
            pDAO.clearPeople();

            for (User u : l.getUsers()) {
                uDAO.insert(u);
            }
            for (Event e : l.getEvents()) {
                eDAO.insert(e);
            }
            for (Person p : l.getPersons()) {
                pDAO.insert(p);
            }

            Integer personLength = l.getPersons().length;
            Integer eventLength = l.getEvents().length;
            Integer userLength = l.getUsers().length;

            respMessage = "Successfully added " + personLength.toString(l.getPersons().length) + " people, " +
            eventLength.toString(l.getEvents().length) + " events, " + userLength.toString(l.getUsers().length)
            + " users.";
            success = true;
            LoadResult loadR = new LoadResult(respMessage, success);
            db.closeConnection(true);
            return loadR;
        }
        catch (DataAccessException d) {
            d.printStackTrace();
            respMessage = "Error: " + d.getMessage();
            success = false;
            try {
                db.closeConnection(false);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
            LoadResult loadR = new LoadResult(respMessage, success);
            return loadR;
        }

    }
}
