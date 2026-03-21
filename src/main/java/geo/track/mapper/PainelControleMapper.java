package geo.track.mapper;

import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.response.listagem.ResponsePainelControle;
import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.util.OrdemDeServicoMapper;

import java.util.HashMap;
import java.util.List;

public class PainelControleMapper {
    public static HashMap<StatusVeiculo, ResponsePainelControle> toResponseList(List<List<OrdemDeServico>> matrizOrdens) {
        HashMap<StatusVeiculo, ResponsePainelControle> response = new HashMap<>();

        for (int i = 0; i < matrizOrdens.size(); i++) {
            response.put(StatusVeiculo.values()[i], toResponse(matrizOrdens.get(i)));
        }

        return response;
    }

    public static ResponsePainelControle toResponse(List<OrdemDeServico> ordens) {
        return new ResponsePainelControle(ordens.size(), ordens.stream().map(OrdemDeServicoMapper::toCard).toList());
    }
}
