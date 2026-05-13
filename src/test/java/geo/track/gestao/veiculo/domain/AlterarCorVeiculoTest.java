package geo.track.gestao.veiculo.domain;

import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.request.RequestPatchCor;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlterarCorVeiculoTest {

    @Mock
    private VeiculoRepository VEICULO_REPOSITORY;

    @Mock
    private VeiculoService VEICULO_SERVICE;

    @Mock
    private Log log;

    private AlterarCorVeiculo useCase;

    @BeforeEach
    void setUp() {
        useCase = new AlterarCorVeiculo(VEICULO_REPOSITORY, VEICULO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
        RequestPatchCor body = new RequestPatchCor();
        body.setIdVeiculo(1);

        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(1);

        when(VEICULO_SERVICE.buscarVeiculoPeloId(1)).thenReturn(veiculo);
        when(VEICULO_REPOSITORY.save(veiculo)).thenReturn(veiculo);

        Veiculo retorno = useCase.execute(body);

        assertSame(veiculo, retorno);
        verify(VEICULO_REPOSITORY).save(veiculo);
    }

}
