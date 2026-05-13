package geo.track.jornada.domain;

import geo.track.jornada.infraestructure.persistence.RegistroEntradaRepository;
import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        RegistroEntrada entrada = new RegistroEntrada();
        entrada.setIdRegistroEntrada(1);

        when(ENTRADA_REPOSITORY.save(entrada)).thenReturn(entrada);

        RegistroEntrada retorno = useCase.execute(entrada);

        assertSame(entrada, retorno);
        verify(ENTRADA_REPOSITORY).save(entrada);
    }

}
