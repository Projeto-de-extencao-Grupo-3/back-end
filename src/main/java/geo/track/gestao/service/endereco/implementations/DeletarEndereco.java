package geo.track.gestao.service.endereco.implementations;

import geo.track.gestao.entity.repository.EnderecoRepository;
import geo.track.gestao.service.EnderecoService;
import geo.track.gestao.service.endereco.DeletarEnderecoUseCase;
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
    public void execute(Integer idEndereco) {
        var endereco = ENDERECO_SERVICE.buscarEnderecoPorId(idEndereco);

        if (endereco.getFkCliente().getEnderecos().size() <= 1) {
            throw new BadBusinessRuleException("Não é possível deletar o endereço. O cliente deve possuir pelo menos um endereço cadastrado.", Domains.ENDERECO);
        }

        ENDERECO_REPOSITORY.deleteById(idEndereco);
    }
}
