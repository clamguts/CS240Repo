package model;

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
