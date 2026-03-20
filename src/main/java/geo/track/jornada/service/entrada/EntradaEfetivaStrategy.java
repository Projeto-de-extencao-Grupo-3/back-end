package geo.track.jornada.service.entrada;

import geo.track.domain.Veiculo;
import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.entity.repository.RegistroEntradaRepository;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.request.entrada.RequestEntradaEfetiva;
import geo.track.jornada.service.EntradaJornadaStrategy;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import geo.track.mapper.RegistroEntradaMapper;
import geo.track.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ENTRADA EFETIVA **/
@Component
@RequiredArgsConstructor
public class EntradaEfetivaStrategy implements EntradaJornadaStrategy {
    private final CadastrarOrdemServicoUseCase CADASTRAR_ORDEM_PORT;
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final VeiculoService VEICULO_SERVICE;

    @Override
    public Boolean isApplicable(TipoJornada tipo) {
        return TipoJornada.ENTRADA_EFETIVA.equals(tipo);
    }

    /**
     * @param request deve ser um RequestEfetiva contendo:
     *                - fkVeiculo: ID do veículo
     *                - entrada: dados da entrada efetiva
     */
    @Override
    public RegistroEntrada execute(GetJornada request) {
        RequestEntradaEfetiva requestEfetiva = (RequestEntradaEfetiva) request;

        StatusVeiculo status = StatusVeiculo.AGUARDANDO_ORCAMENTO;
        OrdemDeServico ordemDeServico = CADASTRAR_ORDEM_PORT.execute(status);
        Veiculo veiculo = VEICULO_SERVICE.findVeiculoById(requestEfetiva.fkVeiculo());

        RegistroEntrada entradaEfetiva = RegistroEntradaMapper.toEntity(requestEfetiva.entrada(), veiculo, ordemDeServico);

        return REGISTRO_ENTRADA_REPOSITORY.save(entradaEfetiva);
    }
}
