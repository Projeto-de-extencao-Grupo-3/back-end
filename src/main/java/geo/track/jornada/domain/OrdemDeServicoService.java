package geo.track.jornada.domain;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ItemServico;
import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.response.listagem.ViewNotaFiscal;
import geo.track.jornada.infraestructure.response.listagem.ViewPagtoPendente;
import geo.track.jornada.infraestructure.response.listagem.ViewPagtoRealizado;
import geo.track.infraestructure.exception.BadRequestException;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.ForbiddenException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.OrdemDeServicoExceptionMessages;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.infraestructure.log.Log;
import geo.track.catalogo.item_servico.domain.ItemServicoService;
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

    public OrdemDeServico buscarUltimaOrdemServicoPorVeiculo(Integer idVeiculo) {
        Log.info("Buscando última Ordem de Serviço para o veículo ID: {}", idVeiculo);
        Optional<OrdemDeServico> ordem = ORDEM_REPOSITORY.findLastOrdemServicoVeiculo(idVeiculo);

        if (ordem.isEmpty()) {
            throw new DataNotFoundException(OrdemDeServicoExceptionMessages.ULTIMA_ORDEM_NAO_ENCONTRADA_POR_VEICULO, Domains.ORDEM_DE_SERVICO);
        }
        return ordem.get();
    }

    public List<OrdemDeServico> listarOrdensServico(){
        Log.info("Listando todas as Ordens de Serviço");
        return ORDEM_REPOSITORY.findAll();
    }

    public Boolean existeOrdemServicoAbertaPorCliente(Integer idCliente) {
        Log.info("Verificando existência de Ordens de Serviço não finalizadas para o cliente ID: {}", idCliente);
        return ORDEM_REPOSITORY.existsByIdCliente(idCliente, Status.FINALIZADO);
    }

    public OrdemDeServico buscarOrdemServicoPorId(Integer idOrdem){
        Optional<OrdemDeServico> ordem = ORDEM_REPOSITORY.findById(idOrdem);

        if (ordem.isEmpty()){
            throw new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO);
        }
        return ordem.get();
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

    public ViewNotaFiscal exibirKpiNotaFiscal(Integer ano, Integer mes) {
        Log.info("Calculando KPI de Nota Fiscal para Ordem no ano {} e mês {}", ano, mes);
        ViewNotaFiscal viewNotasFicaisPendentes = ORDEM_REPOSITORY.findViewNotasFicaisPendentes(ano, mes);
        if (viewNotasFicaisPendentes == null) {
            return new ViewNotaFiscal(0L);
        }
        return viewNotasFicaisPendentes;
    }

    public ViewPagtoRealizado exibirKpiPagtoRealizado(Integer ano, Integer mes) {
        Log.info("Calculando KPI de Pagamento Realizado para Ordem no ano {} e mês {}", ano, mes);
        ViewPagtoRealizado viewPagamentoRealizados = ORDEM_REPOSITORY.findViewPagamentoRealizados(ano, mes);
        if (viewPagamentoRealizados == null) {
            return new ViewPagtoRealizado(0L);
        }
        return viewPagamentoRealizados;
    }

    public ViewPagtoPendente exibirKpiPagtoPendente(Integer ano, Integer mes) {
        Log.info("Calculando KPI de Pagamento Pendente para Ordem no ano {} e mês {}", ano, mes);
        ViewPagtoPendente viewPagamentoPendente = ORDEM_REPOSITORY.findViewPagamentoPendente(ano, mes);
        if (viewPagamentoPendente == null) {
            return new ViewPagtoPendente(0.0, 0L);
        }
        return viewPagamentoPendente;
    }

    public List<OrdemDeServico> buscarOrdemServicoPorNotaFiscalEPagamentoRealizado(Boolean nfRealizada, Boolean pagtRealizado, Integer ano, Integer mes) {
        Log.info("Filtrando Ordens por NF: {}, Pagamento: {}", nfRealizada, pagtRealizado, ano, mes);
        return ORDEM_REPOSITORY.findByListagemAnaliseFinanceiraStrategy(nfRealizada, pagtRealizado, ano, mes);
    }

    public List<OrdemDeServico> listarOrdensServicoIntervaloMeses(Integer idVeiculo, Integer intervalo) {
        LocalDate dataInferiorIntervalo = LocalDate.now().minusMonths(intervalo);

        return ORDEM_REPOSITORY.findByIntervaloMesesAndIdVeiculo(dataInferiorIntervalo, idVeiculo);
    }

    public List<OrdemDeServico> listarOrdensServicoIntervaloMeses(LocalDate dataReferencia) {
        return ORDEM_REPOSITORY.findByIntervaloMeses(dataReferencia);
    }

    public List<OrdemDeServico> listarOrdensServicoPorVeiculo(Integer idVeiculo) {
        return ORDEM_REPOSITORY.findAllByVeiculo(idVeiculo);
    }

    public Boolean existeOrdemServicoAbertaUsandoProduto(Integer idProduto) {
        return ORDEM_REPOSITORY.existsByIdProduto(idProduto, Status.FINALIZADO);
    }

    public void existeOrdemServicoAbertaPorVeiculo(Integer fkVeiculo) {
        Boolean existeOrdemAberta = ORDEM_REPOSITORY.existsByIdVeiculo(fkVeiculo);
        if (existeOrdemAberta) {
            throw new BadBusinessRuleException(OrdemDeServicoExceptionMessages.EXISTE_ORDEM_ABERTA_POR_VEICULO, Domains.ORDEM_DE_SERVICO);
        }
    }

    public List<OrdemDeServico> listarOrdensServicoEntreDataInicioEDataFim(LocalDate dataInicio, LocalDate dataFim) {
        System.out.println(dataInicio);
        System.out.println(dataFim);
        return ORDEM_REPOSITORY.findAllByDataInicioAndDataFim(dataInicio, dataFim);
    }
}