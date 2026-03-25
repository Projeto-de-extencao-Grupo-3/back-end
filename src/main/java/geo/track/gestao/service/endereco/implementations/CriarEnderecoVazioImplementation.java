package geo.track.gestao.service.endereco.implementations;

import geo.track.gestao.entity.Endereco;
import geo.track.gestao.entity.repository.EnderecoRepository;
import geo.track.gestao.service.endereco.CriarEnderecoVazioUseCase;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CriarEnderecoVazioImplementation implements CriarEnderecoVazioUseCase {
    private final EnderecoRepository ENDERECO_REPOSITORY;
    private final Log log;

    public Endereco execute() {
        log.info("Criando endereco vazio");
        Endereco e = new Endereco();
        e.setCep("00000000");
        e.setLogradouro("A PREENCHER");
        e.setNumero(0);
        e.setBairro("A PREENCHER");
        e.setCidade("A PREENCHER");
        e.setEstado("AA");
        e.setComplemento("-");

        return ENDERECO_REPOSITORY.save(e);
    }
}

