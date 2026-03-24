package geo.track.gestao.service.veiculo.implementations;

import geo.track.gestao.entity.Cliente;
import geo.track.gestao.entity.Veiculo;
import geo.track.gestao.entity.repository.ClienteRepository;
import geo.track.gestao.entity.repository.VeiculoRepository;
import geo.track.gestao.service.veiculo.CadastrarVeiculoUseCase;
import geo.track.gestao.util.VeiculoMapper;
import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.VeiculoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarVeiculoImplementation implements CadastrarVeiculoUseCase {
    private final VeiculoRepository VEICULO_REPOSITORY;
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final Log log;

    public Veiculo execute(RequestPostVeiculo body) {
        log.info("Iniciando cadastro de novo veículo com placa: {}", body.getPlaca());

        if(VEICULO_REPOSITORY.existsByPlacaIgnoreCase(body.getPlaca())){
            log.warn("Falha ao cadastrar: Placa {} já existe no sistema", body.getPlaca());
            throw new ConflictException(VeiculoExceptionMessages.PLACA_EXISTENTE, Domains.VEICULO);
        }

        Cliente cliente = CLIENTE_REPOSITORY.findById(body.getIdCliente()).orElseThrow(() ->
                new DataNotFoundException("Cliente proprietário não encontrado.", Domains.CLIENTE));

        Veiculo veiculo = VeiculoMapper.toEntity(body);
        veiculo.setFkCliente(cliente);

        Veiculo salvo = VEICULO_REPOSITORY.save(veiculo);
        log.info("Veículo cadastrado com sucesso. ID: {}", salvo.getIdVeiculo());
        return salvo;
    }
}
