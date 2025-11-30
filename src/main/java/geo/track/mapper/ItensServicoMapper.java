package geo.track.mapper;

import geo.track.domain.ItensServicos;
import geo.track.dto.itensservicos.ItensServicoResponse;

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
}
