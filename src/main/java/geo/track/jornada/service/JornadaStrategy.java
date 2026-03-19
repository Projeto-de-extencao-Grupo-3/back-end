package geo.track.jornada.service;

import geo.track.jornada.enums.TipoJornada;

public interface JornadaStrategy<T, U> {
    Boolean isApplicable(TipoJornada tipoJornada);
    U execute(T request);
}
