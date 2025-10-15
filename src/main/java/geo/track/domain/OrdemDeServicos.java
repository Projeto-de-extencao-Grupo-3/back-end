package geo.track.domain;

import geo.track.enums.os.StatusVeiculo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdemDeServicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrdemServico;
    @NotNull
    private Double valorTotal;
    private LocalDate dtSaidaPrevista;
    private LocalDate dtSaidaEfetiva;
    @NotNull
    private StatusVeiculo status;
    private Boolean seguradora;
    private Boolean nfRealizada;
    private Boolean pagtRealizado;
    private Integer fkEntrada;

}
