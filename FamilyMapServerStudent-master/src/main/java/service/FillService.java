package service;

import dao.*;
import handler.JsonFileReader;
import model.LocationArray;
import request.FillRequest;
import result.FillResult;
import model.Person;
import model.User;
import model.Event;
import result.LoginResult;

import java.util.Random;
import java.sql.Connection;
import java.util.Set;
import java.util.UUID;

public class FillService {

    private Set<Event> events;
    private Set<Person> people;

    private JsonFileReader reader = new JsonFileReader();
    private final String[] fNames = reader.FileToStringArray("json/fnames.json");
    private final String[] mNames = reader.FileToStringArray("json/mnames.json");
    private final String[] sNames = reader.FileToStringArray("json/snames.json");
    private final LocationArray locations = reader.FileToLocationArray("json/locations.json");

    /** method fills the family tree according to the specified username and potential number of generations
     * @param fRequest the request with said required info
     * @return the result of that request
     */
   public FillResult fill(FillRequest fRequest) {
    //clear database of everything associated with user
        String userName = fRequest.getUsername();
        int generations = fRequest.getGenerations();
        String message = "";
        boolean success = false;
        return generateRoot(userName, generations, message, success);
    }

    private FillResult generateRoot(String userName, int generations, String message, boolean success) {
       Database db = new Database();
       try {
           Connection connect = db.openConnection();

           UserDAO uDAO = new UserDAO(connect);
           PersonDAO pDAO = new PersonDAO(connect);
           EventDAO eDAO = new EventDAO(connect);

           User rootUser = uDAO.find(userName);
           String gender = rootUser.getGender();
           generatePerson(gender, generations, userName, 1700);

           for (Person p : people) {
               pDAO.insert(p);
           }

           for (Event e : events) {
               eDAO.insert(e);
           }

           message = "Successfully added " + people.size() + "people and " + events.size() + " events";
           success = true;
           FillResult fillResult = new FillResult(message, success);
           db.closeConnection(true);
           return fillResult;
       } catch(DataAccessException d) {
           d.printStackTrace();
           message = "Error: " + d.getMessage();
           success = false;
           try {
               db.closeConnection(false);
           } catch (DataAccessException e) {
               e.printStackTrace();
           }
           FillResult fillResult = new FillResult(message, success);
           return fillResult;
       }
    }

    private Person generatePerson(String gender, int generations, String userName, int year) {

       Person mother = null;
       Person father = null;

       if (generations > 1) {
           mother = generatePerson("f", generations - 1, userName, year-30);
           father = generatePerson("m", generations-1, userName, year-30);

           mother.setSpouseID(father.getPersonID());
           father.setSpouseID(mother.getPersonID());

            events.add(createEvent("marriage", mother, year-10));
            events.add(createEvent("marriage", father, year-10));

       }

       Person newPerson = new Person();
       String personID = generateID();
       String firstName = "bob";
       String lastName;
       int randomIndexfName = 0;
       if (gender.equals("f")) {
           randomIndexfName = randomIndexStringArray(fNames);
           firstName = fNames[randomIndexfName];
       }
       if (gender.equals("m")) {
           randomIndexfName = randomIndexStringArray(mNames);
           firstName = mNames[randomIndexfName];
       }
       int randomIndexlName = randomIndexStringArray(sNames);
       lastName = sNames[randomIndexlName];
       newPerson.setAssociatedUsername(userName);
       newPerson.setMotherID(mother.getPersonID());
       newPerson.setFatherID(father.getPersonID());
       newPerson.setPersonID(personID);
       newPerson.setGender(gender);
       newPerson.setFirstName(firstName);
       newPerson.setLastName(lastName);

       events.add(createEvent("birth", newPerson, year));
       events.add(createEvent("death", newPerson, year+75));
       people.add(newPerson);

       return newPerson;
    }

    private int randomIndexStringArray(String[] array) {
       int random = 0;
        Random randInt = new Random();
        random = randInt.nextInt(array.length);
        return random;
    }

    private int randomIndexLocationArray(LocationArray array) {
        int random = 0;
       Random randInt = new Random();
       random = randInt.nextInt(array.getLength());
       return random;
    }

    private String generateID() {
        UUID randomID = UUID.randomUUID();
        String randomString = randomID.toString();
        return randomString;
    }

    private Event createEvent(String eventType, Person currPerson, int year) {
       int randomIndex = randomIndexLocationArray(locations);
       String eventID = generateID();
       Event newEvent = new Event();
       newEvent.setLocation(locations.at(randomIndex));
       newEvent.setPersonId(currPerson.getPersonID());
       newEvent.setYear(year);
       newEvent.setEventType(eventType);
       newEvent.setAssociateUserName(currPerson.getAssociatedUsername());
       newEvent.setEventID(eventID);

       return newEvent;
    }
}
