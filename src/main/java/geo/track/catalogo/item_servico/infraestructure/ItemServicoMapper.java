package geo.track.catalogo.item_servico.infraestructure;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ItemServico;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.catalogo.item_servico.infraestructure.response.ItemServicoOsResponse;
import geo.track.catalogo.item_servico.infraestructure.response.ItemServicoResponse;
import geo.track.catalogo.item_servico.infraestructure.request.RequestPutItemServico;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.Servico;
import geo.track.infraestructure.exception.BadRequestException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ItemServicoExceptionMessages;
import geo.track.catalogo.item_servico.infraestructure.request.RequestPostItemServico;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemServicoMapper {
    public static ItemServicoResponse toResponse(ItemServico entity) {
        if (entity == null) {
            return null;
        }

        ItemServicoResponse response = new ItemServicoResponse();
        response.setIdItemServico(entity.getIdRegistroServico());
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

        if (dto.tipoServico().equals(Servico.PINTURA) && dto.tipoPintura() == null) throw new BadRequestException(ItemServicoExceptionMessages.TIPO_PINTURA_OBRIGATORIO, Domains.ITEM_SERVICO);
        else item.setTipoPintura(dto.tipoPintura());
        if (dto.tipoServico().equals(Servico.PINTURA) && dto.cor() == null) throw new BadRequestException(ItemServicoExceptionMessages.COR_OBRIGATORIA, Domains.ITEM_SERVICO);
        else item.setCor(dto.cor());

        item.setTipoServico(dto.tipoServico());
        item.setPrecoCobrado(dto.precoCobrado());
        item.setParteVeiculo(dto.parteVeiculo());
        item.setLadoVeiculo(dto.ladoVeiculo());
        item.setCor(dto.cor());
        item.setEspecificacaoServico(dto.especificacaoServico());
        item.setTipoPintura(dto.tipoPintura());
        item.setFkOrdemServico(ordemServico);

        return item;
    }

    public static ItemServico updateDomain(ItemServico item, RequestPutItemServico dto) {
        if (dto == null) {
            return item;
        }

        if (dto.precoCobrado() != null) item.setPrecoCobrado(dto.precoCobrado());
        if (dto.parteVeiculo() != null) item.setParteVeiculo(dto.parteVeiculo());
        if (dto.ladoVeiculo() != null) item.setLadoVeiculo(dto.ladoVeiculo());
        if (dto.cor() != null) item.setCor(dto.cor());
        if (dto.especificacaoServico() != null) item.setEspecificacaoServico(dto.especificacaoServico());
        if (dto.tipoPintura() != null) item.setTipoPintura(dto.tipoPintura());
        if (dto.tipoServico() != null) item.setTipoServico(dto.tipoServico());

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
