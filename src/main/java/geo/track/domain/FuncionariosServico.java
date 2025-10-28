package geo.track.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto utilizado para armazenar informações dos funcionarios que estão responsável por um determinado serviço") // 1. Descrição do objeto
public class FuncionariosServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id da lista dos funcionarios que estão trabalhando em um determinado serviço", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idFuncionariosServico;

    @ManyToOne
    @JoinColumn(name = "fk_servico")
    @Schema(description = "FK do servico ")
    private ItensServicos fkServicoFunc;

    @ManyToOne
    @JoinColumn(name = "fk_funcionario")
    @Schema(description = "FK do funcionario ")
    private Funcionarios fkFuncionarioLista;
}
