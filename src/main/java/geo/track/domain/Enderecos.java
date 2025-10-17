package geo.track.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
@Schema(description = "Objeto utilizado para enviar ou receber") // 1. Descrição do objeto
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idEndereco")
public class Enderecos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Schema(description = "ID do Endereço", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) // 2. Detalhes do campo
    private Integer idEndereco;
    @NotBlank
    @Schema(description = "CEP do Endereço", example = "01414001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cep;

    @NotBlank
    @Schema(description = "Logradouro do Endereço", example = "Rua Haddock Lobo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String logradouro;

    @NotNull
    @Schema(description = "Número do Endereço", example = "595", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer numero;

    @NotBlank
    @Schema(description = "Complemento do Endereço", example = "Melhor faculdade de Tecnologia do Brasil", requiredMode = Schema.RequiredMode.REQUIRED)
    private String complemento;

    @NotBlank
    @Schema(description = "Bairro do Endereço", example = "Cerqueira César", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bairro;

    @NotBlank
    @Schema(description = "Cidade do Endereço", example = "São Paulo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cidade;

    @NotBlank
    @Schema(description = "Estado do Endereço", example = "São Paulo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String estado;

    @OneToOne(mappedBy = "endereco")
    private Clientes cliente;
}
