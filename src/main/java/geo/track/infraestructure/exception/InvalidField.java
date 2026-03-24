package geo.track.infraestructure.exception;

public record InvalidField(
        String campo,
        String valor,
        String mesangem
) {
}
