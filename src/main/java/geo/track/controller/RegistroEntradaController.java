package geo.track.controller;

import geo.track.controller.swagger.RegistroEntradaSwagger;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.dto.registroEntrada.request.RequestPostEntrada;
import geo.track.dto.registroEntrada.request.RequestPostEntradaAgendada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.dto.registroEntrada.response.RegistroEntradaCriacaoResponse;
import geo.track.jornada.response.entrada.RegistroEntradaResponse;
import geo.track.mapper.RegistroEntradaMapper;
import geo.track.service.RegistroEntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrada")
@RequiredArgsConstructor
public class RegistroEntradaController implements RegistroEntradaSwagger {
    private final RegistroEntradaService REGISTRO_ENTRADA_SERVICE;

    @Override
    @PostMapping("/agendamento")
    public ResponseEntity<RegistroEntradaResponse> realizarAgendamentoVeiculo(@RequestBody @Valid RequestPostEntradaAgendada registroDTO) {
        RegistroEntrada registro = REGISTRO_ENTRADA_SERVICE.realizarAgendamentoVeiculo(registroDTO);
        return ResponseEntity.status(201).body(RegistroEntradaMapper.toResponse(registro));
    }

    @Override
    @PostMapping()
    public ResponseEntity<RegistroEntradaCriacaoResponse> realizarEntradaVeiculo(@RequestBody @Valid RequestPostEntrada registroDTO) {
        RegistroEntrada registro = REGISTRO_ENTRADA_SERVICE.realizarEntradaVeiculo(registroDTO);
        return ResponseEntity.status(201).body(RegistroEntradaMapper.toResponsePost(registro));
    }

    @Override
    @PutMapping("/atualizar")
    public ResponseEntity<RegistroEntradaResponse> atualizarEntradaVeiculoAgendado(@RequestBody @Valid RequestPutRegistroEntrada registroDTO) {
        RegistroEntrada registro = REGISTRO_ENTRADA_SERVICE.atualizarEntradaVeiculoAgendado(registroDTO);
        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(registro));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RegistroEntradaResponse>> findRegistro() {
        List<RegistroEntrada> registro = REGISTRO_ENTRADA_SERVICE.listarEntradas();
        if (registro.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(registro));
    }

    @Override
    @GetMapping("/{idRegistro}")
    public ResponseEntity<RegistroEntradaResponse> findRegistroById(@PathVariable Integer idRegistro) {
        RegistroEntrada registro = REGISTRO_ENTRADA_SERVICE.buscarEntradaPorId(idRegistro);
        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(registro));
    }

    public ResponseEntity<Void> deleteRegistro(@PathVariable Integer idRegistro) {
        REGISTRO_ENTRADA_SERVICE.deletarEntrada(idRegistro);
        return ResponseEntity.status(204).build();
    }
}