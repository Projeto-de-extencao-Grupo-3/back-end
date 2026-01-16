package geo.track.mapper;

import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.domain.Servico;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.itensServicos.RequestPostItemServico;

import java.util.List;
import java.util.stream.Collectors;

public class ItemServicoMapper {
    public static ItemServicoResponse toResponse(ItemServico entity) {
        if (entity == null) {
            return null;
        }

        ItemServicoResponse response = new ItemServicoResponse();
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

    public static List<ItemServicoResponse> toResponse(List<ItemServico> entities) {
        return entities.stream()
                .map(ItemServicoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static ItemServico toDomain(RequestPostItemServico dto, OrdemDeServico ordemServico, Servico servico) {
        ItemServico item = new ItemServico();

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
