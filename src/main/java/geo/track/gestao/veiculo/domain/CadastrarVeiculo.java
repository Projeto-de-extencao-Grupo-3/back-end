package geo.track.gestao.veiculo.domain;

import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.veiculo.infraestructure.persistence.entity.Veiculo;
import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.gestao.veiculo.infraestructure.persistence.VeiculoRepository;
import geo.track.gestao.cliente.domain.ClienteService;
import geo.track.gestao.veiculo.application.CadastrarVeiculoUseCase;
import geo.track.gestao.veiculo.infraestructure.VeiculoMapper;
import geo.track.gestao.veiculo.infraestructure.request.RequestPostVeiculo;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.VeiculoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarVeiculo implements CadastrarVeiculoUseCase {
    private final VeiculoRepository VEICULO_REPOSITORY;
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final ClienteService CLIENTE_SERVICE;
    private final Log log;

    public Veiculo execute(RequestPostVeiculo body) {
        log.info("Iniciando cadastro de novo veículo com placa: {}", body.getPlaca());

        if(VEICULO_REPOSITORY.existsByPlacaIgnoreCase(body.getPlaca())){
            log.warn("Falha ao cadastrar: Placa {} já existe no sistema", body.getPlaca());
            throw new ConflictException(VeiculoExceptionMessages.PLACA_EXISTENTE, Domains.VEICULO);
        }

        Cliente cliente = CLIENTE_SERVICE.buscarClientePorId(body.getIdCliente());

        Veiculo veiculo = VeiculoMapper.toEntity(body);
        veiculo.setFkCliente(cliente);

        Veiculo salvo = VEICULO_REPOSITORY.save(veiculo);
        log.info("Veículo cadastrado com sucesso. ID: {}", salvo.getIdVeiculo());
        return salvo;
    }
}
