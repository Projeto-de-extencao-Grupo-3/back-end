package geo.track.jornada.service.listagem.implementation;

import geo.track.jornada.enums.Status;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.response.listagem.ResponsePainelControle;
import geo.track.jornada.service.listagem.ListagemPainelControleUseCase;
import geo.track.jornada.util.PainelControleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ListagemPainelControleImplementation implements ListagemPainelControleUseCase {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public ListagemJornadaResponse execute(ListagemJornadaParams request) {
        List<Status> status = Arrays.stream(Status.values()).toList();
        var response = new HashMap<Status, ResponsePainelControle>();

        status.forEach((statusVeiculo) -> {
            List<OrdemDeServico> ordens = ORDEM_SERVICO_REPOSITORY.findByStatus(statusVeiculo);
            ResponsePainelControle responsePainelControle = PainelControleMapper.toResponse(ordens);
            response.put(statusVeiculo, responsePainelControle);
        });

        return ListagemJornadaResponse.builder().listagemPainelControle(response).build();
    }
}
