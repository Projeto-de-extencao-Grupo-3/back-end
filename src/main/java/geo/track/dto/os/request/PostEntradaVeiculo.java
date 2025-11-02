package geo.track.dto.os.request;

import geo.track.domain.OrdemDeServicos;
import geo.track.domain.Veiculos;
import geo.track.enums.os.StatusVeiculo;
import lombok.Data;

@Data
public class PostEntradaVeiculo {
    private StatusVeiculo status;
    private Double valorTotal = 0.0;
    private Integer fkEntrada;

    public static class Builder {
        private final PostEntradaVeiculo instance;

        public Builder() {
            this.instance = new PostEntradaVeiculo();
        }

        public Builder status(StatusVeiculo status) {
            instance.setStatus(status);
            return this;
        }

        public Builder valorTotal(Double valorTotal) {
            instance.setValorTotal(valorTotal);
            return this;
        }

        public Builder fkEntrada(Integer fkEntrada) {
            instance.setFkEntrada(fkEntrada);
            return this;
        }

        public PostEntradaVeiculo build() {
            return instance;
        }
    }
}
