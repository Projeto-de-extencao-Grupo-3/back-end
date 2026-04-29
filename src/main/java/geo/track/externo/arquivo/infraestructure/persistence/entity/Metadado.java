package geo.track.externo.arquivo.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "metadados")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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