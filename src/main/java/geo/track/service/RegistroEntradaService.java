package geo.track.service;

import geo.track.domain.RegistroEntrada;
import geo.track.domain.Veiculos;
import geo.track.dto.registroEntrada.request.*;
import geo.track.dto.registroEntrada.request.RequestPostEntradaAgendada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.ForbiddenException;
import geo.track.port.RegistroEntradaPort;
import geo.track.repository.RegistroEntradaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistroEntradaService implements RegistroEntradaPort {
    private final RegistroEntradaRepository REGISTRO_REPOSITORY;
    private final VeiculosService VEICULO_SERVICE;

    public RegistroEntrada realizarAgendamentoVeiculo(RequestPostEntradaAgendada dto) {
        RegistroEntrada novoAgendamento = this.postRegistro(dto);
        return novoAgendamento;
    }

    public RegistroEntrada realizarEntradaVeiculo(RequestPostEntrada dto) {
        RegistroEntrada novaEntrada = this.postRegistro(dto);
        return novaEntrada;
    }

    public RegistroEntrada atualizarEntradaVeiculoAgendado(RequestPutRegistroEntrada dto) {
        RegistroEntrada entradaRealizada = this.putRegistro(dto);
        return entradaRealizada;
    }

    private RegistroEntrada postRegistro(RequestPostEntradaAgendada registroDTO){
        RegistroEntrada registro = new RegistroEntrada();
        Veiculos veiculo = VEICULO_SERVICE.findVeiculoById(registroDTO.getFkVeiculo());

        registro.setIdRegistroEntrada(null);
        registro.setDataEntradaPrevista(registroDTO.getDtEntradaPrevista());
        registro.setFkVeiculo(veiculo);

        return REGISTRO_REPOSITORY.save(registro);
    }

    public RegistroEntrada postRegistro(RequestPostEntrada body){
        Veiculos veiculo = VEICULO_SERVICE.findVeiculoById(body.idVeiculo());

        RegistroEntrada registro = new RegistroEntrada();
        registro.setDataEntradaEfetiva(body.dataEntradaEfetiva());
        registro.setDataEntradaPrevista(body.dataEntradaEfetiva()); // Porque o carro não era previsto nenhuma nada, então define a data da entrada efetiva
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

        return REGISTRO_REPOSITORY.save(registro);
    }

    public List<RegistroEntrada> findRegistros(){
        return REGISTRO_REPOSITORY.findAll();
    }

    public RegistroEntrada findRegistroById(Integer idRegistro){
        Optional<RegistroEntrada> registro = REGISTRO_REPOSITORY.findById(idRegistro);

        if (registro.isEmpty()){
            throw new DataNotFoundException("Registro de Entrada não encontrado", "Registro de Entrada");
        }

        return registro.get();
    }

    public RegistroEntrada putRegistro(RequestPutRegistroEntrada registroDTO) {
        // Busca o registro ou lança a exceção diretamente (Clean Code)
        RegistroEntrada registro = REGISTRO_REPOSITORY.findById(registroDTO.getIdRegistro())
                .orElseThrow(() -> new DataNotFoundException("Não existe um registro de entrada com esse ID", "Registro de Entrada"));

        // Verifica cada campo antes de atualizar
        if (registroDTO.getDtEntradaEfetiva() != null) {
            registro.setDataEntradaEfetiva(registroDTO.getDtEntradaEfetiva());
        }

        if (registroDTO.getResponsavel() != null) {
            registro.setResponsavel(registroDTO.getResponsavel());
        }

        if (registroDTO.getCpf() != null) {
            registro.setCpf(registroDTO.getCpf());
        }

        if (registroDTO.getExtintor() != null) {
            registro.setExtintor(registroDTO.getExtintor());
        }

        if (registroDTO.getMacaco() != null) {
            registro.setMacaco(registroDTO.getMacaco());
        }

        if (registroDTO.getChaveRoda() != null) {
            registro.setChaveRoda(registroDTO.getChaveRoda());
        }

        if (registroDTO.getGeladeira() != null) {
            registro.setGeladeira(registroDTO.getGeladeira());
        }

        if (registroDTO.getMonitor() != null) {
            registro.setMonitor(registroDTO.getMonitor());
        }

        if (registroDTO.getEstepe() != null) {
            registro.setEstepe(registroDTO.getEstepe());
        }

        if (registroDTO.getSomDvd() != null) {
            registro.setSomDvd(registroDTO.getSomDvd());
        }

        if (registroDTO.getCaixaFerramentas() != null) {
            registro.setCaixaFerramentas(registroDTO.getCaixaFerramentas());
        }

        if (registroDTO.getObservacoes() != null) {
//            registro.setObservacoes(registroDTO.getObservacoes());
        }

        return REGISTRO_REPOSITORY.save(registro);
    }

    public void deletarRegistro(Integer idRegistro){
        if (!REGISTRO_REPOSITORY.existsById(idRegistro)){
            throw new DataNotFoundException("Registro de Entrada não encontrado", "Registro de Entrada");
        }

        REGISTRO_REPOSITORY.deleteById(idRegistro);
    }
}
