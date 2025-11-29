package geo.track.mapper;

import geo.track.domain.ItensServicos;
import geo.track.domain.OrdemDeServicos;
import geo.track.domain.Servicos;
import geo.track.dto.itensServicos.ItensServicoRequestDTO;
import geo.track.dto.itensServicos.ItensServicoResponseDTO;

public class ItensServicoMapper {

    public static ItensServicos toEntity(ItensServicoRequestDTO dto, Servicos servico, OrdemDeServicos ordem) {

        ItensServicos itens = new ItensServicos();
        itens.setPrecoCobrado(dto.getPrecoCobrado());
        itens.setParteVeiculo(dto.getParteVeiculo());
        itens.setLadoVeiculo(dto.getLadoVeiculo());
        itens.setCor(dto.getCor());
        itens.setEspecificacaoServico(dto.getEspecificacaoServico());
        itens.setObservacoesItem(dto.getObservacoesItem());
        itens.setFkServico(servico);
        itens.setFkOrdemServico(ordem);

        return itens;
    }

    public static ItensServicoResponseDTO toDTO(ItensServicos entity) {
        ItensServicoResponseDTO dto = new ItensServicoResponseDTO();

        dto.setIdItensServicos(entity.getIdItensServicos());
        dto.setPrecoCobrado(entity.getPrecoCobrado());
        dto.setParteVeiculo(entity.getParteVeiculo());
        dto.setLadoVeiculo(entity.getLadoVeiculo());
        dto.setCor(entity.getCor());
        dto.setEspecificacaoServico(entity.getEspecificacaoServico());
        dto.setObservacoesItem(entity.getObservacoesItem());

        dto.setFkServicoId(entity.getFkServico() != null ? entity.getFkServico().getIdServico() : null);
        dto.setFkOrdemServicoId(entity.getFkOrdemServico() != null ? entity.getFkOrdemServico().getIdOrdemServico() : null);

        return dto;
    }
}
