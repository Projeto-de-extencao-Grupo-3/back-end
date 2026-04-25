package geo.track.catalogo.item_servico.infraestructure.persistence.entity;

public enum LadoVeiculo {
    // Posições Básicas
    DIANTEIRO,
    TRASEIRO,
    CENTRAL,
    COMPLETO,

    // Lados
    DIREITO,
    ESQUERDO,
    LATERAL_DIREITA,
    LATERAL_ESQUERDA,

    // Combinações de Quadrantes
    DIANTEIRO_DIREITO,
    DIANTEIRO_ESQUERDO,
    DIANTEIRO_CENTRAL,
    TRASEIRO_DIREITO,
    TRASEIRO_ESQUERDO,
    TRASEIRO_CENTRAL,

    // Específicos e Estruturais
    TETO,
    SAIA,
    INFERIOR,
    SUPERIOR,
    INTERNO,
    EXTERNO,

    ASSOALHO,
    NAO_APLICAVEL
}