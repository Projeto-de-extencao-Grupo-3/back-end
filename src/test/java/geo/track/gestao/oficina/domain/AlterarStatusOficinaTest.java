package geo.track.gestao.oficina.domain;

import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.gestao.oficina.infraestructure.request.OficinaPatchStatusDTO;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlterarStatusOficinaTest {

    @Mock
    private OficinaRepository OFICINA_REPOSITORY;

    @Mock
    private OficinaService OFICINA_SERVICEA;

    @Mock
    private Log log;

    private AlterarStatusOficina useCase;

    @BeforeEach
    void setUp() {
        useCase = new AlterarStatusOficina(OFICINA_REPOSITORY, OFICINA_SERVICEA, log);
    }

    @Test
    void deveExecutarComSucesso() {
        OficinaPatchStatusDTO dto = new OficinaPatchStatusDTO(1, false);

        Oficina oficina = new Oficina();
        oficina.setIdOficina(1);
        oficina.setStatus(true);

        when(OFICINA_SERVICEA.buscarOficinaPorId(1)).thenReturn(oficina);
        when(OFICINA_REPOSITORY.save(oficina)).thenReturn(oficina);

        Oficina retorno = useCase.execute(dto);

        assertSame(oficina, retorno);
        assertFalse(retorno.getStatus());
        verify(OFICINA_REPOSITORY).save(oficina);
    }

}
