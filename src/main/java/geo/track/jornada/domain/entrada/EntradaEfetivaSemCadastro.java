package geo.track.jornada.domain.entrada;

import geo.track.gestao.veiculo.application.CadastrarVeiculoUseCase;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.veiculo.infraestructure.request.RequestPostVeiculo;
import geo.track.jornada.application.CadastrarEntradaUseCase;
import geo.track.jornada.application.CadastrarOrdemServicoUseCase;
import geo.track.jornada.application.entrada.EntradaEfetivaSemCadastroUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.request.entrada.RequestEntrada;
import geo.track.jornada.infraestructure.mapper.RegistroEntradaMapper;
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
        Veiculo veiculo = CADASTRAR_VEICULO_PORT.execute(requestVeiculo);

        OrdemDeServico ordemDeServico = CADASTRAR_ORDEM_PORT.execute(status, veiculo.getIdVeiculo());


        RegistroEntrada entradaEfetiva = RegistroEntradaMapper.toEntity(requestEntrada, veiculo, ordemDeServico);

        return CADASTRAR_ENTRADA_PORT.execute(entradaEfetiva);
    }
}
