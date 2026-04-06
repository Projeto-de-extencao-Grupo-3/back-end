package geo.track.jornada.service.entrada.implementation;

import geo.track.gestao.entity.Veiculo;
import geo.track.jornada.enums.Status;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.jornada.request.entrada.RequestEntrada;
import geo.track.jornada.request.entrada.RequestEntradaEfetiva;
import geo.track.jornada.service.entrada.EntradaEfetivaUseCase;
import geo.track.jornada.service.usecase.CadastrarEntradaUseCase;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import geo.track.jornada.util.RegistroEntradaMapper;
import geo.track.gestao.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ENTRADA EFETIVA **/
@Component
@RequiredArgsConstructor
public class EntradaEfetiva implements EntradaEfetivaUseCase {
    private final CadastrarOrdemServicoUseCase CADASTRAR_ORDEM_PORT;
    private final CadastrarEntradaUseCase CADASTRAR_ENTRADA_PORT;
    private final VeiculoService VEICULO_SERVICE;

    @Override
    public RegistroEntrada execute(Integer fkVeiculo, RequestEntrada requestEntrada) {

        Status status = Status.AGUARDANDO_ORCAMENTO;
        OrdemDeServico ordemDeServico = CADASTRAR_ORDEM_PORT.execute(status);
        Veiculo veiculo = VEICULO_SERVICE.findVeiculoById(fkVeiculo);

        RegistroEntrada entradaEfetiva = RegistroEntradaMapper.toEntity(requestEntrada, veiculo, ordemDeServico);

        return CADASTRAR_ENTRADA_PORT.execute(entradaEfetiva);
    }
}
