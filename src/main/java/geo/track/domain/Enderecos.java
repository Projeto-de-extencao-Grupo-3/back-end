package geo.track.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enderecos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer idEndereco;
    @NotBlank
    private String cep;
    @NotBlank
    private String logradouro;
    @NotNull
    private Integer numero;
    @NotBlank
    private String complemento;
    @NotBlank
    private String bairro;
    @NotBlank
    private String cidade;
    @NotBlank
    private String estado;

    public Enderecos(String logradouro, String bairro, String cidade, String estado) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }
}
