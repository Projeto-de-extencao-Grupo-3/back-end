package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.gestao.cliente.application.endereco.DeletarEnderecoUseCase;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarEndereco implements DeletarEnderecoUseCase {
    private final EnderecoService ENDERECO_SERVICE;
    private final EnderecoRepository ENDERECO_REPOSITORY;

    @Override
    public void execute(Integer idCliente, Integer idEndereco) {
        Endereco endereco = ENDERECO_SERVICE.buscarEnderecoPorId(idCliente, idEndereco);
        ENDERECO_REPOSITORY.delete(endereco);
    }
}
