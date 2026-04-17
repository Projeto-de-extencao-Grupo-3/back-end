package geo.track.jornada.domain;

import geo.track.jornada.domain.listagem.BuscaSimplesImplementation;
import geo.track.jornada.domain.listagem.ListagemAnaliseFinanceiraImplementation;
import geo.track.jornada.domain.listagem.ListagemPainelControleImplementation;
import geo.track.jornada.domain.listagem.ListagemSimplesImplementation;
import geo.track.jornada.infraestructure.request.ListagemJornadaParams;
import geo.track.jornada.infraestructure.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.application.listagem.ListagemJornadaStrategy;
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
