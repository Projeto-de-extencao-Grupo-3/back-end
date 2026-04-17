package geo.track.gestao.cliente.application.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPostEndereco;

public interface CadastrarEnderecoUseCase {
    Endereco execute(RequestPostEndereco request, Integer fkCliente);
}

