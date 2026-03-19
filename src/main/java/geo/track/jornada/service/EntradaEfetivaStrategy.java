package geo.track.jornada.service;

import geo.track.domain.Veiculo;
import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.OrdemDeServicoRepository;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.entity.RegistroEntradaRepository;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.request.entrada.RequestEntradaEfetiva;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import geo.track.mapper.RegistroEntradaMapper;
import geo.track.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntradaEfetivaStrategy implements JornadaStrategy<RequestEntradaEfetiva, RegistroEntrada> {
    private final CadastrarOrdemServicoUseCase CADASTRAR_ORDEM_PORT;
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final VeiculoService VEICULO_SERVICE;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.ENTRADA_EFETIVA.equals(tipoJornada);
    }

    @Override
    public RegistroEntrada execute(RequestEntradaEfetiva request) {
        StatusVeiculo status = StatusVeiculo.AGUARDANDO_ORCAMENTO;
        OrdemDeServico ordemDeServico = CADASTRAR_ORDEM_PORT.execute(status);
        Veiculo veiculo = VEICULO_SERVICE.findVeiculoById(request.veiculo());

        RegistroEntrada entradaEfetiva = RegistroEntradaMapper.toEntity(request.entrada(), veiculo, ordemDeServico);

        return REGISTRO_ENTRADA_REPOSITORY.save(entradaEfetiva);
    }
}
