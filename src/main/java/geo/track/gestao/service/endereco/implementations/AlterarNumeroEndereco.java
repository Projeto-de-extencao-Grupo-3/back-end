package geo.track.gestao.service.endereco.implementations;

import geo.track.gestao.entity.Endereco;
import geo.track.gestao.entity.repository.EnderecoRepository;
import geo.track.gestao.service.EnderecoService;
import geo.track.gestao.service.endereco.AlterarNumeroEnderecoUseCase;
import geo.track.dto.enderecos.request.RequestPatchNumero;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.EnderecoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

