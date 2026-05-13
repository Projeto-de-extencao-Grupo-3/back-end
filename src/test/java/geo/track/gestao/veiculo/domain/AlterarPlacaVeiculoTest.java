package geo.track.gestao.veiculo.domain;

import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.request.RequestPatchPlaca;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlterarPlacaVeiculoTest {

    @Mock
    private VeiculoRepository VEICULO_REPOSITORY;

    @Mock
    private VeiculoService VEICULO_SERVICE;

    @Mock
    private Log log;

    private AlterarPlacaVeiculo useCase;

    @BeforeEach
    void setUp() {
        useCase = new AlterarPlacaVeiculo(VEICULO_REPOSITORY, VEICULO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
        RequestPatchPlaca body = new RequestPatchPlaca();
        body.setIdVeiculo(1);
        body.setPlaca("BRA2E19");

        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(1);

        when(VEICULO_SERVICE.buscarVeiculoPeloId(1)).thenReturn(veiculo);
        when(VEICULO_REPOSITORY.existsByPlacaIgnoreCase(body.getPlaca())).thenReturn(false);
        when(VEICULO_REPOSITORY.save(veiculo)).thenReturn(veiculo);

        Veiculo retorno = useCase.execute(body);

        assertSame(veiculo, retorno);
        assertEquals("BRA2E19", retorno.getPlaca());
        verify(VEICULO_REPOSITORY).save(veiculo);
    }

    @Test
    void deveLancarConflictException_QuandoVeiculoRepositoryExistsbyplacaignorecaseBodyGetplaca() {
        RequestPatchPlaca body = new RequestPatchPlaca();
        body.setIdVeiculo(1);
        body.setPlaca("BRA2E19");

        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(1);

        when(VEICULO_SERVICE.buscarVeiculoPeloId(1)).thenReturn(veiculo);
        when(VEICULO_REPOSITORY.existsByPlacaIgnoreCase(body.getPlaca())).thenReturn(true);

        assertThrows(ConflictException.class, () -> useCase.execute(body));

        verify(VEICULO_REPOSITORY, never()).save(any());
    }

}
