package geo.track.mapper;

import geo.track.domain.Enderecos;
import geo.track.dto.enderecos.request.RequestPostEndereco;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnderecosMapper {
    public static final Enderecos RequestToEndereco(RequestPostEndereco dto) {
        return new Enderecos(null, dto.getCep(), dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCidade(), dto.getEstado());
    }
}
