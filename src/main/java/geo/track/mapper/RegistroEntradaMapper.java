package geo.track.mapper;

import geo.track.domain.RegistroEntrada;
import geo.track.dto.registroEntrada.response.RegistroEntradaResponse;

import java.util.List;
import java.util.stream.Collectors;

public class RegistroEntradaMapper {
    public static RegistroEntradaResponse toResponse(RegistroEntrada entity) {
        if (entity == null) {
            return null;
        }

        RegistroEntradaResponse response = new RegistroEntradaResponse();
        response.setIdRegistroEntrada(entity.getIdRegistroEntrada());
        response.setDataEntradaPrevista(entity.getDataEntradaPrevista());
        response.setDataEntradaEfetiva(entity.getDataEntradaEfetiva());
        response.setResponsavel(entity.getResponsavel());
        response.setCpf(entity.getCpf());
        response.setExtintor(entity.getExtintor());
        response.setMacaco(entity.getMacaco());
        response.setChaveRoda(entity.getChaveRoda());
        response.setGeladeira(entity.getGeladeira());
        response.setMonitor(entity.getMonitor());
        response.setEstepe(entity.getEstepe());
        response.setSomDvd(entity.getSomDvd());
        response.setCaixaFerramenta(entity.getCaixaFerramentas());

        if (entity.getFkVeiculo() != null) {
            response.setIdVeiculo(entity.getFkVeiculo().getIdVeiculo());
        }

        if (entity.getOrdemDeServicos() != null) {
            response.setIdOrdemDeServico(entity.getOrdemDeServicos().getIdOrdemServico());
        }

        return response;
    }

    public static List<RegistroEntradaResponse> toResponse(List<RegistroEntrada> entities) {
        return entities.stream()
                .map(RegistroEntradaMapper::toResponse)
                .collect(Collectors.toList());
    }
}
