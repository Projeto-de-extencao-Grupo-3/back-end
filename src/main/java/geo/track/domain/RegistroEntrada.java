package geo.track.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroEntrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegistroEntrada;
    @NotNull
    private LocalDate dataEntradaPrevista;
    private LocalDate dataEntradaEfetiva;
    private String responsavel;
    @CPF
    private String cpf;
    private Boolean extintor;
    private Boolean macaco;
    private Boolean chave_roda;
    private Integer geladeira;
    private Integer monitor;
    private Integer fkVeiculo;
}
