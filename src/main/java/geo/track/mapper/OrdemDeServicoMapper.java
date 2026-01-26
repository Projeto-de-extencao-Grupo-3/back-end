package geo.track.mapper;

import geo.track.domain.OrdemDeServico;
import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.os.response.OrdemDeServicoResponse;
import geo.track.dto.registroEntrada.response.RegistroEntradaResponse;
import geo.track.mapper.*;

import java.util.List;
import java.util.stream.Collectors;

public class OrdemDeServicoMapper {
    public static OrdemDeServicoResponse toResponse(OrdemDeServico entity) {
        if (entity == null) {
            return null;
        }

        OrdemDeServicoResponse response = new OrdemDeServicoResponse();
        response.setIdOrdemServico(entity.getIdOrdemServico());
        response.setValorTotal(entity.getValorTotal());
        response.setDtSaidaPrevista(entity.getDtSaidaPrevista());
        response.setDtSaidaEfetiva(entity.getDtSaidaEfetiva());
        response.setStatus(entity.getStatus());
        response.setSeguradora(entity.getSeguradora());
        response.setNfRealizada(entity.getNfRealizada());
        response.setPagtRealizado(entity.getPagtRealizado());
        response.setAtivo(entity.getAtivo());
        response.setServicos(ItemServicoMapper.toOsResponse(entity.getServicos()));
        response.setProdutos(ItemProdutoMapper.toOsResponse(entity.getProdutos()));

        if (entity.getFk_entrada().getFkVeiculo().getFkCliente() != null) {
            ClienteResponse cliente = ClientesMapper.toResponse(entity.getFk_entrada().getFkVeiculo().getFkCliente());
            response.setCliente(cliente);
        }

        if (entity.getFk_entrada() != null) {
//            RegistroEntradaResponse entrada = RegistroEntradaMapper.toResponse(entity.getFk_entrada());
            response.setEntrada(RegistroEntradaMapper.toResponse(entity.getFk_entrada()));
        }

        return response;
    }

    public static List<OrdemDeServicoResponse> toResponse(List<OrdemDeServico> entities) {
        return entities.stream()
                .map(OrdemDeServicoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
