package geo.track.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idFuncionário")
@Schema(description = "Representa um Funcionário do sistema.")
public class Funcionarios {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador do funcionário", example = "1")
    private Integer idFuncionario;
    @Schema(description = "Nome do funcionário", example = "José Silva")
    private String nome;
    @Schema(description = "Cargo do funcionário", example = "Chefe de produção")
    private String cargo;
    @Schema(description = "Especialidade do funcionário", example = "")
    private String especialidade;
    @Schema(description = "Telefone do funcionário", example = "4002-8922")
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "fk_oficina")
    private Oficinas fkOficina;
}