package geo.track.exception;

public class NotAcepptableException extends RuntimeException {
    private String domain;

    public NotAcepptableException(String message, String domain) {
        super(message);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
