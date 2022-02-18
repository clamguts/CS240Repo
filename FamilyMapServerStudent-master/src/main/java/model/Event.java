package model;

import java.util.Objects;

public class Event {
    private String eventID;
    private String associateUserName;
    private String personId;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    public Event() {
        this.latitude = 0;
        this.longitude = 0;
        this.year = 0;

    }

    public Event(String id, String uName, String pId, float lat, float eLong,
                 String eCountry, String eCity, String eType, int eYear) {
        this.eventID = id;
        this.associateUserName = uName;
        this.personId = pId;
        this.latitude = lat;
        this.longitude = eLong;
        this.country = eCountry;
        this.city = eCity;
        this.eventType = eType;
        this.year = eYear;
    }

    public String getEventID() {
        return eventID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Float.compare(event.latitude, latitude) == 0 && Float.compare(event.longitude, longitude) == 0 && year == event.year && eventID.equals(event.eventID) && associateUserName.equals(event.associateUserName) && personId.equals(event.personId) && country.equals(event.country) && city.equals(event.city) && eventType.equals(event.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID, associateUserName, personId, latitude, longitude, country, city, eventType, year);
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociateUserName() {
        return associateUserName;
    }

    public void setAssociateUserName(String associateUserName) {
        this.associateUserName = associateUserName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
