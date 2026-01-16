package geo.track.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
@Table(name = "funcionarios_servico")
public class FuncionarioServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id da lista dos funcionarios que estão trabalhando em um determinado serviço", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idFuncionariosServico;

    @ManyToOne
    @JoinColumn(name = "fk_servico")
    @Schema(description = "FK do servico ")
    private ItemServico fkServicoFunc;

    @ManyToOne
    @JoinColumn(name = "fk_funcionario")
    @Schema(description = "FK do funcionario ")
    private Funcionario fkFuncionarioLista;
}
