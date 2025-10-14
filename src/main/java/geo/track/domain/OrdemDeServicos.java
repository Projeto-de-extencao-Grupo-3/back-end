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
    private Integer id_ordem_servico;
    @NotNull
    private Double valor_total;
    private LocalDate dt_saida_prevista;
    private LocalDate dt_saida_efetiva;
    @NotNull
    private StatusVeiculo status;
    private Boolean seguradora;
    private Boolean nf_realizada;
    private Boolean pagt_realizado;
    private Integer fk_entrada;

}
