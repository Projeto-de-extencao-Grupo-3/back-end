package geo.track.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPeca;
    @NotBlank
    private String nome;
    @NotBlank
    private String fornecedorNf;

    @NotNull
    @Positive
    private Double precoCompra;

    @NotNull
    @Positive
    private Double precoVenda;

    @NotNull
    @Positive
    private Integer quantidadeEstoque;
}

