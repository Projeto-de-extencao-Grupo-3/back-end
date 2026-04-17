package geo.track.gestao.cliente.infraestructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import io.swagger.v3.oas.annotations.media.Schema; // Import adicionado
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa a entidade Cliente no sistema") // Adicionado
@Table(name = "clientes")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idCliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do Cliente", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    // Adicionado
    private Integer idCliente;

    @Schema(description = "Nome completo do cliente", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
    // Adicionado
    private String nome;

    @Schema(description = "CPF (11 dígitos) ou CNPJ (14 dígitos) do cliente", example = "12345678909", requiredMode = Schema.RequiredMode.REQUIRED)
    // Adicionado
    private String cpfCnpj;

    @Schema(description = "Inscrição estadual do cliente (opcional, apenas para pessoas jurídicas)", example = "123456789", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String inscricaoEstadual;


    @Schema(description = "Tipo de cliente", example = "PESSOA_FISICA", requiredMode = Schema.RequiredMode.REQUIRED)
    // Adicionado
    private String tipoCliente;

    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "fk_oficina")
    @Schema(description = "Oficina associada a este cliente") // Adicionado
    private Oficina fkOficina;

    @OneToMany(mappedBy = "fkCliente")
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "fkCliente")
    private List<Contato> contatos;

    @OneToMany(mappedBy = "fkCliente")
    @Schema(description = "Lista de veículos pertencentes a este cliente") // Adicionado
    private List<Veiculo> veiculos;

    public Cliente(Integer idCliente, String nome, String cpfCnpj, String inscricaoEstadual, TipoCliente tipoCliente, Oficina fkOficina) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.tipoCliente = tipoCliente.name();
        this.fkOficina = fkOficina;
    }
}