package service;

import com.google.gson.Gson;
import handler.JsonFileReader;
import model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import model.NamesArray;

public class FamilyTree {

    private final NamesArray fNames = FileToStringArray("json/fnames.json");
    private final NamesArray mNames = FileToStringArray("json/mnames.json");
    private final NamesArray sNames = FileToStringArray("json/snames.json");
    private final LocationArray locations = FileToLocationArray("json/locations.json");

    private NamesArray FileToStringArray(String fileName) {
        Gson gson = new Gson();
        try {
            Reader fileReader = new FileReader(fileName);
            NamesArray names = gson.fromJson(fileReader, NamesArray.class);
            return names;

        } catch(FileNotFoundException e) {
            System.out.println("Json file not found (names)");
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private LocationArray FileToLocationArray(String fileName) {
        Gson gson = new Gson();
        try {
            Reader fileReader = new FileReader(fileName);
            LocationArray locations = gson.fromJson(fileReader, LocationArray.class);
            return locations;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Json file not found (locations)");
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void generateRoot(String userName, int generations, Set<Event> events, Set<Person> people, User rootUser) {
        String gender = rootUser.getGender();
        generatePerson(gender, generations, userName, 1700, events, people);
    }

    private Person generatePerson(String gender, int generations, String userName, int year, Set<Event> events, Set<Person> people) {

        Person mother = null;
        Person father = null;

        if (generations > 1) {
            mother = generatePerson("f", generations - 1, userName, year-30, events, people);
            father = generatePerson("m", generations-1, userName, year-30, events, people);

            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());

            Location marriageLocation = getLocation();
            events.add(createEvent("marriage", mother, year-10, marriageLocation));
            events.add(createEvent("marriage", father, year-10, marriageLocation));

        }

        Person newPerson = new Person();
        String personID = generateID();
        String firstName = "bob";
        String lastName = "bob";
        int randomIndexfName = 0;
        if (gender.equals("f")) {
            randomIndexfName = randomIndexStringArray(fNames);
            firstName = fNames.at(randomIndexfName);
        }
        if (gender.equals("m")) {
            randomIndexfName = randomIndexStringArray(mNames);
            firstName = mNames.at(randomIndexfName);
        }
        int randomIndexlName = randomIndexStringArray(sNames);
        lastName = sNames.at(randomIndexlName);
        newPerson.setAssociatedUsername(userName);
        if (mother != null) {
            newPerson.setMotherID(mother.getPersonID());
        }
        if (father != null) {
            newPerson.setFatherID(father.getPersonID());
        }
        newPerson.setPersonID(personID);
        newPerson.setGender(gender);
        newPerson.setFirstName(firstName);
        newPerson.setLastName(lastName);
        try {
            events.add(createEvent("birth", newPerson, year, getLocation()));
            events.add(createEvent("death", newPerson, year+75, getLocation()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        people.add(newPerson);

        return newPerson;
    }

    private int randomIndexStringArray(NamesArray array) {
        int random = 0;
        Random randInt = new Random();
        random = randInt.nextInt(array.getLength());
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

    private Location getLocation() {
        int randomIndex = randomIndexLocationArray(locations);
        return locations.at(randomIndex);
    }

    private Event createEvent(String eventType, Person currPerson, int year, Location location) {
        String eventID = generateID();
        Event newEvent = new Event();
        newEvent.setLocation(location);
        newEvent.setPersonId(currPerson.getPersonID());
        newEvent.setYear(year);
        newEvent.setEventType(eventType);
        newEvent.setAssociateUserName(currPerson.getAssociatedUsername());
        newEvent.setEventID(eventID);
        return newEvent;
    }
}
