package geo.track.domain;

import geo.track.enums.servico.TipoServico;
import io.swagger.v3.oas.annotations.media.Schema; // Import adicionado
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank; // Import adicionado
import jakarta.validation.constraints.NotNull; // Import adicionado
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa um tipo de serviço oferecido pela oficina")
@Table(name = "servicos")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do serviço", example = "1")
    private Integer idServico;

    @NotBlank // Assumindo que este campo é obrigatório
    @Schema(description = "Categoria do serviço", example = "Mecânico", requiredMode = Schema.RequiredMode.REQUIRED)
    @Enumerated(EnumType.STRING)
    private TipoServico tipoServico;

    @NotBlank // Assumindo que este campo é obrigatório
    @Schema(description = "Nome ou título descritivo do serviço", example = "Troca de óleo e filtro", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "nome")
    private String tituloServico;

    @NotNull // Assumindo que este campo é obrigatório
    @Schema(description = "Tempo base estimado para a execução do serviço (em minutos)", example = "60", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer tempoBase;

    @Schema(description = "Indica se o serviço está ativo e disponível para novas O.S.", example = "true")
    private boolean ativo;

    @OneToMany(mappedBy = "fkServico")
    @Schema(description = "Lista de servicos associados à uma lista de produtos")
    private List<ItemServico> servicos;
}