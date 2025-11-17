package geo.track.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idOficina")
public class Oficinas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da oficina", example = "1")
    private Integer idOficina;

    @Schema(description = "Razão social da oficina", example = "Auto Center São Lucas LTDA")
    private String razaoSocial;

    @Schema(description = "CNPJ da oficina")
    private String cnpj;

    @Schema(description = "Email de contato da oficina", example = "contato@autocenter.com")
    private String email;

    @Schema(description = "Data e hora de criação do cadastro")
    private LocalDateTime dtCriacao;

    @Schema(description = "Status atual da oficina", example = "ATIVO")
    private String status;

    @Schema(description = "Senha de acesso ao sistema (armazenada de forma segura)", example = "••••••••")
    private String senha;

    @OneToMany(mappedBy = "fkOficina", cascade = CascadeType.ALL)
    @Schema(description = "Lista de funcionários associados à oficina")
    private List<Funcionarios> funcionarios;

    @OneToMany(mappedBy = "fkOficina", cascade = CascadeType.ALL)
    @Schema(description = "Lista de clientes associados à oficina")
    private List<Clientes> clientes;
}
