package geo.track.jornada.util;

import geo.track.gestao.entity.ItemProduto;
import geo.track.gestao.entity.ItemServico;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.jornada.enums.Status;
import geo.track.jornada.response.listagem.*;
import geo.track.dto.veiculos.response.VeiculoResponse;
import geo.track.gestao.util.ClientesMapper;
import geo.track.gestao.util.ItemProdutoMapper;
import geo.track.gestao.util.ItemServicoMapper;
import geo.track.gestao.util.VeiculoMapper;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class OrdemDeServicoMapper {
    public static OrdemDeServicoResponse toResponse(OrdemDeServico entity) {
        if (entity == null) {
            return null;
        }

        OrdemDeServicoResponse response = new OrdemDeServicoResponse();
        response.setIdOrdemServico(entity.getIdOrdemServico());
        response.setValorTotal(entity.getValorTotal());
        response.setValorTotalServicos(entity.getValorTotalServicos());
        response.setValorTotalProdutos(entity.getValorTotalProdutos());
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
        Status status = entity.getStatus();

        LocalDate dataEntradaPrevista = entity.getFkEntrada().getDataEntradaPrevista();
        LocalDate dataEntradaEfetiva = entity.getFkEntrada().getDataEntradaEfetiva();
        LocalDate dataSaidaPrevista = entity.getDataSaidaPrevista();
        LocalDate dataSaidaEfetiva = entity.getDataSaidaEfetiva();

        ResumoOrdemServicoResponse resumo = OrdemDeServicoMapper.toResumo(entity);

        List<ItemServicoResponse> servicos = entity.getServicos().stream().map(ItemServicoMapper::toResponse).toList();
        List<ItemProdutoResponse> produtos = entity.getProdutos().stream().map(ItemProdutoMapper::toResponse).toList();

        return new TelaOrdemServicoResponse(idOrdemServico, status, dataEntradaPrevista, dataEntradaEfetiva, dataSaidaPrevista, dataSaidaEfetiva, cliente, veiculo, resumo, servicos, produtos);
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

        return new ResumoOrdemServicoResponse(totalGeral, totalServicos, totalProdutos, pagamentoRealizado, notaFiscalRealizada, produtosSaidaEstoqueRealizada, produtosSaidaEstoquePendente);
    }

    public static List<CardOrdemDeServicoResponse> toCard(List<OrdemDeServico> ordem) {
        return ordem.stream().map(OrdemDeServicoMapper::toCard).toList();
    }

    public static CardOrdemDeServicoResponse toCard(OrdemDeServico ordem) {
        OrdemDeServicoResponse ordemResponse = OrdemDeServicoMapper.toResponse(ordem);

        LocalDate dataReferencia = ordem.getStatus().equals(Status.FINALIZADO) ? ordem.getDataSaidaEfetiva() : LocalDate.now();
        Long diasEspera = ChronoUnit.DAYS.between(ordem.getDataAtualizacao(), dataReferencia);
        diasEspera = diasEspera < 0 ? 0L : diasEspera;

        return new CardOrdemDeServicoResponse(
                ordem.getIdOrdemServico(),
                ordemResponse.getEntrada().getIdRegistroEntrada(),
                ordemResponse.getValorTotal(),
                diasEspera,
                ordemResponse.getDataSaidaPrevista(),
                ordemResponse.getDataSaidaEfetiva(),
                ordemResponse.getEntrada().getDataEntradaPrevista(),
                ordemResponse.getEntrada().getDataEntradaEfetiva(),
                ordemResponse.getStatus(),
                ordemResponse.getCliente(),
                ordemResponse.getVeiculo()
        );
    }
    public static List<OrdemDeServicoHistoricoResponse> toResponseHistoricoVeiculo(List<OrdemDeServico> entities) {
        return entities.stream()
                .map(OrdemDeServicoMapper::toResponseHistoricoVeiculo)
                .toList();
    }

    public static OrdemDeServicoHistoricoResponse toResponseHistoricoVeiculo(OrdemDeServico entity) {
        if (entity == null) {
            return null;
        }

        String status = switch (entity.getStatus()) {
            case AGUARDANDO_ENTRADA -> "Com agendamento";
            case AGUARDANDO_ORCAMENTO, AGUARDANDO_AUTORIZACAO, AGUARDANDO_VAGA -> "Presente na oficina";
            case EM_PRODUCAO -> "Em produção";
            case FINALIZADO -> "Sem agendamento";
        };

        OrdemDeServicoHistoricoResponse response = new OrdemDeServicoHistoricoResponse();
        response.setIdOrdemServico(entity.getIdOrdemServico());
        response.setValorTotal(entity.getValorTotal());
        response.setValorTotalServicos(entity.getValorTotalServicos());
        response.setValorTotalProdutos(entity.getValorTotalProdutos());
        response.setDataSaidaPrevista(entity.getDataSaidaPrevista());
        response.setDataSaidaEfetiva(entity.getDataSaidaEfetiva());
        response.setStatus(status);
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
}
