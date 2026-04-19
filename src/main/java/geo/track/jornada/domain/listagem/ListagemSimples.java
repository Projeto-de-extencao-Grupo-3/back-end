package geo.track.jornada.domain.listagem;

import geo.track.jornada.application.listagem.ListagemSimplesUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.infraestructure.request.ListagemJornadaParams;
import geo.track.jornada.infraestructure.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.infraestructure.mapper.OrdemDeServicoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListagemSimples implements ListagemSimplesUseCase {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public ListagemJornadaResponse execute(ListagemJornadaParams request) {
        List<OrdemDeServico> ordens = ORDEM_SERVICO_REPOSITORY.findAll();

        var response = ordens.stream().map(OrdemDeServicoMapper::toResponse).toList();

        return ListagemJornadaResponse.builder().listagemSimples(response).build();
    }
}
