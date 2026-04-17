package geo.track.gestao.cliente.application.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;

public interface CriarEnderecoVazioUseCase {
    Endereco execute();
}

