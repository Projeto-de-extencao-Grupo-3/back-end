package geo.track.gestao.service.endereco.implementations;

import geo.track.gestao.entity.Endereco;
import geo.track.gestao.entity.repository.EnderecoRepository;
import geo.track.gestao.service.EnderecoService;
import geo.track.gestao.service.endereco.AtualizarEnderecoUseCase;
import geo.track.dto.enderecos.request.RequestPutEndereco;
import geo.track.gestao.util.EnderecoMapper;
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
    private final EnderecoService ENDERECO_SERVICE;
    private final Log log;

    public Endereco execute(RequestPutEndereco body) {
        log.info("Atualizacao completa solicitada para o endereco ID: {}", body.getIdEndereco());
        Endereco endereco = ENDERECO_SERVICE.buscarEnderecoPorId(body.getIdEndereco());

        Endereco enderecoAtualizado = EnderecoMapper.toEntityUpdate(body, endereco);

        ENDERECO_REPOSITORY.save(enderecoAtualizado);

        log.info("Endereco ID {} atualizado completamente com sucesso", endereco.getIdEndereco());
        return endereco;
    }
}

