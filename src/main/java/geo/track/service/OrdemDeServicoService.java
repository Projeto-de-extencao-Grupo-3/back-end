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
import geo.track.repository.RegistroEntradaRepository;
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
    private final RegistroEntradaRepository REGISTRO_ENTRADA_SERVICE;

    public OrdemDeServico cadastrarOrdemServico(@Valid @RequestBody PostEntradaVeiculo body) {
        RegistroEntrada entrada = REGISTRO_ENTRADA_SERVICE.findById(body.getFkEntrada()).orElseThrow(()-> new DataNotFoundException("FODEU", "fodeu" +
                ""));

        OrdemDeServico ordem = OrdemDeServico.builder()
                .status(body.getStatus())
                .dtSaidaPrevista(null)
                .dtSaidaEfetiva(null)
                .seguradora(false)
                .nfRealizada(false)
                .pagtRealizado(false)
                .ativo(true)
                .fkEntrada(entrada)
                .build();

        return ORDEM_REPOSITORY.save(ordem);
    }

    public List<OrdemDeServico> listarOrdensServico(Integer idOficina){
        return ORDEM_REPOSITORY.findAllByIdOficina(idOficina);
    }

    public OrdemDeServico buscarOrdemServicoPorId(Integer idOrdem, Integer idOficina){
        Optional<OrdemDeServico> ordem = ORDEM_REPOSITORY.findByIdAndIdOficina(idOrdem, idOficina);

        if (ordem.isEmpty()){
            throw new DataNotFoundException("O ID %d não foi encontrado ou não pertence a esta oficina".formatted(idOrdem), "Ordem de Serviço");
        }
        return ordem.get();
    }

    public OrdemDeServico atualizarValorESaida(RequestPutValorESaida body){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        if (body.getSaidaPrevista() != null) {
            ordem.setDataSaidaPrevista(body.getSaidaPrevista());
        }

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico atualizarSaidaEfetiva(RequestPatchSaidaEfetiva body){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }
        
        OrdemDeServico ordem = ordemOPT.get();
        
        ordem.setDataSaidaEfetiva(body.getDtSaidaEfeiva());
        
        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico atualizarStatus(RequestPatchStatus body){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setStatus(body.getStatus());

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico atualizarSeguradora(RequestPatchSeguradora body){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setSeguradora(body.getSeguradora());

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico atualizarNotaFiscalRealizada(RequestPatchNfRealizada body){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setSeguradora(body.getNfRealizada());

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico atualizarPagamentoRealizado(RequestPatchPagtoRealizado body){
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma ordem com esse ID", "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setPagtRealizado(body.getPagtoRealizado());
        return ORDEM_REPOSITORY.save(ordem);
    }

    public void deletarOrdemServico(Integer idOrdem){
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

    public List<OrdemDeServico> buscarOrdemServicoPorPlaca(String placa, Integer idOficina) {
        return ORDEM_REPOSITORY.findByPlacaAndIdOficina(placa, idOficina);
    }

    public List<OrdemDeServico> buscarOrdemPorStatus(StatusVeiculo status, Integer idOficina) {
        LocalDate dataLimite = LocalDate.now().minusDays(30L);
        if (status.equals(StatusVeiculo.FINALIZADO)) {
            return ORDEM_REPOSITORY.findByStatusUltimos30DiasAndIdOficina(dataLimite, idOficina);

        } else {
            return ORDEM_REPOSITORY.findByStatusAndIdOficina(status, idOficina);
        }
    }

    public Boolean existeOrdemServicoPorEntrada(Integer idRegistroEtrada) {
        RegistroEntrada entrada = REGISTRO_ENTRADA_SERVICE.findById(idRegistroEtrada).orElseThrow(()-> new DataNotFoundException("FODEU", "fodeu" +
                ""));
        return ORDEM_REPOSITORY.existsByFkEntrada(entrada);
    }

    public ViewNotaFiscal exibirKpiNotaFiscal(Integer idOrdem) {
        ViewNotaFiscal viewNotasFicaisPendentes = ORDEM_REPOSITORY.findViewNotasFicaisPendentes(idOrdem);
        if (viewNotasFicaisPendentes == null) {
            return new ViewNotaFiscal(0L);
        }
        return viewNotasFicaisPendentes;
    }

    public ViewPagtoRealizado exibirKpiPagtoRealizado(Integer idOrdem) {
        ViewPagtoRealizado viewPagamentoRealizados = ORDEM_REPOSITORY.findViewPagamentoRealizados(idOrdem);
        if (viewPagamentoRealizados == null) {
            return new ViewPagtoRealizado(0L);
        }
        return viewPagamentoRealizados;
    }

    public ViewPagtoPendente exibirKpiPagtoPendente(Integer idOrdem) {
        ViewPagtoPendente viewPagamentoPendente = ORDEM_REPOSITORY.findViewPagamentoPendente(idOrdem);
        if (viewPagamentoPendente == null) {
            return new ViewPagtoPendente(0.0, 0L);
        }
        return viewPagamentoPendente;
    }

    public List<OrdemDeServico> buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(Boolean nfRealizada, Boolean pagtRealizado, Integer idOficina) {
        return ORDEM_REPOSITORY.findByNfRealizadaAndPagtRealizadoAndIdOficinaAndIsFinalizado(nfRealizada, pagtRealizado, idOficina);
    }
}