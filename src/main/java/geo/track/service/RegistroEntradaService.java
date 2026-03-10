package geo.track.service;

import geo.track.domain.OrdemDeServico;
import geo.track.domain.RegistroEntrada;
import geo.track.domain.Veiculo;
import geo.track.dto.os.request.PostEntradaVeiculo;
import geo.track.dto.registroEntrada.request.*;
import geo.track.dto.registroEntrada.request.RequestPostEntradaAgendada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.enums.os.StatusVeiculo;
import geo.track.exception.BadBusinessRuleException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.EnumDomains;
import geo.track.exception.constraint.message.OrdemDeServicoExceptionMessages;
import geo.track.exception.constraint.message.RegistroEntradaExceptionMessages;
import geo.track.repository.RegistroEntradaRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public RegistroEntrada realizarAgendamentoVeiculo(RequestPostEntradaAgendada body){
        validarExistenciaOrdemServicoEmAndamentoVeiculo(body.getFkVeiculo());

        Veiculo veiculo = VEICULO_SERVICE.findVeiculoById(body.getFkVeiculo());

        RegistroEntrada registro = new RegistroEntrada();
        registro.setDataEntradaPrevista(body.getDtEntradaPrevista());
        registro.setFkVeiculo(veiculo);

        // Salva apenas UMA vez para gerar o ID necessário para a Ordem de Serviço
        RegistroEntrada entrada = REGISTRO_ENTRADA_REPOSITORY.save(registro);

        // Cria a OS. O ID da entrada já existe aqui.
        OrdemDeServico os = ORDEM_SERVICO_SERVICE.cadastrarOrdemServico(
                new PostEntradaVeiculo(StatusVeiculo.AGUARDANDO_ENTRADA, entrada.getIdRegistroEntrada())
        );

        // Atualiza a referência em memória.
        entrada.setOrdemDeServicos(os);

        return entrada;
    }

    public RegistroEntrada realizarEntradaVeiculo(RequestPostEntrada body){
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
        registro.setFkVeiculo(veiculo);

        RegistroEntrada entrada = REGISTRO_ENTRADA_REPOSITORY.save(registro);
        entrada.setOrdemDeServicos(ORDEM_SERVICO_SERVICE.cadastrarOrdemServico(new PostEntradaVeiculo(StatusVeiculo.AGUARDANDO_ORCAMENTO, entrada.getIdRegistroEntrada())));

        return REGISTRO_ENTRADA_REPOSITORY.save(entrada);
    }

    public List<RegistroEntrada> listarEntradas(){
        return REGISTRO_ENTRADA_REPOSITORY.findAll();
    }

    public RegistroEntrada buscarEntradaPorId(Integer idRegistro){
        Optional<RegistroEntrada> registro = REGISTRO_ENTRADA_REPOSITORY.findById(idRegistro);

        if (registro.isEmpty()){
            throw new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, EnumDomains.REGISTRO_ENTRADA);
        }

        return registro.get();
    }

    public RegistroEntrada atualizarEntradaVeiculoAgendado(RequestPutRegistroEntrada body) {
        // Busca o registro ou lança a exceção diretamente (Clean Code)
        RegistroEntrada registro = REGISTRO_ENTRADA_REPOSITORY.findById(body.getIdRegistro())
                .orElseThrow(() -> new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, EnumDomains.REGISTRO_ENTRADA));

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

        return REGISTRO_ENTRADA_REPOSITORY.save(registro);
    }

    public void deletarEntrada(Integer idRegistro){
        if (!REGISTRO_ENTRADA_REPOSITORY.existsById(idRegistro)){
            throw new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, EnumDomains.REGISTRO_ENTRADA);
        }

        REGISTRO_ENTRADA_REPOSITORY.deleteById(idRegistro);
    }

    private void validarExistenciaOrdemServicoEmAndamentoVeiculo(Integer idVeiculo) {
        if (REGISTRO_ENTRADA_REPOSITORY.existsOrdensNaoFinalizadas(idVeiculo)){
            throw new BadBusinessRuleException(RegistroEntradaExceptionMessages.VEICULO_JA_POSSUI_ENTRADA_EM_ANDAMENTO, EnumDomains.REGISTRO_ENTRADA);
        }
    }

    public RegistroEntrada putRegistro(RequestPutRegistroEntrada putRegistroEntrada) {
        Optional<RegistroEntrada> entradaOPT = REGISTRO_ENTRADA_REPOSITORY.findById(putRegistroEntrada.getIdRegistro());

        if (entradaOPT.isEmpty()){
            throw new DataNotFoundException(RegistroEntradaExceptionMessages.REGISTRO_ENTRADA_NAO_ENCONTRADO, EnumDomains.ORDEM_DE_SERVICO);
        }

        RegistroEntrada entrada = entradaOPT.get();

        entrada.setResponsavel(putRegistroEntrada.getResponsavel());

        return REGISTRO_ENTRADA_REPOSITORY.save(entrada);
    }
}
