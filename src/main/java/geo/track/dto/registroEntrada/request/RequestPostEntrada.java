package geo.track.dto.registroEntrada.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record RequestPostEntrada(
        Integer idCliente,
        Integer idVeiculo,
        LocalDate dataEntradaEfetiva,
        String nomeResponsavel,
        String cpfResponsavel,
        Integer quantidadeExtintor,
        Integer quantidadeMacaco,
        Integer quantidadeChaveRoda,
        Integer quantidadeGeladeira,
        Integer quantidadeMonitor,
        Integer quantidadeEstepe,
        Integer quantidadeSomDvd,
        Integer quantidadeCaixaFerramenta,
        String observacoes
) {}
