package geo.track.jornada.domain.listagem;

import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.OrdemDeServicoExceptionMessages;
import geo.track.jornada.application.listagem.BuscaSimplesUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.infraestructure.response.listagem.ListagemJornadaResponse;
import geo.track.jornada.infraestructure.mapper.OrdemDeServicoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuscaSimplesImplementation implements BuscaSimplesUseCase {
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public ListagemJornadaResponse execute(Integer id) {
        OrdemDeServico ordem = ORDEM_SERVICO_REPOSITORY.findById(id).orElseThrow(() -> new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO));

        var response = OrdemDeServicoMapper.toTelaOrdemServicoResponse(ordem);

        return ListagemJornadaResponse.builder().buscaSimples(response).build();
    }
}
