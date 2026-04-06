package geo.track.jornada.enums;

public enum Status {
    AGUARDANDO_ENTRADA,
    AGUARDANDO_ORCAMENTO,
    AGUARDANDO_AUTORIZACAO,
    AGUARDANDO_VAGA,
    EM_PRODUCAO,
    FINALIZADO,
    CANCELADO;

    public static Boolean podeSerCancelada(Status status) {
        return switch (status) {
            case AGUARDANDO_ENTRADA, AGUARDANDO_ORCAMENTO, AGUARDANDO_AUTORIZACAO, AGUARDANDO_VAGA -> true;
            default -> false;
        };
    }
}
