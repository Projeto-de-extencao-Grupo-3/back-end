package geo.track.mapper;

import geo.track.domain.ItemProduto;
import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.os.response.OrdemDeServicoResponse;
import geo.track.dto.os.response.ServicoProdutoOrdemResponse;

import java.util.List;
import java.util.stream.Collectors;

public class OrdemDeServicoMapper {
    public static OrdemDeServicoResponse toResponse(OrdemDeServico entity) {
        if (entity == null) {
            return null;
        }

        Double totalServico = entity.getServicos().stream()
                .mapToDouble(ItemServico::getPrecoCobrado)
                .sum();

        Double totalProduto = entity.getProdutos().stream()
                .mapToDouble(p -> p.getPrecoPeca() * p.getQuantidade())
                .sum();

        OrdemDeServicoResponse response = new OrdemDeServicoResponse();
        response.setIdOrdemServico(entity.getIdOrdemServico());
        response.setValorTotal(totalServico + totalProduto);
        response.setValorTotalServicos(totalServico);
        response.setValorTotalProdutos(totalProduto);
        response.setDataSaidaPrevista(entity.getDataSaidaPrevista());
        response.setDataSaidaEfetiva(entity.getDataSaidaEfetiva());
        response.setStatus(entity.getStatus());
        response.setSeguradora(entity.getSeguradora());
        response.setNfRealizada(entity.getNfRealizada());
        response.setPagtRealizado(entity.getPagtRealizado());
        response.setAtivo(entity.getAtivo());
        response.setServicos(ItemServicoMapper.toOsResponse(entity.getServicos()));
        response.setProdutos(ItemProdutoMapper.toOsResponse(entity.getProdutos()));
        response.setVeiculo(VeiculoMapper.toResponse(entity.getFkEntrada().getFkVeiculo()));

        if (entity.getFkEntrada().getFkVeiculo().getFkCliente() != null) {
            ClienteResponse cliente = ClientesMapper.toResponse(entity.getFkEntrada().getFkVeiculo().getFkCliente());
            response.setCliente(cliente);
        }

        if (entity.getFkEntrada() != null) {
            response.setEntrada(RegistroEntradaMapper.toResponse(entity.getFkEntrada()));
            response.setEntrada(RegistroEntradaMapper.toResponse(entity.getFkEntrada()));
        }

        return response;
    }

    public static List<OrdemDeServicoResponse> toResponse(List<OrdemDeServico> entities) {
        return entities.stream()
                .map(OrdemDeServicoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static ServicoProdutoOrdemResponse toServicoProdutoResponse(OrdemDeServico entity) {
        if (entity == null) {
            return null;
        }

        List<ItemServicoResponse> servicos = entity.getServicos().stream().map(ItemServicoMapper::toResponse).toList();
        List<ItemProdutoResponse> produtos = entity.getProdutos().stream().map(ItemProdutoMapper::toResponse).toList();

        return new ServicoProdutoOrdemResponse(servicos, produtos);
    }
}
