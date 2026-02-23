package geo.track.mapper;

import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.domain.Servico;
import geo.track.dto.itensServicos.ItemServicoOsResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.itensServicos.RequestPostItemServico;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemServicoMapper {
    public static ItemServicoResponse toResponse(ItemServico entity) {
        if (entity == null) {
            return null;
        }

        ItemServicoResponse response = new ItemServicoResponse();
        response.setPrecoCobrado(entity.getPrecoCobrado());
        response.setParteVeiculo(entity.getParteVeiculo());
        response.setLadoVeiculo(entity.getLadoVeiculo());
        response.setCor(entity.getCor());
        response.setEspecificacaoServico(entity.getEspecificacaoServico());
        response.setObservacoesItem(entity.getObservacoesItem());

        return response;
    }

    public static List<ItemServicoResponse> toResponse(List<ItemServico> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(ItemServicoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static ItemServico toDomain(RequestPostItemServico dto, OrdemDeServico ordemServico, Servico servico) {
        if (dto == null) {
            return null; // Or throw an IllegalArgumentException
        }

        ItemServico item = new ItemServico();

        item.setPrecoCobrado(dto.getPrecoCobrado());
        item.setParteVeiculo(dto.getParteVeiculo());
        item.setLadoVeiculo(dto.getLadoVeiculo());
        item.setCor(dto.getCor());
        item.setEspecificacaoServico(dto.getEspecificacaoServico());
        item.setObservacoesItem(dto.getObservacoesItem());
        item.setFkOrdemServico(ordemServico); // Can be null if FK is nullable
        item.setFkServico(servico); // Can be null if FK is nullable

        return item;
    }

    public static ItemServicoOsResponse toOsResponse(ItemServico entity) {
        if (entity == null) {
            return null;
        }

        ItemServicoOsResponse response = new ItemServicoOsResponse();
        response.setIdRegistroServico(entity.getIdRegistroServico());
        response.setPrecoCobrado(entity.getPrecoCobrado());
        response.setParteVeiculo(entity.getParteVeiculo());
        response.setLadoVeiculo(entity.getLadoVeiculo());
        response.setCor(entity.getCor());
        response.setEspecificacaoServico(entity.getEspecificacaoServico());
        response.setObservacoesItem(entity.getObservacoesItem());

        // Safe access to nested properties
        Optional.ofNullable(entity.getFkServico()).ifPresent(fkServico -> {
            response.setNomeServico(fkServico.getTituloServico());
            response.setTipoServico(fkServico.getTipoServico());
            response.setTempoBase(fkServico.getTempoBase());
        });
        return response;
    }

    public static List<ItemServicoOsResponse> toOsResponse(List<ItemServico> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(ItemServicoMapper::toOsResponse)
                .collect(Collectors.toList());
    }
}
