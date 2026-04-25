package geo.track.jornada.domain.entrada;

import geo.track.gestao.veiculo.domain.VeiculoService;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.jornada.application.CadastrarEntradaUseCase;
import geo.track.jornada.application.CadastrarOrdemServicoUseCase;
import geo.track.jornada.application.entrada.EntradaEfetivaUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.entity.RegistroEntrada;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.infraestructure.request.entrada.RequestEntrada;
import geo.track.jornada.infraestructure.mapper.RegistroEntradaMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ENTRADA EFETIVA **/
@Component
@RequiredArgsConstructor
public class EntradaEfetiva implements EntradaEfetivaUseCase {
    private final CadastrarOrdemServicoUseCase CADASTRAR_ORDEM_PORT;
    private final CadastrarEntradaUseCase CADASTRAR_ENTRADA_PORT;
    private final VeiculoService VEICULO_SERVICE;

    @Transactional
    @Override
    public RegistroEntrada execute(Integer fkVeiculo, RequestEntrada requestEntrada) {

        Status status = Status.AGUARDANDO_ORCAMENTO;
        Veiculo veiculo = VEICULO_SERVICE.buscarVeiculoPeloId(fkVeiculo);

        OrdemDeServico ordemDeServico = CADASTRAR_ORDEM_PORT.execute(status, veiculo.getIdVeiculo());

        RegistroEntrada entradaEfetiva = RegistroEntradaMapper.toEntity(requestEntrada, veiculo, ordemDeServico);

        return CADASTRAR_ENTRADA_PORT.execute(entradaEfetiva);
    }
}
