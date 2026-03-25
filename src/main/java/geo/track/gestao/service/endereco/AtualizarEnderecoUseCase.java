package geo.track.gestao.service.endereco;

import geo.track.gestao.entity.Endereco;
import geo.track.dto.enderecos.request.RequestPutEndereco;

public interface AtualizarEnderecoUseCase {
    Endereco execute(RequestPutEndereco request);
}

