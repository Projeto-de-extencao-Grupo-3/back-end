package geo.track.gestao.cliente.infraestructure.response.endereco;

import lombok.Data;

@Data
public class EnderecoResponse {
    private Integer idEndereco;
    private String cep;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
}
