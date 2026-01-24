package exception;


public class InvalidTicketException extends RuntimeException {

    public InvalidTicketException(String ticketId) {
        super("Invalid ticket id: " + ticketId);
    }
}
