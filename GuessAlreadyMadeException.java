package hangman;

public class GuessAlreadyMadeException extends Exception {
    private String error;
    public GuessAlreadyMadeException(String errorMessage) {
        this.error = errorMessage;
    }

    String getError() {
        return error;
    }
}
