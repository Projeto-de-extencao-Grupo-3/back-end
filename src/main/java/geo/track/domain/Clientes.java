package geo.track.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema; // Import adicionado
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa a entidade Cliente no sistema") // Adicionado
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idCliente")
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do Cliente", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private Integer idCliente;

    @NotBlank
    @Schema(description = "Nome completo do cliente", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String nome;

    @NotBlank
    @CPF
    @Schema(description = "CPF (11 dígitos) ou CNPJ (14 dígitos) do cliente", example = "12345678909", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String cpfCnpj;

    @NotBlank
    @Size(min = 10, max = 11)
    @Schema(description = "Número de telefone do cliente (com DDD)", example = "11987654321", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String telefone;

    @NotBlank
    @Schema(description = "Endereço de e-mail do cliente", example = "joao.silva@email.com", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String email;

    @ManyToOne
    @JoinColumn(name = "fk_oficina")
    @Schema(description = "Oficina associada a este cliente") // Adicionado
    private Oficinas fkOficina;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_endereco")
    @Schema(description = "Endereço associado a este cliente") // Adicionado
    private Enderecos fkEndereco;

    @OneToMany(mappedBy = "fkCliente", cascade = CascadeType.ALL)
    @Schema(description = "Lista de veículos pertencentes a este cliente") // Adicionado
    private List<Veiculos> veiculos;
}