package geo.track.catalogo.item_servico.domain;

import geo.track.catalogo.item_servico.infraestructure.persistence.ItemServicoRepository;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.*;
import geo.track.catalogo.item_servico.infraestructure.request.RequestPostItemServico;
import geo.track.infraestructure.exception.BadRequestException;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdicionarItemServicoTest {

    @Mock
    private ItemServicoRepository ITEM_SERVICO_REPOSITORY;

    @Mock
    private OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Mock
    private Log log;

    private AdicionarItemServico useCase;

    @BeforeEach
    void setUp() {
        useCase = new AdicionarItemServico(ITEM_SERVICO_REPOSITORY, ORDEM_SERVICO_SERVICE, log);
    }

    private OrdemDeServico buildOrdem(Integer id) {
        OrdemDeServico ordem = new OrdemDeServico();
        ordem.setIdOrdemServico(id);
        return ordem;
    }

    @Test
    void deveExecutarComSucesso_QuandoServicoNaoPintura() {
        Integer idOrdem = 1;
        OrdemDeServico ordem = buildOrdem(idOrdem);

        RequestPostItemServico request = new RequestPostItemServico(
                200.0,
                ParteVeiculo.PARACHOQUE,
                LadoVeiculo.DIANTEIRO,
                Servico.FUNILARIA,
                null,
                "Reparo no parachoque",
                null
        );

        ItemServico itemSalvo = new ItemServico();
        itemSalvo.setIdRegistroServico(50);
        itemSalvo.setPrecoCobrado(200.0);
        itemSalvo.setFkOrdemServico(ordem);

        when(ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdem)).thenReturn(ordem);
        when(ITEM_SERVICO_REPOSITORY.save(any(ItemServico.class))).thenReturn(itemSalvo);

        ItemServico resultado = useCase.execute(idOrdem, request);

        assertNotNull(resultado);
        assertEquals(50, resultado.getIdRegistroServico());
        assertEquals(200.0, resultado.getPrecoCobrado());
        verify(ORDEM_SERVICO_SERVICE).buscarOrdemServicoPorId(idOrdem);
        verify(ITEM_SERVICO_REPOSITORY).save(any(ItemServico.class));
    }

    /**
     * ItemServicoMapper.toEntity lança BadRequestException quando tipoServico=PINTURA e tipoPintura é null.
     * Esse comportamento é exercitado através de AdicionarItemServico.execute().
     */
    @Test
    void deveLancarBadRequestException_QuandoPinturaSemTipoPintura() {
        Integer idOrdem = 1;
        OrdemDeServico ordem = buildOrdem(idOrdem);

        RequestPostItemServico request = new RequestPostItemServico(
                300.0,
                ParteVeiculo.CAPO,
                LadoVeiculo.COMPLETO,
                Servico.PINTURA,
                "Azul Metálico",
                "Pintura completa do capô",
                null  // tipoPintura ausente → deve lançar BadRequestException
        );

        when(ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdem)).thenReturn(ordem);

        assertThrows(BadRequestException.class, () -> useCase.execute(idOrdem, request));
        verify(ITEM_SERVICO_REPOSITORY, never()).save(any());
    }

    /**
     * ItemServicoMapper.toEntity lança BadRequestException quando tipoServico=PINTURA e cor é null.
     */
    @Test
    void deveLancarBadRequestException_QuandoPinturaSemCor() {
        Integer idOrdem = 1;
        OrdemDeServico ordem = buildOrdem(idOrdem);

        RequestPostItemServico request = new RequestPostItemServico(
                300.0,
                ParteVeiculo.CAPO,
                LadoVeiculo.COMPLETO,
                Servico.PINTURA,
                null,  // cor ausente → deve lançar BadRequestException
                "Pintura completa do capô",
                TipoPintura.COMPLETA
        );

        when(ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdem)).thenReturn(ordem);

        assertThrows(BadRequestException.class, () -> useCase.execute(idOrdem, request));
        verify(ITEM_SERVICO_REPOSITORY, never()).save(any());
    }
}
