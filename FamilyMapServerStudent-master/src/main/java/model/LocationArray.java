package model;

public class LocationArray {
    private Location[] data;
    private int length = data.length;

    public int getLength() {
        return length;
    }

    public Location at(int index) {
        return data[index];
    }
}
