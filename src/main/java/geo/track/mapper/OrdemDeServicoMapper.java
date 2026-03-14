package geo.track.mapper;

import geo.track.domain.ItemProduto;
import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.os.response.CardOrdemDeServicoResponse;
import geo.track.dto.os.response.OrdemDeServicoResponse;
import geo.track.dto.os.response.ResumoOrdemServicoResponse;
import geo.track.dto.os.response.TelaOrdemServicoResponse;
import geo.track.dto.veiculos.response.VeiculoResponse;
import geo.track.enums.os.StatusVeiculo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OrdemDeServicoMapper {
    public static OrdemDeServicoResponse toResponse(OrdemDeServico entity) {
        if (entity == null) {
            return null;
        }
        Double totalServico = 0.0;
        Double totalProduto = 0.0;
        if (entity.getServicos() != null) {
            totalServico = entity.getServicos().stream()
                    .mapToDouble(ItemServico::getPrecoCobrado)
                    .sum();
        }

        if (entity.getProdutos() != null) {
            totalProduto = entity.getProdutos().stream()
                    .mapToDouble(p -> p.getPrecoPeca() * p.getQuantidade())
                    .sum();
        }

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

    public static TelaOrdemServicoResponse toTelaOrdemServicoResponse(OrdemDeServico entity) {
        if (entity == null) {
            return null;
        }

        Integer idOrdemServico = entity.getIdOrdemServico();
        ClienteResponse cliente = ClientesMapper.toResponse(entity.getFkEntrada().getFkVeiculo().getFkCliente());
        VeiculoResponse veiculo = VeiculoMapper.toResponse(entity.getFkEntrada().getFkVeiculo());
        StatusVeiculo status = entity.getStatus();

        LocalDate dataEntradaPrevista = entity.getFkEntrada().getDataEntradaPrevista();
        LocalDate dataEntradaEfetiva = entity.getFkEntrada().getDataEntradaEfetiva();
        LocalDate dataSaidaPrevista = entity.getDataSaidaPrevista();
        LocalDate dataSaidaEfetiva = entity.getDataSaidaEfetiva();

        ResumoOrdemServicoResponse resumo = OrdemDeServicoMapper.toResumo(entity);

        List<ItemServicoResponse> servicos = entity.getServicos().stream().map(ItemServicoMapper::toResponse).toList();
        List<ItemProdutoResponse> produtos = entity.getProdutos().stream().map(ItemProdutoMapper::toResponse).toList();

        return new TelaOrdemServicoResponse(idOrdemServico, status, cliente, veiculo, dataEntradaPrevista, dataEntradaEfetiva, dataSaidaPrevista, dataSaidaEfetiva, resumo, servicos, produtos);
    }

    private static ResumoOrdemServicoResponse toResumo(OrdemDeServico entity) {
        Double totalServicos = entity.getServicos().stream()
                .mapToDouble(ItemServico::getPrecoCobrado)
                .sum();

        Double totalProdutos = entity.getProdutos().stream()
                .mapToDouble(p -> p.getPrecoPeca() * p.getQuantidade())
                .sum();

        Double totalGeral = totalServicos + totalProdutos;

        Integer produtosSaidaEstoqueRealizada = entity.getProdutos().stream().filter(ItemProduto::getBaixado).toList().size();
        Integer produtosSaidaEstoquePendente = entity.getProdutos().stream().filter(p -> !p.getBaixado()).toList().size();

        Boolean pagamentoRealizado = entity.getPagtRealizado();
        Boolean notaFiscalRealizada = entity.getNfRealizada();

        return new ResumoOrdemServicoResponse(totalGeral, totalServicos, totalProdutos, produtosSaidaEstoqueRealizada, produtosSaidaEstoquePendente, pagamentoRealizado, notaFiscalRealizada    );
    }

    public static List<CardOrdemDeServicoResponse> toCard(List<OrdemDeServico> ordem) {
        return ordem.stream().map(OrdemDeServicoMapper::toCard).toList();
    }

    public static CardOrdemDeServicoResponse toCard(OrdemDeServico ordem) {
        OrdemDeServicoResponse ordemResponse = OrdemDeServicoMapper.toResponse(ordem);

        return new   CardOrdemDeServicoResponse(
                ordem.getIdOrdemServico(),
                ordemResponse.getValorTotal(),
                ordemResponse.getDataSaidaPrevista(),
                ordemResponse.getDataSaidaEfetiva(),
                ordemResponse.getEntrada().getDataEntradaPrevista(),
                ordemResponse.getEntrada().getDataEntradaEfetiva(),
                ordemResponse.getStatus(),
                ordemResponse.getCliente(),
                ordemResponse.getVeiculo()
        );
    }
}
