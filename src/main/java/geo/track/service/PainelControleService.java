package geo.track.service;

import geo.track.domain.OrdemDeServico;
import geo.track.dto.painelControle.response.ResponsePainelControle;
import geo.track.enums.os.StatusVeiculo;
import geo.track.mapper.OrdemDeServicoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PainelControleService {
    private final OrdemDeServicoService ordemService;

    public List<List<OrdemDeServico>> findOrdensPorStatus(Integer idOficina) {
        List<List<OrdemDeServico>> listaOrdensPorStatus = new ArrayList<>();

        for(int i = 0; i < StatusVeiculo.values().length; i++) {
            if (StatusVeiculo.values()[i].equals(StatusVeiculo.FINALIZADO)) {
                listaOrdensPorStatus.add(ordemService.findOrdemByStatusUltimos30Dias(idOficina));
            } else {
                listaOrdensPorStatus.add(ordemService.findOrdemByStatus(StatusVeiculo.values()[i], idOficina));
            }
        }

        return listaOrdensPorStatus;
    }
}
