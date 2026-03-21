package geo.track.service;

import geo.track.jornada.entity.RegistroEntrada;
import geo.track.entity.Veiculo;
import geo.track.dto.os.request.RequestPostEntradaVeiculo;
import geo.track.dto.registroEntrada.request.RequestPostEntrada;
import geo.track.dto.registroEntrada.request.RequestPostEntradaAgendada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.enums.os.StatusVeiculo;
import geo.track.exception.BadBusinessRuleException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.RegistroEntradaExceptionMessages;
import geo.track.jornada.entity.repository.RegistroEntradaRepository;
import geo.track.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RegistroEntradaService{
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final VeiculoService VEICULO_SERVICE;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final Log log;

    public RegistroEntrada realizarAgendamentoVeiculo(RequestPostEntradaAgendada body){
        log.info("Iniciando agendamento de entrada para o veículo ID: {}", body.getFkVeiculo());
        validarExistenciaOrdemServicoEmAndamentoVeiculo(body.getFkVeiculo());

        RegistroEntrada registro = new RegistroEntrada();
        Veiculo veiculo = VEICULO_SERVICE.findVeiculoById(body.getFkVeiculo());

        registro.setIdRegistroEntrada(null);
        registro.setDataEntradaPrevista(body.getDtEntradaPrevista());
        registro.setFkVeiculo(veiculo);

        RegistroEntrada entrada = REGISTRO_ENTRADA_REPOSITORY.save(registro);
        registro.setFkOrdemServico(ORDEM_SERVICO_SERVICE.cadastrarOrdemServico(new RequestPostEntradaVeiculo(StatusVeiculo.AGUARDANDO_ENTRADA, entrada.getIdRegistroEntrada())));
        log.info("Agendamento realizado com sucesso para o veículo ID: {}. Registro ID: {}", body.getFkVeiculo(), entrada.getIdRegistroEntrada());
        return REGISTRO_ENTRADA_REPOSITORY.save(entrada);
    }

    public RegistroEntrada realizarEntradaVeiculo(RequestPostEntrada body){
        log.info("Realizando entrada efetiva do veículo ID: {}", body.fkVeiculo());
        validarExistenciaOrdemServicoEmAndamentoVeiculo(body.fkVeiculo());

        Veiculo veiculo = VEICULO_SERVICE.findVeiculoById(body.fkVeiculo());

        RegistroEntrada registro = new RegistroEntrada();
        registro.setDataEntradaEfetiva(body.dataEntradaEfetiva());
        registro.setDataEntradaPrevista(body.dataEntradaEfetiva());
        registro.setResponsavel(body.nomeResponsavel());
        registro.setCpf(body.cpfResponsavel());
        registro.setExtintor(body.quantidadeExtintor());
        registro.setMacaco(body.quantidadeMacaco());
        registro.setChaveRoda(body.quantidadeChaveRoda());
        registro.setGeladeira(body.quantidadeGeladeira());
        registro.setMonitor(body.quantidadeMonitor());
        registro.setEstepe(body.quantidadeEstepe());
        registro.setSomDvd(body.quantidadeSomDvd());
        registro.setCaixaFerramentas(body.quantidadeCaixaFerramentas());
        registro.setObservacoes(body.observacoes());
        registro.setFkVeiculo(veiculo);

        RegistroEntrada entrada = REGISTRO_ENTRADA_REPOSITORY.save(registro);
        entrada.setFkOrdemServico(ORDEM_SERVICO_SERVICE.cadastrarOrdemServico(new RequestPostEntradaVeiculo(StatusVeiculo.AGUARDANDO_ORCAMENTO, entrada.getIdRegistroEntrada())));

        log.info("Entrada do veículo ID: {} registrada com sucesso. Registro ID: {}", body.fkVeiculo(), entrada.getIdRegistroEntrada());
        return REGISTRO_ENTRADA_REPOSITORY.save(entrada);
    }

    public List<RegistroEntrada> listarEntradas(){
        log.info("Listando todos os registros de entrada.");
        return REGISTRO_ENTRADA_REPOSITORY.findAll();
    }

    public RegistroEntrada buscarEntradaPorId(Integer idRegistro){
        log.info("Buscando registro de entrada ID: {}", idRegistro);
        Optional<RegistroEntrada> registro = REGISTRO_ENTRADA_REPOSITORY.findById(idRegistro);

        if (registro.isEmpty()){
            throw new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, Domains.REGISTRO_ENTRADA);
        }

        return registro.get();
    }

    public RegistroEntrada atualizarEntradaVeiculoAgendado(RequestPutRegistroEntrada body) {
        log.info("Atualizando registro de entrada ID: {}", body.getIdRegistro());
        // Busca o registro ou lança a exceção diretamente (Clean Code)
        RegistroEntrada registro = REGISTRO_ENTRADA_REPOSITORY.findById(body.getIdRegistro())
                .orElseThrow(() -> new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, Domains.REGISTRO_ENTRADA));

        // Verifica cada campo antes de atualizar
        if (body.getDtEntradaEfetiva() != null) {
            registro.setDataEntradaEfetiva(body.getDtEntradaEfetiva());
        }

        if (body.getResponsavel() != null) {
            registro.setResponsavel(body.getResponsavel());
        }

        if (body.getCpf() != null) {
            registro.setCpf(body.getCpf());
        }

        if (body.getExtintor() != null) {
            registro.setExtintor(body.getExtintor());
        }

        if (body.getMacaco() != null) {
            registro.setMacaco(body.getMacaco());
        }

        if (body.getChaveRoda() != null) {
            registro.setChaveRoda(body.getChaveRoda());
        }

        if (body.getGeladeira() != null) {
            registro.setGeladeira(body.getGeladeira());
        }

        if (body.getMonitor() != null) {
            registro.setMonitor(body.getMonitor());
        }

        if (body.getEstepe() != null) {
            registro.setEstepe(body.getEstepe());
        }

        if (body.getSomDvd() != null) {
            registro.setSomDvd(body.getSomDvd());
        }

        if (body.getCaixaFerramentas() != null) {
            registro.setCaixaFerramentas(body.getCaixaFerramentas());
        }

        log.info("Registro de entrada ID: {} atualizado com sucesso.", body.getIdRegistro());
        return REGISTRO_ENTRADA_REPOSITORY.save(registro);
    }

    public void deletarEntrada(Integer idRegistro){
        log.info("Tentando deletar registro de entrada ID: {}", idRegistro);
        if (!REGISTRO_ENTRADA_REPOSITORY.existsById(idRegistro)){
            throw new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, Domains.REGISTRO_ENTRADA);
        }

        REGISTRO_ENTRADA_REPOSITORY.deleteById(idRegistro);
        log.info("Registro de entrada ID: {} deletado com sucesso.", idRegistro);
    }

    private void validarExistenciaOrdemServicoEmAndamentoVeiculo(Integer idVeiculo) {
        if (REGISTRO_ENTRADA_REPOSITORY.existsOrdensNaoFinalizadas(idVeiculo)){
            log.warn("Tentativa de entrada negada: Veículo ID {} já possui ordem de serviço em andamento.", idVeiculo);
            throw new BadBusinessRuleException(RegistroEntradaExceptionMessages.VEICULO_JA_POSSUI_ENTRADA_EM_ANDAMENTO, Domains.REGISTRO_ENTRADA);
        }
    }
}
