package geo.track.jornada.service;

import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.service.listagem.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Service
public class ListagemService {
    private final List<Condition> conditions = new ArrayList<>();
    private final ListagemSimplesStrategy listagemSimplesStrategy;
    private final ListagemPainelControleStrategy listagemPainelControleStrategy;
    private final ListagemAnaliseFinanceiraStrategy listagemAnaliseFinanceiraStrategy;
    private final BuscaSimplesStrategy buscaSimplesStrategy;


    public ListagemJornadaResponse execute(ListagemJornadaParams params, Boolean hasId) {
        for (Condition condition : conditions) {
            if (condition.isApplicable(params, hasId)) {
                return condition.estrategia().execute(params);
            }
        }

        throw new UnsupportedOperationException("Tipo de listagem não suportado: " + params);
    }

    public ListagemJornadaResponse execute(Integer id) {
        return buscaSimplesStrategy.execute(id);
    }

    /**
     * DTO para armazenar a condição e a estrategia
     * @param condicao - Condição que deve ser valida para pegar a estrategia
     * @param estrategia - Classe implementada com a lógica
     */
    public record Condition(
            BiPredicate<ListagemJornadaParams, Boolean> condicao,
            ListagemJornadaStrategy estrategia
    ) {
        public Boolean isApplicable(ListagemJornadaParams params, Boolean hasId) {
            return condicao.test(params, hasId);
        }
    }

    public ListagemService(ListagemSimplesStrategy listagemSimplesStrategy, ListagemPainelControleStrategy listagemPainelControleStrategy, ListagemAnaliseFinanceiraStrategy listagemAnaliseFinanceiraStrategy, BuscaSimplesStrategy buscaSimplesStrategy) {
        this.listagemSimplesStrategy = listagemSimplesStrategy;
        this.listagemPainelControleStrategy = listagemPainelControleStrategy;
        this.listagemAnaliseFinanceiraStrategy = listagemAnaliseFinanceiraStrategy;
        this.buscaSimplesStrategy = buscaSimplesStrategy;

        // Map
        conditions.addAll(
                List.of(
                        new Condition(
                                (request, hasId) -> request.isListagemSimples().equals(true) && hasId.equals(false),
                                listagemSimplesStrategy
                        ),
                        new Condition(
                                (request, hasId) -> request.isListagemPainelControle().equals(true) && hasId.equals(false),
                                listagemPainelControleStrategy
                        ),
                        new Condition(
                                (request, hasId) -> request.isListagemAnaliseFinanceira().equals(true) && hasId.equals(false),
                                listagemAnaliseFinanceiraStrategy
                        )
                )
        );
    }
}
