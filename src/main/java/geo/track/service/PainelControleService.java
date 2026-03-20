package geo.track.service;

import geo.track.jornada.entity.OrdemDeServico;
import geo.track.enums.os.StatusVeiculo;
import geo.track.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PainelControleService {
    private final OrdemDeServicoService ODEM_SERVICO_SERVICE;
    private final Log log;

    public List<List<OrdemDeServico>> findOrdensPorStatus(Integer idOficina) {
        log.info("Iniciando busca de ordens de serviço agrupadas por status para a oficina ID: {}", idOficina);

        List<List<OrdemDeServico>> listaOrdensPorStatus = new ArrayList<>();

        for (int i = 0; i < StatusVeiculo.values().length; i++) {
            listaOrdensPorStatus.add(ODEM_SERVICO_SERVICE.buscarOrdemPorStatus(StatusVeiculo.values()[i]));
        }

        log.info("Busca finalizada com sucesso para a oficina ID: {}. Total de categorias processadas: {}", idOficina, listaOrdensPorStatus.size());
        return listaOrdensPorStatus;
    }
}
