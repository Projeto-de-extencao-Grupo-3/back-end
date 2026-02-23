package geo.track.exception;

public class BadBusinessRuleException extends RuntimeException {
    private String domain;

    public BadBusinessRuleException(String message, String domain) {
        super(message);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
