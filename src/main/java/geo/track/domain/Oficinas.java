package geo.track.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Oficinas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;

    private String razaoSocial;

    private String cnpj;

    private String email;

    private LocalDateTime dtCriacao;

    private String status;

    private String senha;

    @OneToMany(mappedBy = "oficina")
    private List<Funcionarios> funcionario;
}
