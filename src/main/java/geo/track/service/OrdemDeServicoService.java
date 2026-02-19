package geo.track.service;

import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.domain.RegistroEntrada;
import geo.track.dto.os.request.*;
import geo.track.dto.os.response.*;
import geo.track.enums.os.StatusVeiculo;
import geo.track.exception.BadRequestException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.ForbiddenException;
import geo.track.repository.OrdemDeServicoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdemDeServicoService {
    private final OrdemDeServicoRepository ORDEM_REPOSITORY;
    private final ItemServicoService ITEM_SERVICO_SERVICE;
    private final RegistroEntradaService REGISTRO_ENTRADA_SERVICE;

    public OrdemDeServico postOrdem(@Valid @RequestBody PostEntradaVeiculo ordemDTO) {
        RegistroEntrada entrada = REGISTRO_ENTRADA_SERVICE.findRegistroById(ordemDTO.getFkEntrada());

//      Data prevista ser da seguinte forma : Data atual(HOJE) + 1 Mês para frente
        LocalDate dataPrevista = LocalDate.now().plusMonths(1);

        OrdemDeServico ordem = OrdemDeServico.builder()
                .status(ordemDTO.getStatus())
                .dtSaidaPrevista(dataPrevista)
                .dtSaidaEfetiva(null)
                .seguradora(false)
                .nfRealizada(false)
                .pagtRealizado(false)
                .ativo(true)
                .fkEntrada(entrada)
                .build();

        return ORDEM_REPOSITORY.save(ordem);
    }

    public List<OrdemDeServico> findOrdem(Integer idOficina){
        return ORDEM_REPOSITORY.findAllByIdOficina(idOficina);
    }

    public OrdemDeServico findOrdemById(Integer idOrdem, Integer idOficina){
        Optional<OrdemDeServico> ordem = ORDEM_REPOSITORY.findByIdAndIdOficina(idOrdem, idOficina);

        if (ordem.isEmpty()){
            throw new DataNotFoundException("O ID %d não foi encontrado ou não pertence a esta oficina".formatted(idOrdem), "Ordem de Serviço");
        }
        return ordem.get();
    }

    public OrdemDeServico putValorESaida(RequestPutValorESaida ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setDataSaidaPrevista(ordemDTO.getSaidaPrevista());

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico patchSaidaEfetiva(RequestPatchSaidaEfetiva ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }
        
        OrdemDeServico ordem = ordemOPT.get();
        
        ordem.setDataSaidaEfetiva(ordemDTO.getDtSaidaEfeiva());
        
        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico patchStatus(RequestPatchStatus ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setStatus(ordemDTO.getStatus());

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico patchSeguradora(RequestPatchSeguradora ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setSeguradora(ordemDTO.getSeguradora());

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico patchNfRealizada(RequestPatchNfRealizada ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setSeguradora(ordemDTO.getNfRealizada());

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico patchPagtoRealizado(RequestPatchPagtoRealizado ordemDTO){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(ordemDTO.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setPagtRealizado(ordemDTO.getPagtoRealizado());
        return ORDEM_REPOSITORY.save(ordem);
    }

    public void deleteOrdem(Integer idOrdem){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(idOrdem);

        if (ordemOPT.isEmpty()) {
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();
        RegistroEntrada entrada = ordem.getFkEntrada();

        List<ItemServico> servicos = ITEM_SERVICO_SERVICE.listarPelaOrdemServico(ordem);

        if (!servicos.isEmpty()) {
            throw new BadRequestException("Não é possível deletar ordem de serviço que possui serviços anexados", "Ordem de Serviço");
        }

        if (entrada == null){
            throw new ForbiddenException("Solicitação recusada", "Ordem de Serviço");
        }

        // verificar se tem serviços atrelado
        ORDEM_REPOSITORY.delete(ordem);
    }

    public List<OrdemDeServico> findOrdemByPlaca(String placa, Integer idOficina) {
        return ORDEM_REPOSITORY.findByPlacaAndIdOficina(placa, idOficina);
    }

    public List<OrdemDeServico> findOrdemByStatus(StatusVeiculo status, Integer idOficina) {
        return ORDEM_REPOSITORY.findByStatusAndIdOficina(status, idOficina);
    }

    public List<OrdemDeServico> findOrdemByStatusUltimos30Dias(Integer idOficina) {
        LocalDate dataLimite = LocalDate.now().minusDays(30L);
        return ORDEM_REPOSITORY.findByStatusUltimos30DiasAndIdOficina(dataLimite, idOficina);
    }

    public Boolean existsOrdemByRegistroEntrada(Integer idRegistroEtrada) {
        RegistroEntrada entrada = REGISTRO_ENTRADA_SERVICE.findRegistroById(idRegistroEtrada);
        return ORDEM_REPOSITORY.existsByFkEntrada(entrada);
    }

    public ViewNotaFiscal findKpiNotaFiscal(Integer idOrdem) {
        ViewNotaFiscal viewNotasFicaisPendentes = ORDEM_REPOSITORY.findViewNotasFicaisPendentes(idOrdem);
        if (viewNotasFicaisPendentes == null) {
            return new ViewNotaFiscal(0L);
        }
        return viewNotasFicaisPendentes;
    }

    public ViewPagtoRealizado findKpiPagamentoRealizado(Integer idOrdem) {
        ViewPagtoRealizado viewPagamentoRealizados = ORDEM_REPOSITORY.findViewPagamentoRealizados(idOrdem);
        if (viewPagamentoRealizados == null) {
            return new ViewPagtoRealizado(0L);
        }
        return viewPagamentoRealizados;
    }

    public ViewPagtoPendente findKpiPagamentoPendente(Integer idOrdem) {
        ViewPagtoPendente viewPagamentoPendente = ORDEM_REPOSITORY.findViewPagamentoPendente(idOrdem);
        if (viewPagamentoPendente == null) {
            return new ViewPagtoPendente(0.0, 0L);
        }
        return viewPagamentoPendente;
    }

    public List<OrdemDeServico> findOrdemByNfRealizadaAndPagtRealizado(Boolean nfRealizada, Boolean pagtRealizado, Integer idOficina) {
        return ORDEM_REPOSITORY.findByNfRealizadaAndPagtRealizadoAndIdOficinaAndIsFinalizado(nfRealizada, pagtRealizado, idOficina);
    }
}
