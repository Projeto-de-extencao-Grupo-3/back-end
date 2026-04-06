package geo.track.gestao.service.endereco.implementations;

import geo.track.gestao.entity.Endereco;
import geo.track.gestao.entity.repository.EnderecoRepository;
import geo.track.gestao.service.endereco.AtualizarEnderecoUseCase;
import geo.track.dto.enderecos.request.RequestPutEndereco;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.EnderecoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AtualizarEndereco implements AtualizarEnderecoUseCase {
    private final EnderecoRepository ENDERECO_REPOSITORY;
    private final Log log;

    public Endereco execute(RequestPutEndereco body) {
        log.info("Atualizacao completa solicitada para o endereco ID: {}", body.getIdEndereco());
        Optional<Endereco> enderecos = ENDERECO_REPOSITORY.findById(body.getIdEndereco());

        if (enderecos.isPresent()) {
            Endereco endereco = enderecos.get();

            endereco.setCep(body.getCep());
            endereco.setLogradouro(body.getLogradouro());
            endereco.setNumero(body.getNumero());
            endereco.setComplemento(body.getComplemento());
            endereco.setBairro(body.getBairro());
            endereco.setCidade(body.getCidade());
            endereco.setEstado(body.getEstado());

            ENDERECO_REPOSITORY.save(endereco);

            log.info("Endereco ID {} atualizado completamente com sucesso", endereco.getIdEndereco());
            return endereco;
        } else {
            log.error("Endereco ID {} nao encontrado para atualizacao", body.getIdEndereco());
            throw new DataNotFoundException(EnderecoExceptionMessages.ENDERECO_NAO_ENCONTRADO, Domains.ENDERECO);
        }
    }
}

