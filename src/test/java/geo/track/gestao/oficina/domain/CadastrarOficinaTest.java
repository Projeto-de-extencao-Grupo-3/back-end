package geo.track.gestao.oficina.domain;

import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarOficinaTest {

    @Mock
    private OficinaRepository OFICINA_REPOSITORY;

    @Mock
    private Log log;

    private CadastrarOficina useCase;

    @BeforeEach
    void setUp() {
        useCase = new CadastrarOficina(OFICINA_REPOSITORY, log);
    }

    @Test
    void deveExecutarComSucesso() {
        Oficina body = new Oficina();
        body.setCnpj("123");

        Oficina salvo = new Oficina();
        salvo.setIdOficina(1);

        when(OFICINA_REPOSITORY.findByCnpj(body.getCnpj())).thenReturn(Optional.empty());
        when(OFICINA_REPOSITORY.save(body)).thenReturn(salvo);

        Oficina retorno = useCase.execute(body);

        assertSame(salvo, retorno);
        verify(OFICINA_REPOSITORY).save(body);
    }

    @Test
    void deveLancarConflictException_QuandoOficinaRepositoryFindbycnpjBodyGetcnpj() {
        Oficina body = new Oficina();
        body.setCnpj("123");

        when(OFICINA_REPOSITORY.findByCnpj(body.getCnpj())).thenReturn(Optional.of(new Oficina()));

        assertThrows(ConflictException.class, () -> useCase.execute(body));

        verify(OFICINA_REPOSITORY, never()).save(any());
    }

}
