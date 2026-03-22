package geo.track.jornada.service;

import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.service.base.JornadaStrategyRegistry;
import geo.track.jornada.service.listagem.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Orquestrador de Strategy para Listagem de Jornadas.
 * Utiliza o padrão de Predicados para mapear diferentes tipos de listagem
 */
@Service
public class ListagemService implements JornadaStrategyRegistry<ListagemJornadaParams, ListagemJornadaStrategy, ListagemJornadaResponse> {
    private final List<Condition<ListagemJornadaParams, ListagemJornadaStrategy>> conditions = new ArrayList<>();
    private final BuscaSimplesUseCase buscaSimplesUseCase;

    /**
     * Executa a strategy de listagem apropriada baseada nos parâmetros
     * @param params parâmetros com flags indicando tipo de listagem
     * @return resposta com dados de listagem
     */
    @Override
    public ListagemJornadaResponse execute(ListagemJornadaParams params) {
        for (Condition<ListagemJornadaParams, ListagemJornadaStrategy> condition : conditions) {
            if (condition.isApplicable(params)) {
                return condition.estrategia().execute(params);
            }
        }

        throw new UnsupportedOperationException("Tipo de listagem não suportado: " + params);
    }

    /**
     * UseCase específico para busca simples de um registro por ID
     * @param id ID da ordem de serviço
     * @return resposta com dados do registro
     */
    public ListagemJornadaResponse execute(Integer id) {
        return buscaSimplesUseCase.execute(id);
    }

    public ListagemService(
            ListagemSimplesStrategy listagemSimplesStrategy,
            ListagemPainelControleStrategy listagemPainelControleStrategy,
            ListagemAnaliseFinanceiraStrategy listagemAnaliseFinanceiraStrategy,
            BuscaSimplesUseCase buscaSimplesUseCase) {
        this.buscaSimplesUseCase = buscaSimplesUseCase;

        // Mapeamento de condições para estratégias
        conditions.addAll(
                List.of(
                        new Condition<>(
                                request -> request.isListagemSimples().equals(true),
                                listagemSimplesStrategy
                        ),
                        new Condition<>(
                                request -> request.isListagemPainelControle().equals(true),
                                listagemPainelControleStrategy
                        ),
                        new Condition<>(
                                request -> request.isListagemAnaliseFinanceira().equals(true),
                                listagemAnaliseFinanceiraStrategy
                        )
                )
        );
    }
}
