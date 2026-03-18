package geo.track.jornada.entity;

import geo.track.dto.os.response.ViewNotaFiscal;
import geo.track.dto.os.response.ViewPagtoPendente;
import geo.track.dto.os.response.ViewPagtoRealizado;
import geo.track.enums.os.StatusVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Integer> {
    
    @Query("SELECT o FROM OrdemDeServico o JOIN o.fkEntrada e JOIN e.fkVeiculo v JOIN v.fkCliente c JOIN c.fkOficina ofi WHERE v.placa = :placa AND ofi.idOficina = :idOficina")
    List<OrdemDeServico> findByPlacaAndIdOficina(@Param("placa") String placa, @Param("idOficina") Integer idOficina);

    @Query("SELECT o FROM OrdemDeServico o JOIN o.fkEntrada e JOIN e.fkVeiculo v JOIN v.fkCliente c JOIN c.fkOficina ofi WHERE o.status = :status AND ofi.idOficina = :idOficina")
    List<OrdemDeServico> findByStatusAndIdOficina(@Param("status") StatusVeiculo status, @Param("idOficina") Integer idOficina);

    @Query("SELECT o FROM OrdemDeServico o JOIN o.fkEntrada e JOIN e.fkVeiculo v JOIN v.fkCliente c JOIN c.fkOficina ofi WHERE o.dataSaidaEfetiva >= :data AND ofi.idOficina = :idOficina")
    List<OrdemDeServico> findByStatusUltimos30DiasAndIdOficina(@Param("data") LocalDate data, @Param("idOficina") Integer idOficina);

    @Query("SELECT o FROM OrdemDeServico o JOIN o.fkEntrada e JOIN e.fkVeiculo v JOIN v.fkCliente c JOIN c.fkOficina ofi WHERE ofi.idOficina = :idOficina")
    List<OrdemDeServico> findAllByIdOficina(@Param("idOficina") Integer idOficina);

    @Query("SELECT o FROM OrdemDeServico o JOIN o.fkEntrada e JOIN e.fkVeiculo v JOIN v.fkCliente c JOIN c.fkOficina ofi WHERE o.idOrdemServico = :idOrdem AND ofi.idOficina = :idOficina")
    Optional<OrdemDeServico> findByIdAndIdOficina(@Param("idOrdem") Integer idOrdem, @Param("idOficina") Integer idOficina);

    Boolean existsByFkEntrada(RegistroEntrada registroEntrada);

    // KPI Queries
    @Query(value = "SELECT quantidade_nfs_pendentes FROM vw_kpi_nfs_pendentes WHERE id_oficina = :idOficina", nativeQuery = true)
    ViewNotaFiscal findViewNotasFicaisPendentes(@Param("idOficina") Integer idOficina);

    @Query(value = "SELECT total_pagamentos_realizados FROM vw_kpi_pgtos_realizados WHERE id_oficina = :idOficina", nativeQuery = true)
    ViewPagtoRealizado findViewPagamentoRealizados(@Param("idOficina") Integer idOficina);

    @Query(value = "SELECT total_valor_nao_pago, quantidade_servicos_nao_pagos FROM vw_kpi_servicos_nao_pagos WHERE id_oficina = :idOficina", nativeQuery = true)
    ViewPagtoPendente findViewPagamentoPendente(@Param("idOficina") Integer idOficina);

    @Query("SELECT o FROM OrdemDeServico o " +
            "JOIN o.fkEntrada e " +
            "JOIN e.fkVeiculo v " +
            "JOIN v.fkCliente c " +
            "JOIN c.fkOficina ofi " +
            "WHERE o.nfRealizada = :nfRealizada " +
            "AND o.pagtRealizado = :pagtRealizado " +
            "AND ofi.idOficina = :idOficina " +
            "AND o.status = 'FINALIZADO'")
    List<OrdemDeServico> findByNfRealizadaAndPagtRealizadoAndIdOficinaAndIsFinalizado(
            @Param("nfRealizada") Boolean nfRealizada,
            @Param("pagtRealizado") Boolean pagtRealizado,
            @Param("idOficina") Integer idOficina);

    @Query(value = "SELECT o FROM OrdemDeServico o JOIN o.fkEntrada e JOIN e.fkVeiculo v JOIN v.fkCliente c JOIN c.fkOficina ofi WHERE ofi.idOficina = :idOficina AND o.dataSaidaEfetiva >= :intervalo AND o.status = 'FINALIZADO'")
    List<OrdemDeServico> findAllByIntervaloMeses(@Param("intervalo") LocalDate intervalo, @Param("idOficina") Integer idOficina);
}
