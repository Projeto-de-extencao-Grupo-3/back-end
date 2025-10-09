package geo.track.controller;

import geo.track.domain.RegistroEntrada;
import geo.track.dto.registroEntrada.request.PostRegistroEntrada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.service.RegistroEntradaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class RegistroEntradaController {
    private final RegistroEntradaService registroService;

    public RegistroEntradaController(RegistroEntradaService registroService) {
        this.registroService = registroService;
    }

    @PostMapping
    public ResponseEntity<RegistroEntrada> postRegistro(PostRegistroEntrada registroDTO){
        RegistroEntrada registro = registroService.postRegistro(registroDTO);
        return ResponseEntity.status(201).body(registro);
    }

    @GetMapping
    public ResponseEntity<List<RegistroEntrada>> findRegistro(){
        List<RegistroEntrada> registro = registroService.findRegistro();
       if (registro.isEmpty()){
           return ResponseEntity.status(204).build();
       }

        return ResponseEntity.status(200).body(registro);
    }


    @GetMapping("/{idRegistro}")
    public ResponseEntity<RegistroEntrada> findRegistroById(Integer idRegistro){
        RegistroEntrada registro = registroService.findRegistroById(idRegistro);
        return ResponseEntity.status(200).body(registro);
    }

    @PutMapping
    public ResponseEntity<RegistroEntrada> putRegistro(RequestPutRegistroEntrada registroDTO){
        RegistroEntrada registro = registroService.putRegistro(registroDTO);
        return ResponseEntity.status(200).body(registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistro(@PathVariable Integer idRegistro){
        registroService.deleteRegistro(idRegistro);
        return ResponseEntity.status(204).build();
    }

}
