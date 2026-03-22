package geo.track.jornada.service.base;

/**
 * Interface base para strategies que verificam aplicabilidade por tipo.
 * Usada pelos subdomínios de Entrada e Itens.
 *
 * @param <T> tipo que será verificado (ex: TipoJornada)
 * @param <R> tipo de retorno da execução
 */
public interface JornadaTypeStrategy<T> {

    /**
     * Verifica se a strategy é aplicável para o tipo fornecido
     * @param type tipo a ser verificado
     * @return true se a strategy deve ser usada
     */
    Boolean isApplicable(T type);
}

