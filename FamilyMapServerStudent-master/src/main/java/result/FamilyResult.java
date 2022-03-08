package result;

import model.Person;

public class FamilyResult {

    private Person[] family;
    private String message;

    public FamilyResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    private boolean success;

    public FamilyResult() {}


    public FamilyResult(Person[] family, boolean success) {
        this.family = family;
        this.success = success;
    }

    public Person[] getFamily() {
        return family;
    }

    public void setFamily(Person[] family) {
        this.family = family;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
