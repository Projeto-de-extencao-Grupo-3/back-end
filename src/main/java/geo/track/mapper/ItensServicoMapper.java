package geo.track.mapper;

import geo.track.domain.ItensServicos;
import geo.track.domain.OrdemDeServicos;
import geo.track.domain.Servicos;
import geo.track.dto.itensServicos.ItensServicoResponse;
import geo.track.dto.itensServicos.RequestPostItemServico;

import java.util.List;
import java.util.stream.Collectors;

public class ItensServicoMapper {
    public static ItensServicoResponse toResponse(ItensServicos entity) {
        if (entity == null) {
            return null;
        }

        ItensServicoResponse response = new ItensServicoResponse();
        response.setIdItensServicos(entity.getIdRegistroServico());
        response.setPrecoCobrado(entity.getPrecoCobrado());
        response.setParteVeiculo(entity.getParteVeiculo());
        response.setLadoVeiculo(entity.getLadoVeiculo());
        response.setCor(entity.getCor());
        response.setEspecificacaoServico(entity.getEspecificacaoServico());
        response.setObservacoesItem(entity.getObservacoesItem());

        if (entity.getFkServico() != null) {
            response.setIdServico(entity.getFkServico().getIdServico());
        }

        if (entity.getFkOrdemServico() != null) {
            response.setIdOrdemServico(entity.getFkOrdemServico().getIdOrdemServico());
        }

        return response;
    }

    public static List<ItensServicoResponse> toResponse(List<ItensServicos> entities) {
        return entities.stream()
                .map(ItensServicoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static ItensServicos toDomain(RequestPostItemServico dto, OrdemDeServicos ordemServico, Servicos servico) {
        ItensServicos item = new ItensServicos();

        item.setPrecoCobrado(dto.getPrecoCobrado());
        item.setParteVeiculo(dto.getParteVeiculo());
        item.setLadoVeiculo(dto.getLadoVeiculo());
        item.setCor(dto.getCor());
        item.setEspecificacaoServico(dto.getEspecificacaoServico());
        item.setObservacoesItem(dto.getObservacoesItem());
        item.setFkOrdemServico(ordemServico);
        item.setFkServico(servico);

        return item;
    }
}
