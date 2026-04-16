package geo.track.jornada.service.entrada.implementation;

import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.gestao.entity.Veiculo;
import geo.track.jornada.enums.Status;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.request.entrada.RequestEntrada;
import geo.track.jornada.request.entrada.RequestEntradaEfetivaSemCadastro;
import geo.track.jornada.service.entrada.EntradaEfetivaSemCadastroUseCase;
import geo.track.jornada.service.usecase.CadastrarEntradaUseCase;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import geo.track.gestao.service.veiculo.CadastrarVeiculoUseCase;
import geo.track.jornada.util.RegistroEntradaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ENTRADA EFETIVA SEM CADASTRO **/
@Component
@RequiredArgsConstructor
public class EntradaEfetivaSemCadastro implements EntradaEfetivaSemCadastroUseCase {
    private final CadastrarOrdemServicoUseCase CADASTRAR_ORDEM_PORT;
    private final CadastrarEntradaUseCase CADASTRAR_ENTRADA_PORT;
    private final CadastrarVeiculoUseCase CADASTRAR_VEICULO_PORT;

    @Override
    public RegistroEntrada execute(RequestPostVeiculo requestVeiculo, RequestEntrada requestEntrada) {

        Status status = Status.AGUARDANDO_ORCAMENTO;
        OrdemDeServico ordemDeServico = CADASTRAR_ORDEM_PORT.execute(status);

        Veiculo veiculo = CADASTRAR_VEICULO_PORT.execute(requestVeiculo);

        RegistroEntrada entradaEfetiva = RegistroEntradaMapper.toEntity(requestEntrada, veiculo, ordemDeServico);

        return CADASTRAR_ENTRADA_PORT.execute(entradaEfetiva);
    }
}
