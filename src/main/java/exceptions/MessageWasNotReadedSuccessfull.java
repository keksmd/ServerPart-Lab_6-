package exceptions;

public class MessageWasNotReadedSuccessfull extends Exception {
    public MessageWasNotReadedSuccessfull() {
    }

    public MessageWasNotReadedSuccessfull(String message) {
        super(message);
    }
}
