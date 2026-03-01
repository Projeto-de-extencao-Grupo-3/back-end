package geo.track.exception;

import geo.track.exception.constraint.message.EnumDomains;

public class NotAcepptableException extends RuntimeException {
    private String domain;

    public NotAcepptableException(String message, EnumDomains domain) {
        super(message);
        this.domain = domain.toString();
    }

    public String getDomain() {
        return domain;
    }
}
