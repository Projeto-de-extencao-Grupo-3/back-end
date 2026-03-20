package geo.track.jornada.entity.repository;

import geo.track.dto.os.response.ViewNotaFiscal;
import geo.track.dto.os.response.ViewPagtoPendente;
import geo.track.dto.os.response.ViewPagtoRealizado;
import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
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
    List<OrdemDeServico> findByStatus(@Param("status") StatusVeiculo status);

    @Query("SELECT o FROM OrdemDeServico o WHERE o.dataSaidaEfetiva >= :data")
    List<OrdemDeServico> findByStatusUltimos30Dias(@Param("data") LocalDate data);

    Boolean existsByFkEntrada(RegistroEntrada registroEntrada);

    // KPI Queries
    @Query(value = "SELECT quantidade_nfs_pendentes FROM vw_kpi_nfs_pendentes WHERE id_oficina = :idOficina", nativeQuery = true)
    ViewNotaFiscal findViewNotasFicaisPendentes(@Param("idOficina") Integer idOficina);

    @Query(value = "SELECT total_pagamentos_realizados FROM vw_kpi_pgtos_realizados WHERE id_oficina = :idOficina", nativeQuery = true)
    ViewPagtoRealizado findViewPagamentoRealizados(@Param("idOficina") Integer idOficina);

    @Query(value = "SELECT total_valor_nao_pago, quantidade_servicos_nao_pagos FROM vw_kpi_servicos_nao_pagos WHERE id_oficina = :idOficina", nativeQuery = true)
    ViewPagtoPendente findViewPagamentoPendente(@Param("idOficina") Integer idOficina);

    @Query("SELECT o FROM OrdemDeServico o " +
            "WHERE o.nfRealizada = :nfRealizada " +
            "AND o.pagtRealizado = :pagtRealizado")
    List<OrdemDeServico> findByNfRealizadaAndPagtRealizado(
            @Param("nfRealizada") Boolean nfRealizada,
            @Param("pagtRealizado") Boolean pagtRealizado);

    @Query("SELECT o FROM OrdemDeServico o WHERE o.dataSaidaEfetiva >= :intervalo")
    List<OrdemDeServico> findAllByIntervaloMeses(@Param("intervalo") LocalDate intervalo);
}
