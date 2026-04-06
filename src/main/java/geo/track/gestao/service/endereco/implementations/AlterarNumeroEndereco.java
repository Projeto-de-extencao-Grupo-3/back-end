package geo.track.gestao.service.endereco.implementations;

import geo.track.gestao.entity.Endereco;
import geo.track.gestao.entity.repository.EnderecoRepository;
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
    private final Log log;

    public Endereco execute(RequestPatchNumero body) {
        log.info("Atualizando numero do endereco ID: {}", body.getId());
        Optional<Endereco> enderecos = ENDERECO_REPOSITORY.findById(body.getId());

        if (enderecos.isPresent()) {
            Endereco endereco = enderecos.get();

            endereco.setNumero(body.getNumero());

            ENDERECO_REPOSITORY.save(endereco);

            log.info("Numero do endereco ID {} atualizado para: {}", endereco.getIdEndereco(), endereco.getNumero());
            return endereco;
        } else {
            log.error("Falha ao atualizar numero: ID {} nao encontrado", body.getId());
            throw new DataNotFoundException(EnderecoExceptionMessages.ENDERECO_NAO_ENCONTRADO, Domains.ENDERECO);
        }
    }
}

