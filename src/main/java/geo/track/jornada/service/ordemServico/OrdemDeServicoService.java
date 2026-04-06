package geo.track.jornada.service.ordemServico;

import geo.track.gestao.entity.ItemServico;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.enums.Status;
import geo.track.jornada.response.listagem.ViewNotaFiscal;
import geo.track.jornada.response.listagem.ViewPagtoPendente;
import geo.track.jornada.response.listagem.ViewPagtoRealizado;
import geo.track.infraestructure.exception.BadRequestException;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.ForbiddenException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.OrdemDeServicoExceptionMessages;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.infraestructure.log.Log;
import geo.track.infraestructure.annotation.ToRefactor;
import geo.track.gestao.service.ItemServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdemDeServicoService {
    private final OrdemDeServicoRepository ORDEM_REPOSITORY;
    private final ItemServicoService ITEM_SERVICO_SERVICE;
    private final Log Log;


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

    public List<OrdemDeServico> buscarOrdemPorStatus(Status status) {
        LocalDate dataLimite = LocalDate.now().minusDays(30L);
        Log.info("Buscando Ordens de Serviço com status: {}", status);
        if (status.equals(Status.FINALIZADO)) {
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

    public List<OrdemDeServico> listarOrdensServicoIntervaloMeses(Integer idVeiculo, Integer intervalo) {
        LocalDate dataInferiorIntervalo = LocalDate.now().minusMonths(intervalo);

        return ORDEM_REPOSITORY.findByIntervaloMesesAndIdVeiculo(dataInferiorIntervalo, idVeiculo);
    }

    public List<OrdemDeServico> listarOrdensServicoPorVeiculo(Integer idVeiculo) {
        return ORDEM_REPOSITORY.findAllByVeiculo(idVeiculo);
    }
}