package geo.track.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idFuncionario")
public class Funcionarios {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idFuncionario;
    private String nome;
    private String cargo;
    private String especialidade;
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "fk_oficina")
    private Oficinas fkOficina;
}
