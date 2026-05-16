package geo.track.gestao.veiculo.domain;

import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
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
class DeletarVeiculoTest {

    @Mock
    private VeiculoRepository VEICULO_REPOSITORY;

    @Mock
    private Log log;

    private DeletarVeiculo useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletarVeiculo(VEICULO_REPOSITORY, log);
    }

    @Test
    void deveExecutarComSucesso() {
        when(VEICULO_REPOSITORY.existsById(1)).thenReturn(true);

        useCase.execute(1);

        verify(VEICULO_REPOSITORY).deleteById(1);
    }

    @Test
    void deveLancarDataNotFoundException_QuandoVeiculoRepositoryExistsbyidId() {
        when(VEICULO_REPOSITORY.existsById(1)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> useCase.execute(1));
    }

}
