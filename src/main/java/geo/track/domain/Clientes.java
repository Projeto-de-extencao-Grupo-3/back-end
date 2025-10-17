package geo.track.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idCliente")
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @NotBlank
    private String nome;

    @NotBlank
    @CPF
    private String cpfCnpj;

    @NotBlank
    @Size(min = 10, max = 11)
    private String telefone;

    @NotBlank
    private String email;

    @ManyToOne
    @JoinColumn(name = "fk_oficina")
    private Oficinas fkOficina;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_endereco")
    private Enderecos fkEndereco;

    @OneToMany(mappedBy = "fkCliente", cascade = CascadeType.ALL)
    private List<Veiculos> veiculos;
}
