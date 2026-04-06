package geo.track.external;

import geo.track.external.service.PlateService;
import geo.track.gestao.entity.Veiculo;
import geo.track.dto.plate.PlateResponse;
import geo.track.gestao.util.VeiculoMapper;
import geo.track.gestao.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/plates") // Recomendado definir um path base
@RequiredArgsConstructor
public class PlateController {

    private final PlateService PLATE_SERVICE;
    private final VeiculoService VEICULO_SERVICE;

    @PostMapping()
    public ResponseEntity<?> recognizeAndFindVeiculo(@RequestParam("file") MultipartFile file) {

        PlateResponse response = PLATE_SERVICE.reconhecerPlaca(file);
        if (response == null || !"success".equals(response.getStatus()) || response.getData() == null) {
            return ResponseEntity.status(422).body("Não foi possível identificar a placa na imagem.");
        }

        String placaDetectada = response.getData().getPlate();

        List<Veiculo> veic = VEICULO_SERVICE.findVeiculoByPlaca(placaDetectada);

        if (veic.isEmpty()) {
            return ResponseEntity.status(404).body("Placa " + placaDetectada + " reconhecida, mas veículo não encontrado no sistema.");
        }

        return ResponseEntity.ok(VeiculoMapper.toResponse(veic));
    }
}