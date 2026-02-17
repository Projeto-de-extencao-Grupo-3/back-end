package geo.track.repository;

import geo.track.domain.OrdemDeServico;
import geo.track.domain.RegistroEntrada;
import geo.track.dto.os.response.ViewNotaFiscal;
import geo.track.dto.os.response.ViewPagtoPendente;
import geo.track.dto.os.response.ViewPagtoRealizado;
import geo.track.enums.os.StatusVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Integer> {
    @Query("SELECT o FROM OrdemDeServico o JOIN o.fkEntrada e JOIN e.fkVeiculo v WHERE v.placa = :placa")
    List<OrdemDeServico> findByPlaca(String placa);

    List<OrdemDeServico> findByStatus(StatusVeiculo status);

    @Query("SELECT o FROM OrdemDeServico o WHERE o.dataSaidaEfetiva >= :data")
    List<OrdemDeServico> findByStatusUltimos30Dias(LocalDate data);

    Boolean existsByFkEntrada(RegistroEntrada registroEntrada);

    // quero buscar as notas ficais pendentes a partir da view 'vw_kpi_nfs_pendentes'
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
            "AND ofi.idOficina = :idOficina")
    List<OrdemDeServico> findByNfRealizadaAndPagtRealizadoAndIdOficina(
            @Param("nfRealizada") Boolean nfRealizada,
            @Param("pagtRealizado") Boolean pagtRealizado,
            @Param("idOficina") Integer idOficina);
}
