package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.gestao.cliente.application.endereco.AlterarComplementoEnderecoUseCase;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlterarComplementoEndereco implements AlterarComplementoEnderecoUseCase {
    private final EnderecoRepository ENDERECO_REPOSITORY;
    private final EnderecoService ENDERECO_SERVICE;
    private final Log log;

    public Endereco execute(Integer idEndereco, String complemento) {
        log.info("Atualizando complemento do endereco ID: {}", idEndereco);
        Endereco endereco = ENDERECO_SERVICE.buscarEnderecoPorId(idEndereco);
        endereco.setComplemento(complemento);

        Endereco salvo = ENDERECO_REPOSITORY.save(endereco);
        log.info("Complemento do endereco ID {} atualizado com sucesso", salvo.getIdEndereco());
        return salvo;
    }
}

