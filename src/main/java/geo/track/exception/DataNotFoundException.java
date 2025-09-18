package geo.track.exception;

public class DataNotFoundException extends RuntimeException {
    private String domain;

    public DataNotFoundException(String message, String domain) {
        super(message);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
