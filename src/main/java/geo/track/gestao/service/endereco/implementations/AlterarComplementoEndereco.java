package geo.track.gestao.service.endereco.implementations;

import geo.track.gestao.entity.Endereco;
import geo.track.gestao.entity.repository.EnderecoRepository;
import geo.track.gestao.service.endereco.AlterarComplementoEnderecoUseCase;
import geo.track.dto.enderecos.request.RequestPatchComplemento;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.EnderecoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlterarComplementoEndereco implements AlterarComplementoEnderecoUseCase {
    private final EnderecoRepository ENDERECO_REPOSITORY;
    private final Log log;

    public Endereco execute(RequestPatchComplemento body) {
        log.info("Atualizando complemento do endereco ID: {}", body.getIdEndereco());
        Optional<Endereco> enderecos = ENDERECO_REPOSITORY.findById(body.getIdEndereco());

        if (enderecos.isEmpty()) {
            log.error("Falha ao atualizar complemento: ID {} nao encontrado", body.getIdEndereco());
            throw new DataNotFoundException(EnderecoExceptionMessages.ENDERECO_NAO_ENCONTRADO, Domains.ENDERECO);
        }

        Endereco endereco = enderecos.get();
        endereco.setComplemento(body.getComplemento());

        Endereco salvo = ENDERECO_REPOSITORY.save(endereco);
        log.info("Complemento do endereco ID {} atualizado com sucesso", salvo.getIdEndereco());
        return salvo;
    }
}

