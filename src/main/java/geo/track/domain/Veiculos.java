package geo.track.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Veiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVeiculo;
    private String placa;
    private String marca;
    private Integer anoModelo;
    private Integer anoFabricacao;
    private String cor;

}

