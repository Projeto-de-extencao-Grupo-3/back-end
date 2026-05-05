package geo.track.externo.arquivo.domain.model;

import geo.track.jornada.infraestructure.mapper.OrdemDeServicoMapper;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.response.listagem.OrdemDeServicoResponse;

import java.time.LocalDate;
import java.util.List;

public class RelatorioOrdemDeServico {
    private final List<OrdemDeServicoResponse> ordens;
    private final Integer mesReferencia;
    private final Integer anoReferencia;
    private final String periodo;
    private final LocalDate dataGeracao;
    private final Integer totalOrdens;
    private final Integer totalFinalizadas;
    private final Double valorTotalPeriodo;
    private final Double ticketMedio;

    public RelatorioOrdemDeServico(
            List<OrdemDeServicoResponse> ordens,
            Integer mesReferencia,
            Integer anoReferencia,
            String periodo,
            LocalDate dataGeracao,
            Integer totalOrdens,
            Integer totalFinalizadas,
            Double valorTotalPeriodo,
            Double ticketMedio
    ) {
        this.ordens = ordens;
        this.mesReferencia = mesReferencia;
        this.anoReferencia = anoReferencia;
        this.periodo = periodo;
        this.dataGeracao = dataGeracao;
        this.totalOrdens = totalOrdens;
        this.totalFinalizadas = totalFinalizadas;
        this.valorTotalPeriodo = valorTotalPeriodo;
        this.ticketMedio = ticketMedio;
    }

    public List<OrdemDeServicoResponse> getOrdens() {
        return ordens;
    }

    public Integer getMesReferencia() {
        return mesReferencia;
    }

    public Integer getAnoReferencia() {
        return anoReferencia;
    }

    public String getPeriodo() {
        return periodo;
    }

    public LocalDate getDataGeracao() {
        return dataGeracao;
    }

    public Integer getTotalOrdens() {
        return totalOrdens;
    }

    public Integer getTotalFinalizadas() {
        return totalFinalizadas;
    }

    public Double getValorTotalPeriodo() {
        return valorTotalPeriodo;
    }

    public Double getTicketMedio() {
        return ticketMedio;
    }

    public static RelatorioOrdemDeServico build(List<OrdemDeServico> ordens, Integer mesReferencia, Integer anoReferencia) {
        String periodo = String.format("%02d/%d", mesReferencia, anoReferencia);
        int totalOrdens = ordens.size();
        int totalFinalizadas = (int) ordens.stream()
                .filter(ordem -> ordem.getStatus() == Status.FINALIZADO)
                .count();

        double valorTotalPeriodo = ordens.stream()
                .map(OrdemDeServico::getValorTotal)
                .filter(valor -> valor != null)
                .reduce(0.0, Double::sum);
        double ticketMedio = totalOrdens == 0 ? 0.0 : valorTotalPeriodo / totalOrdens;

        return new RelatorioOrdemDeServico(
                ordens.stream().map(OrdemDeServicoMapper::toResponse).toList(),
                mesReferencia,
                anoReferencia,
                periodo,
                LocalDate.now(),
                totalOrdens,
                totalFinalizadas,
                valorTotalPeriodo,
                ticketMedio
        );
    }

}
