package geo.track.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}
