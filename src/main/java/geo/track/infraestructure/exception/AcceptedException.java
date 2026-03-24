package geo.track.infraestructure.exception;

import geo.track.infraestructure.exception.constraint.message.Domains;

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
