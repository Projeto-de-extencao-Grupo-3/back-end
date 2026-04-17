package geo.track.jornada.infraestructure.mapper;

import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.response.listagem.ResponsePainelControle;

import java.util.HashMap;
import java.util.List;

public class PainelControleMapper {
    public static HashMap<Status, ResponsePainelControle> toResponseList(List<List<OrdemDeServico>> matrizOrdens) {
        HashMap<Status, ResponsePainelControle> response = new HashMap<>();

        for (int i = 0; i < matrizOrdens.size(); i++) {
            response.put(Status.values()[i], toResponse(matrizOrdens.get(i)));
        }

        return response;
    }

    public static ResponsePainelControle toResponse(List<OrdemDeServico> ordens) {
        return new ResponsePainelControle(ordens.size(), ordens.stream().map(OrdemDeServicoMapper::toCard).toList());
    }
}
