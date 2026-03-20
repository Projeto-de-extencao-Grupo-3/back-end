package geo.track.jornada.service.entrada;

import geo.track.domain.Veiculo;
import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.entity.repository.RegistroEntradaRepository;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.request.entrada.RequestEntradaEfetivaSemCadastro;
import geo.track.jornada.service.EntradaJornadaStrategy;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import geo.track.jornada.service.usecase.CadastrarVeiculoUseCase;
import geo.track.mapper.RegistroEntradaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ENTRADA EFETIVA SEM CADASTRO **/
@Component
@RequiredArgsConstructor
public class EntradaEfetivaSemCadastroStrategy implements EntradaJornadaStrategy {
    private final CadastrarOrdemServicoUseCase CADASTRAR_ORDEM_PORT;
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final CadastrarVeiculoUseCase CADASTRAR_VEICULO_PORT;

    @Override
    public Boolean isApplicable(TipoJornada tipo) {
        return TipoJornada.ENTRADA_EFETIVA_SEM_CADASTRO.equals(tipo);
    }

    /**
     *
     */
    @Override
    public RegistroEntrada execute(GetJornada request) {
        RequestEntradaEfetivaSemCadastro requestEntradaEfetivaSemCadastro = (RequestEntradaEfetivaSemCadastro) request;

        StatusVeiculo status = StatusVeiculo.AGUARDANDO_ORCAMENTO;
        OrdemDeServico ordemDeServico = CADASTRAR_ORDEM_PORT.execute(status);
        Veiculo veiculo = CADASTRAR_VEICULO_PORT.execute(requestEntradaEfetivaSemCadastro.veiculo());

        RegistroEntrada entradaEfetiva = RegistroEntradaMapper.toEntity(requestEntradaEfetivaSemCadastro.entrada(), veiculo, ordemDeServico);
        return REGISTRO_ENTRADA_REPOSITORY.save(entradaEfetiva);
    }
}
