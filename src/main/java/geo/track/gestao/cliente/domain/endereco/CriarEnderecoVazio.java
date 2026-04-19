package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.domain.ClienteService;
import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.gestao.cliente.application.endereco.CriarEnderecoVazioUseCase;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CriarEnderecoVazio implements CriarEnderecoVazioUseCase {
    private final EnderecoRepository ENDERECO_REPOSITORY;
    private final ClienteService CLIENTE_SERVICE;
    private final Log log;

    public Endereco execute(Integer idCliente) {

        log.info("Criando endereco vazio");
        Endereco e = new Endereco();
        e.setCep("00000000");
        e.setLogradouro("A PREENCHER");
        e.setNumero(0);
        e.setBairro("A PREENCHER");
        e.setCidade("A PREENCHER");
        e.setEstado("AA");
        e.setComplemento("-");
        e.setCorrespondencia(false);

        Cliente cliente = CLIENTE_SERVICE.buscarClientePorId(idCliente);
        e.setFkCliente(cliente);

        return ENDERECO_REPOSITORY.save(e);
    }
}

