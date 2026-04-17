package geo.track.jornada.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemEntrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItemEntrada;
    private String nomeItem;
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "fk_registro_entrada")
    private RegistroEntrada fkRegistroEntrada;
}
