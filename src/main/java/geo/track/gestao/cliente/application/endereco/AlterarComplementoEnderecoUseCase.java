package geo.track.gestao.cliente.application.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;

public interface AlterarComplementoEnderecoUseCase {
    Endereco execute(Integer idEndereco, String complemento);
}

