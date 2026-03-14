package geo.track.service;

import geo.track.domain.Veiculo;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.VeiculoExceptionMessages;
import geo.track.log.Log;
import geo.track.repository.VeiculoRepository;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class VeiculoService {
    private final VeiculoRepository VEICULO_REPOSITORY;
    private final Log log;

    public Veiculo cadastrar(@RequestBody Veiculo body){
        log.info("Iniciando cadastro de novo veículo com placa: {}", body.getPlaca());
        body.setIdVeiculo(null);

        if(VEICULO_REPOSITORY.existsByPlacaIgnoreCase(body.getPlaca())){
            log.warn("Falha ao cadastrar: Placa {} já existe no sistema", body.getPlaca());
            throw new ConflictException(VeiculoExceptionMessages.PLACA_EXISTENTE, Domains.VEICULO);
        }

        Veiculo salvo = VEICULO_REPOSITORY.save(body);
        log.info("Veículo cadastrado com sucesso. ID: {}", salvo.getIdVeiculo());
        return salvo;
    }

    public List<Veiculo>listar(){
        log.info("Listando todos os veículos");
        return VEICULO_REPOSITORY.findAll();
    }

    public Veiculo findVeiculoById(@PathVariable Integer id){
        log.info("Buscando veículo pelo ID: {}", id);
        return VEICULO_REPOSITORY.findById(id).orElseThrow(
                () -> {
                    log.error("Veículo com ID {} não encontrado", id);
                    return new DataNotFoundException(VeiculoExceptionMessages.VEICULO_NAO_ENCONTRADO_ID, Domains.VEICULO);
                }
        );
    }

    public List<Veiculo> findVeiculoByPlaca(@PathVariable String placa){
        log.info("Buscando veículos que iniciam com a placa: {}", placa);
        return VEICULO_REPOSITORY.findAllByPlacaStartsWithIgnoreCase(placa);
    }

    public Veiculo putEndereco(Integer id, Veiculo body){
        log.info("Atualizando dados completos do veículo ID: {}", id);
        if(VEICULO_REPOSITORY.existsById(id)){
            body.setIdVeiculo(id);
            Veiculo veic = VEICULO_REPOSITORY.save(body);
            log.info("Veículo ID {} atualizado com sucesso", id);
            return veic;
        }

        log.error("Falha na atualização: Veículo ID {} não encontrado", id);
        throw new DataNotFoundException(VeiculoExceptionMessages.VEICULO_NAO_ENCONTRADO_ID, Domains.VEICULO);
    }

    public Veiculo patchPlaca(RequestPatchPlaca body){
        log.info("Solicitação de alteração de placa para o veículo ID: {}", body.getIdVeiculo());
        Optional<Veiculo> veiculoOpt = VEICULO_REPOSITORY.findById(body.getIdVeiculo());

        if(veiculoOpt.isEmpty()){
            log.error("Falha ao alterar placa: Veículo ID {} não encontrado", body.getIdVeiculo());
            throw new DataNotFoundException(VeiculoExceptionMessages.VEICULO_NAO_ENCONTRADO_ID, Domains.VEICULO);
        }

        Veiculo veiculo = veiculoOpt.get();

        if(VEICULO_REPOSITORY.existsByPlacaIgnoreCase(body.getPlaca())){
            log.warn("Falha ao alterar placa: Nova placa {} já está em uso", body.getPlaca());
            throw new ConflictException(VeiculoExceptionMessages.PLACA_JA_EXISTE_OUTRO_VEICULO, Domains.VEICULO);
        }

        veiculo.setPlaca(body.getPlaca());
        Veiculo atualizado = VEICULO_REPOSITORY.save(veiculo);
        log.info("Placa do veículo ID {} atualizada para {} com sucesso", atualizado.getIdVeiculo(), atualizado.getPlaca());
        return atualizado;
    }

    public Veiculo patchCor(RequestPatchCor body){
        log.info("Solicitação de alteração de cor para o veículo ID: {}", body.getIdVeiculo());
        Optional<Veiculo> veiculoOpt = VEICULO_REPOSITORY.findById(body.getIdVeiculo());

        if(veiculoOpt.isEmpty()){
            log.error("Falha ao alterar cor: Veículo ID {} não encontrado", body.getIdVeiculo());
            throw new DataNotFoundException(VeiculoExceptionMessages.VEICULO_NAO_ENCONTRADO_ID, Domains.VEICULO);
        }

        Veiculo veiculo = veiculoOpt.get();
        Veiculo atualizado = VEICULO_REPOSITORY.save(veiculo);
        log.info("Cor do veículo ID {} atualizada com sucesso", atualizado.getIdVeiculo());
        return atualizado;
    }

     public void deleteVeiculoById(@PathVariable Integer id){
         log.info("Solicitação de exclusão do veículo ID: {}", id);
         if(!VEICULO_REPOSITORY.existsById(id)){
             log.error("Falha ao excluir: Veículo ID {} não encontrado", id);
             throw new DataNotFoundException(VeiculoExceptionMessages.VEICULO_NAO_ENCONTRADO_ID, Domains.VEICULO);
         }

         VEICULO_REPOSITORY.deleteById(id);
         log.info("Veículo ID {} excluído com sucesso", id);
     }

    public void deleteVeiculoByPlaca(@PathVariable String placa){
        log.info("Solicitação de exclusão do veículo pela placa: {}", placa);
        if(!VEICULO_REPOSITORY.existsByPlacaIgnoreCase(placa)){
            log.error("Falha ao excluir: Veículo com placa {} não encontrado", placa);
            throw new DataNotFoundException(VeiculoExceptionMessages.VEICULO_NAO_ENCONTRADO_PLACA, Domains.VEICULO);
        }

        // For now, I'll just delete by ID after finding it, which is safer.
        Optional<Veiculo> veiculoToDelete = VEICULO_REPOSITORY.findAllByPlacaStartsWithIgnoreCase(placa)
                                                .stream().findFirst();
        if (veiculoToDelete.isPresent()) {
            VEICULO_REPOSITORY.deleteById(veiculoToDelete.get().getIdVeiculo());
            log.info("Veículo com placa {} (ID: {}) excluído com sucesso", placa, veiculoToDelete.get().getIdVeiculo());
        } else {
            log.error("Erro inesperado ao tentar excluir veículo com placa {}", placa);
            throw new DataNotFoundException(VeiculoExceptionMessages.VEICULO_NAO_ENCONTRADO_PLACA, Domains.VEICULO);
        }
    }
}
