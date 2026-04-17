package geo.track.gestao.cliente.infraestructure.persistence.entity.embedded;

import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contatos")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico do Contato", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idContato;

    @Schema(description = "Telefone do contato", example = "(11) 98765-4321", requiredMode = Schema.RequiredMode.REQUIRED)
    private String telefone;

    @Schema(description = "E-mail do contato", example = "email@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Nome do contato", example = "Maria Oliveira", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nomeContato;

    @Schema(description = "Departamento do contato", example = "Vendas", requiredMode = Schema.RequiredMode.REQUIRED)
    private String departamento;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente fkCliente;
}
