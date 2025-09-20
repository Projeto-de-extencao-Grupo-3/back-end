package geo.track.exception;

public class ConflictException extends RuntimeException{
    private String domain;

    public ConflictException(String message, String domain) {
        super(message);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
