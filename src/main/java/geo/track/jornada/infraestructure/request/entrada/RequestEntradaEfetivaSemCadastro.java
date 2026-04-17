package geo.track.jornada.infraestructure.request.entrada;

import geo.track.gestao.veiculo.infraestructure.request.RequestPostVeiculo;

public record RequestEntradaEfetivaSemCadastro(
        RequestPostVeiculo veiculo,

        RequestEntrada entrada

){
    }