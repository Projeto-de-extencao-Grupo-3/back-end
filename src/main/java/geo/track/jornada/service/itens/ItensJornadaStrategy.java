package geo.track.jornada.service.itens;

import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.enums.TipoJornada;

/**
 * Esta strategy define as funcionalidades relacionadas:
 * Adição de Itens-Produtos OU Itens-Servicos a um ORÇAMENTO
 * @param <T> -> pode ser RequestAdicionarItemProduto ou RequestAdicionarItemServico
 */
public interface ItensJornadaStrategy<T> {
    /**
     * @param tipoJornada - Recebe um ENUM com o tipo de jornada desejada
     * @return se a strategy é válida para executar a lógica
     */
    Boolean isApplicable(TipoJornada tipoJornada);

    /**
     * Executa a lógica de adição de itens de acordo com o tipo de jornada.
     */
    T execute(Integer idOrdemServico, GetJornada request);
}

