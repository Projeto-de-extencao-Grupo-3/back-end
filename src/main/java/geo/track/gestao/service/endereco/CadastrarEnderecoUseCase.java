package geo.track.gestao.service.endereco;

import geo.track.gestao.entity.Endereco;
import geo.track.dto.enderecos.request.RequestPostEndereco;

public interface CadastrarEnderecoUseCase {
    Endereco execute(RequestPostEndereco request, Integer fkCliente);
}

