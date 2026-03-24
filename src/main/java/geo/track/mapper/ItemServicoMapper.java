package geo.track.mapper;

import geo.track.gestao.entity.ItemServico;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.dto.itensServicos.ItemServicoOsResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.itensServicos.RequestPutItemServico;
import geo.track.enums.Servico;
import geo.track.exception.BadRequestException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.ItemServicoExceptionMessages;
import geo.track.jornada.request.itens.RequestPostItemServico;

import java.util.Collections;
import java.util.List;
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
        response.setTipoPintura(entity.getTipoPintura());
        response.setTipoServico(entity.getTipoServico());

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

    public static ItemServico toEntity(RequestPostItemServico dto, OrdemDeServico ordemServico) {
        if (dto == null) {
            return null;
        }

        ItemServico item = new ItemServico();

        if (dto.getTipoServico().equals(Servico.PINTURA) && dto.getTipoPintura() == null) throw new BadRequestException(ItemServicoExceptionMessages.TIPO_PINTURA_OBRIGATORIO, Domains.ITEM_SERVICO);
        else item.setTipoPintura(dto.getTipoPintura());
        if (dto.getTipoServico().equals(Servico.PINTURA) && dto.getCor() == null) throw new BadRequestException(ItemServicoExceptionMessages.COR_OBRIGATORIA, Domains.ITEM_SERVICO);
        else item.setCor(dto.getCor());

        item.setTipoServico(dto.getTipoServico());
        item.setPrecoCobrado(dto.getPrecoCobrado());
        item.setParteVeiculo(dto.getParteVeiculo());
        item.setLadoVeiculo(dto.getLadoVeiculo());
        item.setCor(dto.getCor());
        item.setEspecificacaoServico(dto.getEspecificacaoServico());
        item.setTipoPintura(dto.getTipoPintura());
        item.setFkOrdemServico(ordemServico);

        return item;
    }

    public static ItemServico updateDomain(ItemServico item, RequestPutItemServico dto) {
        if (dto == null) {
            return item;
        }

        if (dto.getPrecoCobrado() != null) item.setPrecoCobrado(dto.getPrecoCobrado());
        if (dto.getParteVeiculo() != null) item.setParteVeiculo(dto.getParteVeiculo());
        if (dto.getLadoVeiculo() != null) item.setLadoVeiculo(dto.getLadoVeiculo());
        if (dto.getCor() != null) item.setCor(dto.getCor());
        if (dto.getEspecificacaoServico() != null) item.setEspecificacaoServico(dto.getEspecificacaoServico());
        if (dto.getTipoPintura() != null) item.setTipoPintura(dto.getTipoPintura());
        if (dto.getTipoServico() != null) item.setTipoServico(dto.getTipoServico());

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
        response.setTipoPintura(entity.getTipoPintura());

        response.setTipoServico(entity.getTipoServico());
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
