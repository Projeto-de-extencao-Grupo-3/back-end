package geo.track.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Veiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVeiculo;

    @NotBlank
    @Size(min = 7, max = 7)
    private String placa;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    private Integer anoModelo;

    @NotBlank
    private String cor;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Clientes fkCliente;

    @OneToMany(mappedBy = "fk_veiculo", cascade = CascadeType.ALL)
    private List<RegistroEntrada> registroEntradas;
}

