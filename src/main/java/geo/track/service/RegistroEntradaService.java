package geo.track.service;

import geo.track.domain.RegistroEntrada;
import geo.track.dto.registroEntrada.request.PostRegistroEntrada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.ForbiddenException;
import geo.track.repository.RegistroEntradaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistroEntradaService {
    private final RegistroEntradaRepository registroRepository;

    public RegistroEntrada postRegistro(@Valid @RequestBody PostRegistroEntrada registroDTO){
        RegistroEntrada registro = new RegistroEntrada();

        registro.setData_entrada_prevista(registroDTO.getDtEntradaPrevista());
        registro.setFk_veiculo(registroDTO.getFkVeiculo());

        return registroRepository.save(registro);
    }

    public List<RegistroEntrada> findRegistro(){
        return registroRepository.findAll();
    }

    public RegistroEntrada findRegistroById(Integer idRegistro){
        Optional<RegistroEntrada> registro = registroRepository.findById(idRegistro);

        if (registro.isEmpty()){
            throw new DataNotFoundException("Não existe uma registro de entrada com esse ID", "Registro de Entrada");
        }

        return registro.get();
    }

    public RegistroEntrada putRegistro(RequestPutRegistroEntrada registroDTO){
        Optional<RegistroEntrada> registroOPT = registroRepository.findById(registroDTO.getIdRegistro());

        if (registroOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma registro de entrada com esse ID", "Registro de Entrada");
        }

        RegistroEntrada registro = registroOPT.get();

        registro.setData_entrada_efetiva(registroDTO.getDtEntradaEfetiva());
        registro.setResponsavel(registroDTO.getResponsavel());
        registro.setCpf(registroDTO.getCpf());
        registro.setExtintor(registroDTO.getExtintor());
        registro.setMacaco(registroDTO.getMacaco());
        registro.setChave_roda(registroDTO.getChaveRoda());
        registro.setGeladeira(registroDTO.getGeladeira());
        registro.setMonitor(registroDTO.getMonitor());

        return registroRepository.save(registro);
    }

    public void deleteRegistro(Integer idRegistro){
        Optional<RegistroEntrada> registroOPT = registroRepository.findById(idRegistro);

        if (registroOPT.isEmpty()){
            throw new DataNotFoundException("Não existe uma registro de entrada com esse ID", "Registro de Entrada");
        }

        RegistroEntrada registro = registroOPT.get();
        if (registro.getFk_veiculo() == null){
            throw new ForbiddenException("Solicitação recusada", "Registro de Entrada");

        }


    }
}
