package geo.track.exception;

import geo.track.exception.constraint.message.Domains;

public class ServiceUnavailableException extends RuntimeException {
    private String domain;

    public ServiceUnavailableException(Domains domain, String message) {
        super(message);
        this.domain = domain.toString();
    }

    public String getDomain() {
        return domain;
    }
}
