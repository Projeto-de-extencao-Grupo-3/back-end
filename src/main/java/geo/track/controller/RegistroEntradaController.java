package geo.track.controller;

import geo.track.controller.swagger.RegistroEntradaSwagger;
import geo.track.domain.RegistroEntrada;
import geo.track.dto.registroEntrada.request.RequestPostEntrada;
import geo.track.dto.registroEntrada.request.RequestPostEntradaAgendada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.dto.registroEntrada.response.RegistroEntradaResponse;
import geo.track.mapper.RegistroEntradaMapper;
import geo.track.port.RegistroEntradaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrada")
@RequiredArgsConstructor
public class RegistroEntradaController implements RegistroEntradaSwagger {
    private final RegistroEntradaPort REGISTRO_ENTRADA_SERVICE;

    @Override
    @PostMapping("/agendamento")
    public ResponseEntity<RegistroEntradaResponse> realizarAgendamentoVeiculo(@RequestBody RequestPostEntradaAgendada registroDTO) {
        RegistroEntrada registro = REGISTRO_ENTRADA_SERVICE.realizarAgendamentoVeiculo(registroDTO);
        return ResponseEntity.status(201).body(RegistroEntradaMapper.toResponse(registro));
    }

    @Override
    @PostMapping("/entrada")
    public ResponseEntity<RegistroEntradaResponse> realizarEntradaVeiculo(@RequestBody RequestPostEntrada registroDTO) {
        RegistroEntrada registro = REGISTRO_ENTRADA_SERVICE.realizarEntradaVeiculo(registroDTO);
        return ResponseEntity.status(201).body(RegistroEntradaMapper.toResponse(registro));
    }

    @Override
    @PutMapping("/atualizar")
    public ResponseEntity<RegistroEntradaResponse> atualizarEntradaVeiculoAgendado(@RequestBody RequestPutRegistroEntrada registroDTO) {
        RegistroEntrada registro = REGISTRO_ENTRADA_SERVICE.atualizarEntradaVeiculoAgendado(registroDTO);
        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(registro));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RegistroEntradaResponse>> findRegistro() {
        List<RegistroEntrada> registro = REGISTRO_ENTRADA_SERVICE.findRegistros();
        if (registro.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(registro));
    }

    @Override
    @GetMapping("/{idRegistro}")
    public ResponseEntity<RegistroEntradaResponse> findRegistroById(@PathVariable Integer idRegistro) {
        RegistroEntrada registro = REGISTRO_ENTRADA_SERVICE.findRegistroById(idRegistro);
        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(registro));
    }

    public ResponseEntity<Void> deleteRegistro(@PathVariable Integer idRegistro) {
        REGISTRO_ENTRADA_SERVICE.deletarRegistro(idRegistro);
        return ResponseEntity.status(204).build();
    }
}