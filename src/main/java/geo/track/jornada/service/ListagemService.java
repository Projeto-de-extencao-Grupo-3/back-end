package geo.track.jornada.service;

import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.service.listagem.ListagemJornadaStrategy;
import geo.track.jornada.service.listagem.implementation.BuscaSimplesImplementation;
import geo.track.jornada.service.listagem.implementation.ListagemAnaliseFinanceiraImplementation;
import geo.track.jornada.service.listagem.implementation.ListagemPainelControleImplementation;
import geo.track.jornada.service.listagem.implementation.ListagemSimplesImplementation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Orquestrador de Strategy para Listagem de Jornadas.
 * Utiliza o padrão de Predicados para mapear diferentes tipos de listagem
 */
@Service
public class ListagemService implements JornadaStrategyRegistry<ListagemJornadaParams, ListagemJornadaStrategy, Object> {
    private final List<Condition<ListagemJornadaParams, ListagemJornadaStrategy>> conditions = new ArrayList<>();
    private final BuscaSimplesImplementation buscaSimplesImplementation;

    /**
     * Executa a strategy de listagem apropriada baseada nos parâmetros
     * @param params parâmetros com flags indicando tipo de listagem
     * @return resposta com dados de listagem
     */
    @Override
    public Object execute(ListagemJornadaParams params) {
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
        return buscaSimplesImplementation.execute(id);
    }

    public ListagemService(
            ListagemSimplesImplementation listagemSimplesImplementation,
            ListagemPainelControleImplementation listagemPainelControleImplementation,
            ListagemAnaliseFinanceiraImplementation listagemAnaliseFinanceiraImplementation,
            BuscaSimplesImplementation buscaSimplesImplementation) {
        this.buscaSimplesImplementation = buscaSimplesImplementation;

        // Mapeamento de condições para estratégias
        conditions.addAll(
                List.of(
                        new Condition<ListagemJornadaParams, ListagemJornadaStrategy>(
                                request -> request.isListagemSimples().equals(true),
                                listagemSimplesImplementation
                        ),
                        new Condition<ListagemJornadaParams, ListagemJornadaStrategy>(
                                request -> request.isListagemPainelControle().equals(true),
                                listagemPainelControleImplementation
                        ),
                        new Condition<ListagemJornadaParams, ListagemJornadaStrategy>(
                                request -> request.isListagemAnaliseFinanceira().equals(true),
                                listagemAnaliseFinanceiraImplementation
                        )
                )
        );
    }
}
