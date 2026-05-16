package geo.track.gestao.veiculo.domain;

import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.request.RequestPutVeiculo;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarVeiculoTest {

    @Mock
    private VeiculoRepository VEICULO_REPOSITORY;

    @Mock
    private VeiculoService VEICULO_SERVICE;

    @Mock
    private Log log;

    private AtualizarVeiculo useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarVeiculo(VEICULO_REPOSITORY, VEICULO_SERVICE, log);
    }

    @Test
    void deveExecutarComSucesso() {
        RequestPutVeiculo body = new RequestPutVeiculo();
        body.setMarca("GM");
        body.setModelo("Onix");
        body.setPrefixo("2200");
        body.setAnoModelo(2024);

        Veiculo existente = new Veiculo();
        existente.setIdVeiculo(1);

        when(VEICULO_SERVICE.buscarVeiculoPeloId(1)).thenReturn(existente);
        when(VEICULO_REPOSITORY.save(existente)).thenReturn(existente);

        Veiculo retorno = useCase.execute(1, body);

        assertSame(existente, retorno);
        assertEquals("Onix", retorno.getModelo());
        verify(VEICULO_REPOSITORY).save(existente);
    }

}
