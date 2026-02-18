package geo.track.mapper;

import geo.track.domain.OrdemDeServico;
import geo.track.dto.painelControle.response.ResponsePainelControle;
import geo.track.enums.os.StatusVeiculo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class PainelControleMapper {
    public static HashMap<StatusVeiculo, ResponsePainelControle> toResponseList(List<List<OrdemDeServico>> matrixOrdens) {
        HashMap<StatusVeiculo, ResponsePainelControle> response = new HashMap<>();

        for(int i = 0; i < matrixOrdens.size(); i++) {
            response.put(StatusVeiculo.values()[i], toResponse(matrixOrdens.get(i)));
        }

        return response;
    }

    public static ResponsePainelControle toResponse(List<OrdemDeServico> ordens) {
        return new ResponsePainelControle(ordens.size(), OrdemDeServicoMapper.toResponse(ordens));
    }
}
