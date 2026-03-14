package geo.track.exception;

public record InvalidField(
        String campo,
        String valor,
        String mesangem
) {
}
