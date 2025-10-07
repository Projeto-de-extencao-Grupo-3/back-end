package geo.track.exception;

public class ForbiddenException extends RuntimeException {
    private String domain;

    public ForbiddenException(String message, String domain) {
        super(message);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
