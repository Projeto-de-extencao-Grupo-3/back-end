package geo.track.domain;

import geo.track.enums.Formato;
import geo.track.enums.Template;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "arquivos")
@Getter
@Setter
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_arquivo")
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 45)
    private Formato formato;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 45)
    private Template template;

    @Column(nullable = false)
    private String url;

    @Column(name = "data_criacao", nullable = false, length = 45)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false, length = 45)
    private LocalDateTime dataAtualizacao;

    @Column(name = "fk_ordem_servico", nullable = false)
    private Integer fkOrdemServico;

    @OneToMany(mappedBy = "arquivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Metadado> metadados;
}