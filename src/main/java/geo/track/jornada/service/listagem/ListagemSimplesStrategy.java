package geo.track.jornada.service.listagem;

import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.request.ListagemJornadaParams;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.util.OrdemDeServicoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListagemSimplesStrategy implements ListagemJornadaStrategy<ListagemJornadaParams> {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public ListagemJornadaResponse execute(ListagemJornadaParams request) {
        List<OrdemDeServico> ordens = ORDEM_SERVICO_REPOSITORY.findAll();

        var response = ordens.stream().map(OrdemDeServicoMapper::toResponse).toList();

        return ListagemJornadaResponse.builder().listagemSimples(response).build();
    }
}
