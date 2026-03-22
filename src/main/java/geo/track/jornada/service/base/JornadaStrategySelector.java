package geo.track.jornada.service.base;

import java.util.List;

/**
 * Interface genérica para seleção de strategies baseada em tipo.
 * Usada para padronizar o comportamento de strategies que verificam tipo com isApplicable().
 *
 * @param <T> tipo que será verificado para seleção
 * @param <S> tipo da strategy
 * @param <P> tipo do parâmetro
 * @param <R> tipo do retorno
 */
public interface JornadaStrategySelector<T, S extends JornadaTypeStrategy, P, R> {

    /**
     * Executa a strategy apropriada baseada no tipo
     * @param params parâmetros de entrada
     * @return resultado da execução
     */
    R execute(P params);

    /**
     * Seleciona a strategy apropriada da lista
     * @param strategies lista de strategies disponíveis
     * @param type tipo a ser verificado
     * @return strategy apropriada
     */
    default S selectStrategy(List<S> strategies, T type) {
        return strategies.stream()
                .filter(strategy -> strategy.isApplicable(type))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException(
                    "Tipo não suportado: " + type
                ));
    }
}

