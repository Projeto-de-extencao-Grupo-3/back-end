package geo.track.jornada.service;

import geo.track.domain.Veiculo;
import geo.track.enums.os.StatusVeiculo;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.OrdemDeServicoRepository;
import geo.track.jornada.entity.RegistroEntrada;
import geo.track.jornada.entity.RegistroEntradaRepository;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.request.entrada.RequestEntradaEfetivaSemCadastro;
import geo.track.jornada.service.usecase.CadastrarOrdemServicoUseCase;
import geo.track.jornada.service.usecase.CadastrarVeiculoUseCase;
import geo.track.mapper.RegistroEntradaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EntradaEfetivaSemCadastroStrategy implements JornadaStrategy<RequestEntradaEfetivaSemCadastro, RegistroEntrada> {
    private final CadastrarOrdemServicoUseCase CADASTRAR_ORDEM_PORT;
    private final RegistroEntradaRepository REGISTRO_ENTRADA_REPOSITORY;
    private final CadastrarVeiculoUseCase CADASTRAR_VEICULO_PORT;
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.ENTRADA_EFETIVA_SEM_CADASTRO.equals(tipoJornada);
    }

    @Override
    public RegistroEntrada execute(RequestEntradaEfetivaSemCadastro request) {
        Veiculo veiculo = CADASTRAR_VEICULO_PORT.execute(request.veiculo());

        OrdemDeServico os = OrdemDeServico.builder()
                .status(StatusVeiculo.AGUARDANDO_ORCAMENTO)
                .valorTotal(0.0)
                .valorTotalProdutos(0.0)
                .valorTotalServicos(0.0)
                .ativo(true)
                .dataAtualizacao(LocalDate.now())
                .build();

        RegistroEntrada entradaEfetiva = RegistroEntradaMapper.toEntity(request.entrada(), veiculo, os);

        os.setFkEntrada(entradaEfetiva);
        entradaEfetiva.setFkOrdemServico(os);

        ORDEM_SERVICO_REPOSITORY.save(os);

        return REGISTRO_ENTRADA_REPOSITORY.save(entradaEfetiva);
    }
}
