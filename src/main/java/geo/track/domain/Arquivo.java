package geo.track.domain;

import geo.track.enums.Formato;
import geo.track.enums.StatusArquivo;
import geo.track.enums.Template;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "arquivos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_arquivo")
    private Integer idArquivo;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Formato formato;

    @Enumerated(EnumType.STRING)
    private Template template;

    private String url;

    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusArquivo status;

    private LocalDateTime dataAtualizacao;

    private Integer fkOrdemServico;

    @OneToMany(mappedBy = "arquivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Metadado> metadados;
}