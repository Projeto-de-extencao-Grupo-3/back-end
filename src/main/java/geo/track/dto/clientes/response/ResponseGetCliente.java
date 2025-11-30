package geo.track.dto.clientes.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import geo.track.domain.Enderecos;
import geo.track.domain.Oficinas;
import geo.track.domain.Veiculos;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Getter
@Setter
public class ResponseGetCliente {
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

    private Integer fkOficina;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_endereco")
    private Enderecos fkEndereco;

    @JsonIgnore
    @OneToMany(mappedBy = "fkCliente")
    private List<Veiculos> veiculos;

    public ResponseGetCliente(Integer idCliente, String nome, String cpfCnpj, String telefone, String email, Integer fkOficina, Enderecos fkEndereco, List<Veiculos> veiculos) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.email = email;
        this.fkOficina = fkOficina;
        this.fkEndereco = fkEndereco;
        this.veiculos = veiculos;
    }
}
