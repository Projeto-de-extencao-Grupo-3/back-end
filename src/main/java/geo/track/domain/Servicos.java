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
public class Servicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idServico;
    private String tipoServico;
    private String tituloServico;
    private Integer tempoBase;
    private boolean ativo;

}
