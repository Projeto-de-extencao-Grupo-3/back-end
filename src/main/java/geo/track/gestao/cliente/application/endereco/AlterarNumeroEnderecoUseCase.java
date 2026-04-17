package geo.track.gestao.cliente.application.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;

public interface AlterarNumeroEnderecoUseCase {
    Endereco execute(Integer idEndereco, Integer numero);
}

