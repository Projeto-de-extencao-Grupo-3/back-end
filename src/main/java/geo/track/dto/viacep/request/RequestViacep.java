package geo.track.dto.viacep.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RequestViacep {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private Boolean erro;
}
