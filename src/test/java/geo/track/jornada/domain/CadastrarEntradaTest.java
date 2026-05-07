package geo.track.jornada.domain;

import geo.track.jornada.infraestructure.persistence.RegistroEntradaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CadastrarEntradaTest {

    @Mock
    private RegistroEntradaRepository ENTRADA_REPOSITORY;

    private CadastrarEntrada useCase;

    @BeforeEach
    void setUp() {
        useCase = new CadastrarEntrada(ENTRADA_REPOSITORY);
    }

    @Test
    void deveExecutarComSucesso() {
    }

}
