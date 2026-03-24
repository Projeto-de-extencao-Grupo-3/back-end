package geo.track.infraestructure.exception;

import geo.track.infraestructure.exception.constraint.message.Domains;

public class NotAcepptableException extends RuntimeException {
    private String domain;

    public NotAcepptableException(String message, Domains domain) {
        super(message);
        this.domain = domain.toString();
    }

    public String getDomain() {
        return domain;
    }
}
