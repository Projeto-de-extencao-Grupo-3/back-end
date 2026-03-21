package geo.track.jornada.service.listagem;

import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.response.listagem.ResponsePainelControle;
import geo.track.mapper.PainelControleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ListagemPainelControleStrategy implements ListagemJornadaStrategy<ListagemJornadaParams> {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public ListagemJornadaResponse execute(ListagemJornadaParams request) {
        List<StatusVeiculo> status = Arrays.stream(StatusVeiculo.values()).toList();
        var response = new HashMap<StatusVeiculo, ResponsePainelControle>();

        status.forEach((statusVeiculo) -> {
            List<OrdemDeServico> ordens = ORDEM_SERVICO_REPOSITORY.findByStatus(statusVeiculo);
            ResponsePainelControle responsePainelControle = PainelControleMapper.toResponse(ordens);
            response.put(statusVeiculo, responsePainelControle);
        });

        return ListagemJornadaResponse.builder().listagemPainelControle(response).build();
    }
}
