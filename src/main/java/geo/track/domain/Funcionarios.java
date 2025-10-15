package geo.track.domain;

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
public class Funcionarios {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idFuncionario;
    private String nome;
    private String cargo;
    private String especialidade;
    private String telefone;

        @ManyToOne
    @JoinColumn(name = "oficina_id")
    private Oficinas oficina;
}
