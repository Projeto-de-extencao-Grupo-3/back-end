package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.gestao.cliente.application.endereco.AlterarNumeroEnderecoUseCase;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlterarNumeroEndereco implements AlterarNumeroEnderecoUseCase {
    private final EnderecoRepository ENDERECO_REPOSITORY;
    private final EnderecoService ENDERECO_SERVICE;
    private final Log log;

    public Endereco execute(Integer idEndereco, Integer numero) {
        log.info("Atualizando numero do endereco ID: {}", idEndereco);
        Endereco endereco = ENDERECO_SERVICE.buscarEnderecoPorId(idEndereco);
        endereco.setNumero(numero);

        ENDERECO_REPOSITORY.save(endereco);

        log.info("Numero do endereco ID {} atualizado para: {}", endereco.getIdEndereco(), endereco.getNumero());
        return endereco;
    }
}

