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
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.OrdemDeServicoExceptionMessages;
import geo.track.exception.constraint.message.RegistroEntradaExceptionMessages;
import geo.track.log.Log;
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
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final Log Log;

    public OrdemDeServico cadastrarOrdemServico(@Valid @RequestBody RequestPostEntradaVeiculo body) {
        Log.info("Iniciando cadastro de Ordem de Serviço para a entrada ID: {}", body.getFkEntrada());
        RegistroEntrada entrada = REGISTRO_ENTRADA_REPOSITORY.findById(body.getFkEntrada()).orElseThrow(()-> new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, Domains.REGISTRO_ENTRADA));

        OrdemDeServico ordem = OrdemDeServico.builder()
                .status(body.getStatus())
                .valorTotal(null)
                .valorTotalServicos(null)
                .valorTotalProdutos(null)
                .dataSaidaPrevista(null)
                .dataSaidaEfetiva(null)
                .seguradora(false)
                .nfRealizada(false)
                .pagtRealizado(false)
                .ativo(true)
                .fkEntrada(entrada)
                .build();

        OrdemDeServico salva = ORDEM_REPOSITORY.save(ordem);
        Log.info("Ordem de Serviço cadastrada com sucesso. ID Gerado: {}", salva.getIdOrdemServico());
        return salva;
    }

    public List<OrdemDeServico> listarOrdensServico(Integer idOficina){
        Log.info("Listando todas as Ordens de Serviço da oficina ID: {}", idOficina);
        return ORDEM_REPOSITORY.findAllByIdOficina(idOficina);
    }

    public OrdemDeServico buscarOrdemServicoPorId(Integer idOrdem, Integer idOficina){
        Log.info("Buscando Ordem de Serviço ID: {} para a oficina ID: {}", idOrdem, idOficina);
        Optional<OrdemDeServico> ordem = ORDEM_REPOSITORY.findByIdAndIdOficina(idOrdem, idOficina);

        if (ordem.isEmpty()){
            throw new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO);
        }
        return ordem.get();
    }

    public OrdemDeServico atualizarValorESaida(RequestPutValorESaida body){
        Log.info("Atualizando data de saída prevista da Ordem de Serviço ID: {}", body.getIdOrdem());
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO);
        }

        OrdemDeServico ordem = ordemOPT.get();

        if (body.getSaidaPrevista() != null) {
            ordem.setDataSaidaPrevista(body.getSaidaPrevista());
        }

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico atualizarSaidaEfetiva(RequestPatchSaidaEfetiva body){
        Log.info("Atualizando data de saída efetiva da Ordem de Serviço ID: {}", body.getIdOrdem());
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO);
        }
        
        OrdemDeServico ordem = ordemOPT.get();
        
        ordem.setDataSaidaEfetiva(body.getDataSaidaEfetiva());
        
        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico atualizarStatus(RequestPatchStatus body){
        Log.info("Atualizando status da Ordem de Serviço ID: {} para {}", body.getIdOrdem(), body.getStatus());
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO);
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setStatus(body.getStatus());
        ordem.setDataAtualizacao(LocalDate.now());

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico atualizarSeguradora(RequestPatchSeguradora body){
        Log.info("Atualizando flag de seguradora da Ordem de Serviço ID: {} para {}", body.getIdOrdem(), body.getSeguradora());
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO);
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setSeguradora(body.getSeguradora());

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico atualizarNotaFiscalRealizada(RequestPatchNfRealizada body){
        Log.info("Atualizando status de Nota Fiscal da Ordem de Serviço ID: {} para {}", body.getIdOrdem(), body.getNfRealizada());
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO);
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setSeguradora(body.getNfRealizada());

        return ORDEM_REPOSITORY.save(ordem);
    }

    public OrdemDeServico atualizarPagamentoRealizado(RequestPatchPagtoRealizado body){
        Log.info("Atualizando status de pagamento da Ordem de Serviço ID: {} para {}", body.getIdOrdem(), body.getPagtoRealizado());
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(body.getIdOrdem());

        if (ordemOPT.isEmpty()){
            throw new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO);
        }

        OrdemDeServico ordem = ordemOPT.get();

        ordem.setPagtRealizado(body.getPagtoRealizado());
        return ORDEM_REPOSITORY.save(ordem);
    }

    public void deletarOrdemServico(Integer idOrdem){
        Log.info("Tentando deletar Ordem de Serviço ID: {}", idOrdem);
        Optional<OrdemDeServico> ordemOPT = ORDEM_REPOSITORY.findById(idOrdem);

        if (ordemOPT.isEmpty()) {
            throw new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO);
        }

        OrdemDeServico ordem = ordemOPT.get();
        RegistroEntrada entrada = ordem.getFkEntrada();

        List<ItemServico> servicos = ITEM_SERVICO_SERVICE.listarPelaOrdemServico(ordem);

        if (!servicos.isEmpty()) {
            throw new BadRequestException(OrdemDeServicoExceptionMessages.ORDEM_NAO_PODE_SER_DELETADA_COM_SERVICOS, Domains.ORDEM_DE_SERVICO);
        }

        if (entrada == null){
            throw new ForbiddenException(OrdemDeServicoExceptionMessages.SOLICITACAO_RECUSADA, Domains.ORDEM_DE_SERVICO);
        }

        // verificar se tem serviços atrelado
        ORDEM_REPOSITORY.delete(ordem);
        Log.info("Ordem de Serviço ID: {} deletada com sucesso", idOrdem);
    }

    public List<OrdemDeServico> buscarOrdemServicoPorPlaca(String placa, Integer idOficina) {
        Log.info("Buscando Ordens de Serviço pela placa: {} na oficina ID: {}", placa, idOficina);
        return ORDEM_REPOSITORY.findByPlacaAndIdOficina(placa, idOficina);
    }

    public List<OrdemDeServico> buscarOrdemPorStatus(StatusVeiculo status, Integer idOficina) {
        LocalDate dataLimite = LocalDate.now().minusDays(30L);
        Log.info("Buscando Ordens de Serviço com status: {} na oficina ID: {}", status, idOficina);
        if (status.equals(StatusVeiculo.FINALIZADO)) {
            return ORDEM_REPOSITORY.findByStatusUltimos30DiasAndIdOficina(dataLimite, idOficina);

        } else {
            return ORDEM_REPOSITORY.findByStatusAndIdOficina(status, idOficina);
        }
    }

    public Boolean existeOrdemServicoPorEntrada(Integer idRegistroEtrada) {
        Log.info("Verificando existência de Ordem de Serviço para entrada ID: {}", idRegistroEtrada);
        RegistroEntrada entrada = REGISTRO_ENTRADA_REPOSITORY.findById(idRegistroEtrada).orElseThrow(()-> new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, Domains.REGISTRO_ENTRADA));
        return ORDEM_REPOSITORY.existsByFkEntrada(entrada);
    }

    public ViewNotaFiscal exibirKpiNotaFiscal(Integer idOrdem) {
        Log.info("Calculando KPI de Nota Fiscal para Ordem ID: {}", idOrdem);
        ViewNotaFiscal viewNotasFicaisPendentes = ORDEM_REPOSITORY.findViewNotasFicaisPendentes(idOrdem);
        if (viewNotasFicaisPendentes == null) {
            return new ViewNotaFiscal(0L);
        }
        return viewNotasFicaisPendentes;
    }

    public ViewPagtoRealizado exibirKpiPagtoRealizado(Integer idOrdem) {
        Log.info("Calculando KPI de Pagamento Realizado para Ordem ID: {}", idOrdem);
        ViewPagtoRealizado viewPagamentoRealizados = ORDEM_REPOSITORY.findViewPagamentoRealizados(idOrdem);
        if (viewPagamentoRealizados == null) {
            return new ViewPagtoRealizado(0L);
        }
        return viewPagamentoRealizados;
    }

    public ViewPagtoPendente exibirKpiPagtoPendente(Integer idOrdem) {
        Log.info("Calculando KPI de Pagamento Pendente para Ordem ID: {}", idOrdem);
        ViewPagtoPendente viewPagamentoPendente = ORDEM_REPOSITORY.findViewPagamentoPendente(idOrdem);
        if (viewPagamentoPendente == null) {
            return new ViewPagtoPendente(0.0, 0L);
        }
        return viewPagamentoPendente;
    }

    public List<OrdemDeServico> buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(Boolean nfRealizada, Boolean pagtRealizado, Integer idOficina) {
        Log.info("Filtrando Ordens por NF: {}, Pagamento: {} na oficina ID: {}", nfRealizada, pagtRealizado, idOficina);
        return ORDEM_REPOSITORY.findByNfRealizadaAndPagtRealizadoAndIdOficinaAndIsFinalizado(nfRealizada, pagtRealizado, idOficina);
    }

    public List<OrdemDeServico> listarOrdensServicoIntervaloMeses(Integer intervalo, Integer idOficina) {
        LocalDate dataInferiorIntervalo = LocalDate.now().minusMonths(intervalo);

        return ORDEM_REPOSITORY.findAllByIntervaloMeses(dataInferiorIntervalo, idOficina);
    }
}