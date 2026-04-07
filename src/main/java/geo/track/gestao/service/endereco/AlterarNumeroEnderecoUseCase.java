package geo.track.gestao.service.endereco;

import geo.track.gestao.entity.Endereco;
import geo.track.dto.enderecos.request.RequestPatchNumero;

public interface AlterarNumeroEnderecoUseCase {
    Endereco execute(Integer idEndereco, Integer numero);
}

