package geo.track.service;

import geo.track.gestao.entity.ItemServico;
import geo.track.dto.os.request.*;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.response.listagem.ViewNotaFiscal;
import geo.track.jornada.response.listagem.ViewPagtoPendente;
import geo.track.jornada.response.listagem.ViewPagtoRealizado;
import geo.track.enums.os.StatusVeiculo;
import geo.track.exception.BadRequestException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.ForbiddenException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.OrdemDeServicoExceptionMessages;
import geo.track.exception.constraint.message.RegistroEntradaExceptionMessages;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.entity.repository.RegistroEntradaRepository;
import geo.track.log.Log;
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

    public List<OrdemDeServico> listarOrdensServico(){
        Log.info("Listando todas as Ordens de Serviço");
        return ORDEM_REPOSITORY.findAll();
    }

    public OrdemDeServico buscarOrdemServicoPorId(Integer idOrdem){
        Log.info("Buscando Ordem de Serviço ID: {}", idOrdem);
        Optional<OrdemDeServico> ordem = ORDEM_REPOSITORY.findById(idOrdem);

        if (ordem.isEmpty()){
            throw new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO);
        }
        return ordem.get();
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

    public List<OrdemDeServico> buscarOrdemServicoPorPlaca(String placa) {
        Log.info("Buscando Ordens de Serviço pela placa: {}", placa);
        return ORDEM_REPOSITORY.findByPlaca(placa);
    }

    public List<OrdemDeServico> buscarOrdemPorStatus(StatusVeiculo status) {
        LocalDate dataLimite = LocalDate.now().minusDays(30L);
        Log.info("Buscando Ordens de Serviço com status: {}", status);
        if (status.equals(StatusVeiculo.FINALIZADO)) {
            return ORDEM_REPOSITORY.findByStatusUltimos30Dias(dataLimite);

        } else {
            return ORDEM_REPOSITORY.findByStatus(status);
        }
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

    public List<OrdemDeServico> buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(Boolean nfRealizada, Boolean pagtRealizado) {
        Log.info("Filtrando Ordens por NF: {}, Pagamento: {}", nfRealizada, pagtRealizado);
        return ORDEM_REPOSITORY.findByNfRealizadaAndPagtRealizado(nfRealizada, pagtRealizado);
    }

    public List<OrdemDeServico> listarOrdensServicoIntervaloMeses(Integer intervalo) {
        LocalDate dataInferiorIntervalo = LocalDate.now().minusMonths(intervalo);

        return ORDEM_REPOSITORY.findByIntervaloMeses(dataInferiorIntervalo);
    }
}