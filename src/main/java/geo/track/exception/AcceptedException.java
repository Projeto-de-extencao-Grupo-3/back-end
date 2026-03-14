package geo.track.exception;

import geo.track.exception.constraint.message.Domains;

public class AcceptedException extends RuntimeException {
    private String domain;

    public AcceptedException(String message, Domains domain) {
        super(message);
        this.domain = domain.toString();
    }

    public String getDomain() {
        return domain;
    }
}
