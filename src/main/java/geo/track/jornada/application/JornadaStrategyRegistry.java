package geo.track.jornada.application;

import java.util.function.Predicate;

/**
 * Interface genérica para registro de strategies com predicados.
 * Usada para padronizar o map de strategies que usam Predicate como condição.
 *
 * @param <P> tipo do parâmetro de entrada
 * @param <S> tipo da strategy
 * @param <R> tipo do retorno
 */
public interface JornadaStrategyRegistry<P, S, R> {

    /**
     * Executa a strategy apropriada baseada na condição
     * @param params parâmetros de entrada
     * @return resultado da execução
     */
    R execute(P params);

    /**
     * DTO para armazenar a condição e a estratégia
     */
    record Condition<P, S>(
            Predicate<P> condicao,
            S estrategia
    ) {
        public Boolean isApplicable(P params) {
            return condicao.test(params);
        }
    }
}

