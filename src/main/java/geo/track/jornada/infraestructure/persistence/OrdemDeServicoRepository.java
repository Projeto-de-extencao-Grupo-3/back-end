package geo.track.jornada.infraestructure.persistence;

import geo.track.jornada.infraestructure.response.listagem.ViewNotaFiscal;
import geo.track.jornada.infraestructure.response.listagem.ViewPagtoPendente;
import geo.track.jornada.infraestructure.response.listagem.ViewPagtoRealizado;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Integer> {

    @Query("SELECT o FROM OrdemDeServico o JOIN o.fkEntrada e JOIN e.fkVeiculo v WHERE v.placa = :placa")
    List<OrdemDeServico> findByPlaca(@Param("placa") String placa);

    @Query("SELECT o FROM OrdemDeServico o WHERE o.status = :status")
    List<OrdemDeServico> findByStatus(@Param("status") Status status);

    @Query("SELECT o FROM OrdemDeServico o WHERE o.dataSaidaEfetiva >= :data")
    List<OrdemDeServico> findByStatusUltimos30Dias(@Param("data") LocalDate data);
    // KPI Queries
    @Query(value = "SELECT quantidade_nfs_pendentes FROM vw_kpi_nfs_pendentes as vw WHERE vw.ano = :ano AND vw.mes = :mes", nativeQuery = true)
    ViewNotaFiscal findViewNotasFicaisPendentes(@Param("ano") Integer ano, @Param("mes") Integer mes);

    @Query(value = "SELECT total_pagamentos_realizados FROM vw_kpi_pgtos_realizados as vw WHERE vw.ano = :ano AND vw.mes = :mes", nativeQuery = true)
    ViewPagtoRealizado findViewPagamentoRealizados(@Param("ano") Integer ano, @Param("mes") Integer mes);

    @Query(value = "SELECT total_valor_nao_pago, quantidade_servicos_nao_pagos FROM vw_kpi_servicos_nao_pagos as vw WHERE vw.ano = :ano AND vw.mes = :mes", nativeQuery = true)
    ViewPagtoPendente findViewPagamentoPendente(@Param("ano") Integer ano, @Param("mes") Integer mes);

    @Query("SELECT o FROM OrdemDeServico o WHERE o.dataSaidaEfetiva >= :intervalo AND o.fkEntrada.fkVeiculo.idVeiculo = :idVeiculo")
    List<OrdemDeServico> findByIntervaloMesesAndIdVeiculo(@Param("intervalo") LocalDate intervalo, @Param("idVeiculo") Integer idVeiculo);

    @Query("SELECT o FROM OrdemDeServico o WHERE o.dataSaidaEfetiva >= :intervalo AND o.status = 'FINALIZADO'")
    List<OrdemDeServico> findByIntervaloMeses(@Param("intervalo") LocalDate intervalo);

    @Query("SELECT o FROM OrdemDeServico o WHERE o.nfRealizada = :nfRealizada AND o.pagtRealizado = :pagtRealizado AND Year(o.dataSaidaEfetiva) = :ano AND Month(o.dataSaidaEfetiva) = :mes")
    List<OrdemDeServico> findByListagemAnaliseFinanceiraStrategy(
            @Param("nfRealizada") Boolean nfRealizada,
            @Param("pagtRealizado") Boolean pagtRealizado,
            @Param("ano") Integer ano,
            @Param("mes") Integer mes);

    @Query("SELECT o FROM OrdemDeServico o WHERE o.fkEntrada.fkVeiculo.idVeiculo = :idVeiculo")
    List<OrdemDeServico> findAllByVeiculo(@Param("idVeiculo") Integer idVeiculo);

    @Query("SELECT o FROM OrdemDeServico o WHERE o.fkEntrada.fkVeiculo.idVeiculo = :idVeiculo ORDER BY o.idOrdemServico DESC")
    Optional<OrdemDeServico> findLastOrdemServicoVeiculo(@Param("idVeiculo") Integer idVeiculo);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN TRUE ELSE FALSE END FROM OrdemDeServico o WHERE o.fkEntrada.fkVeiculo.fkCliente.idCliente = :idCliente AND o.status <> :status")
    Boolean existsByIdCliente(Integer idCliente, Status status);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN TRUE ELSE FALSE END FROM OrdemDeServico o JOIN o.produtos p WHERE p.fkProduto.idProduto = :idProduto AND o.status <> :status")
    Boolean existsByIdProduto(@Param("idProduto") Integer idProduto, @Param("status") Status status);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN TRUE ELSE FALSE END FROM OrdemDeServico o WHERE o.fkEntrada.fkVeiculo.idVeiculo = :idVeiculo AND o.status <> 'FINALIZADO'")
    Boolean existsByIdVeiculo(@Param("idVeiculo") Integer idVeiculo);
}
