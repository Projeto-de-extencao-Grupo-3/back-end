package geo.track.exception;

public class BadRequestException extends RuntimeException {
    private String domain;

    public BadRequestException(String message, String domain) {
        super(message);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
