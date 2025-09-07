package geo.track.request.viacep;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViacepDTO {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private Boolean erro;
}
