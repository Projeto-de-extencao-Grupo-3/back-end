package geo.track.domain;

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
@Schema(description = "Representa um tipo de serviço oferecido pela oficina") // Adicionado
public class Servicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do serviço", example = "1") // Adicionado
    private Integer idServico;

    @NotBlank // Assumindo que este campo é obrigatório
    @Schema(description = "Categoria do serviço", example = "Mecânico", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String tipoServico;

    @NotBlank // Assumindo que este campo é obrigatório
    @Schema(description = "Nome ou título descritivo do serviço", example = "Troca de óleo e filtro", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private String tituloServico;

    @NotNull // Assumindo que este campo é obrigatório
    @Schema(description = "Tempo base estimado para a execução do serviço (em minutos)", example = "60", requiredMode = Schema.RequiredMode.REQUIRED) // Adicionado
    private Integer tempoBase;

    @Schema(description = "Indica se o serviço está ativo e disponível para novas O.S.", example = "true") // Adicionado
    private boolean ativo;

    @OneToMany(mappedBy = "fkServico", cascade = CascadeType.ALL)
    @Schema(description = "Lista de servicos associados à uma lista de produtos")
    private List<ItensServicos> servicos;
}