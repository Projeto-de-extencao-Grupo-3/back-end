package geo.track.gestao.oficina.domain;

import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeletarOficinaTest {

    @Mock
    private OficinaRepository OFICINA_REPOSITORY;

    @Mock
    private Log log;

    private DeletarOficina useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletarOficina(OFICINA_REPOSITORY, log);
    }

    @Test
    void deveExecutarComSucesso() {
        when(OFICINA_REPOSITORY.existsById(1)).thenReturn(true);

        useCase.execute(1);

        verify(OFICINA_REPOSITORY).deleteById(1);
    }

    @Test
    void deveLancarDataNotFoundException_QuandoOficinaRepositoryExistsbyidId() {
        when(OFICINA_REPOSITORY.existsById(1)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> useCase.execute(1));
    }

}
