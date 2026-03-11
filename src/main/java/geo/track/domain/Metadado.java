package geo.track.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "metadados")
@Getter
@Setter
public class Metadado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metadado")
    private Integer id;

    private String chave;

    private String valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_arquivo", nullable = false)
    private Arquivo arquivo;

    // Getters e Setters
}