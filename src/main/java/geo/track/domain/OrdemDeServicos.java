package geo.track.domain;

import geo.track.enums.os.StatusVeiculo;
import jakarta.persistence.*;
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


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_entrada")
    private RegistroEntrada fk_entrada;

}
