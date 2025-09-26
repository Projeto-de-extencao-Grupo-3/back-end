package geo.track.dto.viacep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseViacep {
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
}
