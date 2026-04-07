package geo.track.gestao.service.endereco;

import geo.track.gestao.entity.Endereco;
import geo.track.dto.enderecos.request.RequestPatchComplemento;

public interface AlterarComplementoEnderecoUseCase {
    Endereco execute(Integer idEndereco, String complemento);
}

