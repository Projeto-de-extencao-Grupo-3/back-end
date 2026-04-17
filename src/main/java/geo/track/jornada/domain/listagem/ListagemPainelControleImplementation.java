package geo.track.jornada.domain.listagem;

import geo.track.jornada.application.listagem.ListagemPainelControleUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.request.ListagemJornadaParams;
import geo.track.jornada.infraestructure.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.infraestructure.response.listagem.ResponsePainelControle;
import geo.track.jornada.infraestructure.mapper.PainelControleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
            LocalDate dataLimite = LocalDate.now().minusDays(30);

            List<OrdemDeServico> ordens = ORDEM_SERVICO_REPOSITORY.findByStatus(statusVeiculo);
            if (statusVeiculo.equals(Status.FINALIZADO)) ordens = ORDEM_SERVICO_REPOSITORY.findByStatusUltimos30Dias(dataLimite);

            ResponsePainelControle responsePainelControle = PainelControleMapper.toResponse(ordens);
            response.put(statusVeiculo, responsePainelControle);
        });

        return ListagemJornadaResponse.builder().listagemPainelControle(response).build();
    }
}
