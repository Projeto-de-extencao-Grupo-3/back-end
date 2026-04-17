package geo.track.externo.placa.domain;

import geo.track.externo.placa.infraestructure.response.PlateResponse;
import geo.track.externo.placa.infraestructure.feign.RecognizePlate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PlateService {

    @Autowired
    private RecognizePlate recognizePlate;

    public PlateResponse reconhecerPlaca(MultipartFile file) {
        try {
            PlateResponse response = recognizePlate.reconhecer(file);

            if (response != null && response.getData() != null) {
                String placaOriginal = response.getData().getPlate();

                if (placaOriginal != null) {
                    String placaLimpa = placaOriginal.replace("-", "").replace(" ", "").toUpperCase();

                    response.getData().setPlate(placaLimpa);
                }
            }
            return response;

        } catch (Exception e) {
            System.err.println("Erro ao chamar gateway: " + e.getMessage());
            return null;
        }
    }
}
