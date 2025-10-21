package geo.track.domain;

import jakarta.persistence.*;
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


    @ManyToOne
    @JoinColumn(name = "fk_veiculo")
    private Veiculos fk_veiculo;

    @OneToOne(mappedBy = "fk_entrada", cascade = CascadeType.ALL)
    private OrdemDeServicos ordemDeServicos;
}
