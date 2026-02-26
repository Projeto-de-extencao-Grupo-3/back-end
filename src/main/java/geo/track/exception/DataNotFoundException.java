package geo.track.exception;

import geo.track.exception.constraint.message.EnumDomains;

public class DataNotFoundException extends RuntimeException {
    private String domain;

    public DataNotFoundException(String message, EnumDomains domain) {
        super(message);
        this.domain = domain.toString();
    }

    public String getDomain() {
        return domain;
    }
}
