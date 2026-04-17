package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.gestao.cliente.application.endereco.AtualizarEnderecoUseCase;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPutEndereco;
import geo.track.gestao.cliente.infraestructure.EnderecoMapper;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

