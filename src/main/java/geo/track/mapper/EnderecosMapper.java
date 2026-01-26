package geo.track.mapper;

import geo.track.domain.Endereco;
import geo.track.dto.enderecos.request.RequestPostEndereco;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnderecosMapper {
    public static final Endereco RequestToEndereco(RequestPostEndereco dto) {
        return new Endereco(null, dto.getCep(), dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCidade(), dto.getEstado(),null);
    }
}
