package geo.track.externo.viacep.request;

import lombok.Data;

@Data
public class RequestViacep {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private Boolean erro;
}
