package geo.track.jornada.service.listagem;

import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.OrdemDeServicoExceptionMessages;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.util.OrdemDeServicoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuscaSimplesUseCase {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    public ListagemJornadaResponse execute(Integer id) {
        OrdemDeServico ordem = ORDEM_SERVICO_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO));

        var response = OrdemDeServicoMapper.toTelaOrdemServicoResponse(ordem);

        return ListagemJornadaResponse.builder().buscaSimples(response).build();
    }
}
