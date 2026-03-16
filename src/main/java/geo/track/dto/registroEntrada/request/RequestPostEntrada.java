package geo.track.dto.registroEntrada.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record RequestPostEntrada(
        @NotNull
        Integer fkVeiculo,
        @NotNull
        LocalDate dataEntradaEfetiva,
        @NotBlank
        String nomeResponsavel,
        @NotBlank @CPF
        String cpfResponsavel,
        @PositiveOrZero
        Integer quantidadeExtintor,
        @PositiveOrZero
        Integer quantidadeMacaco,
        @PositiveOrZero
        Integer quantidadeChaveRoda,
        @PositiveOrZero
        Integer quantidadeGeladeira,
        @PositiveOrZero
        Integer quantidadeMonitor,
        @PositiveOrZero
        Integer quantidadeEstepe,
        @PositiveOrZero
        Integer quantidadeSomDvd,
        @PositiveOrZero
        Integer quantidadeCaixaFerramentas,
        String observacoes
) {}
