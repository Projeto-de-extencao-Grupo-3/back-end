package geo.track.gestao.cliente.application.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPutEndereco;

public interface AtualizarEnderecoUseCase {
    Endereco execute(Integer idCliente, Integer idEndereco, RequestPutEndereco request);
}

