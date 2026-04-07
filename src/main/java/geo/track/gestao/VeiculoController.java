package geo.track.gestao;

import geo.track.controller.swagger.VeiculoSwagger;
import geo.track.dto.veiculos.response.VeiculoHistoricoResponse;
import geo.track.gestao.entity.Veiculo;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import geo.track.dto.veiculos.request.RequestPostVeiculo;
import geo.track.dto.veiculos.request.RequestPutVeiculo;
import geo.track.dto.veiculos.response.VeiculoResponse;
import geo.track.gestao.service.veiculo.*;
import geo.track.gestao.util.VeiculoMapper;
import geo.track.gestao.service.VeiculoService;
import geo.track.jornada.service.ordemServico.OrdemDeServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController implements VeiculoSwagger {
    private final VeiculoService VEICULO_SERVICE;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final CadastrarVeiculoUseCase CADASTRAR_VEICULO_USECASE;
    private final AtualizarVeiculoUseCase ATUALIZAR_VEICULO_USECASE;
    private final DeletarVeiculoUseCase DELETAR_VEICULO_USECASE;
    private final AlterarCorVeiculoUseCase ALTERAR_COR_VEICULO_USECASE;
    private final AlterarPlacaVeiculoUseCase ALTERAR_PLACA_VEICULO_USECASE;

    @Override
    @PostMapping
    public ResponseEntity<VeiculoResponse> cadastrar(@Valid @RequestBody RequestPostVeiculo body){
        Veiculo veicCadastrado = CADASTRAR_VEICULO_USECASE.execute(body);
        return ResponseEntity.status(201).body(VeiculoMapper.toResponse(veicCadastrado));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> listar(){
        List<Veiculo> listaVeiculos = VEICULO_SERVICE.listarVeiculos();

        if(listaVeiculos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(listaVeiculos));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> findVeiculoById(@PathVariable Integer id){
        Veiculo veic = VEICULO_SERVICE.buscarVeiculoPeloId(id);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Override
    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<VeiculoResponse>> findVeiculoByPlaca(@PathVariable String placa){
        List<Veiculo> veic = VEICULO_SERVICE.buscarVeiculoPelaPlaca(placa);

        if(veic.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @GetMapping("/cliente/{id}")
    @Override
    public ResponseEntity<List<VeiculoHistoricoResponse>> findVeiculoByClienteId(@PathVariable Integer id){
        List<Veiculo> veiculos = VEICULO_SERVICE.buscarVeiculoPeloIdCliente(id);

        if(veiculos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(veiculos.stream().map(veiculo -> {
            var ordem = ORDEM_SERVICO_SERVICE.buscarUltimaOrdemServicoPorVeiculo(veiculo.getIdVeiculo());
            return VeiculoMapper.toResponseHistorico(veiculo, ordem);
        }).toList());
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponse> putVeiculo(@PathVariable Integer id, @Valid @RequestBody RequestPutVeiculo body){
        Veiculo veic = ATUALIZAR_VEICULO_USECASE.execute(id, body);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Override
    @PatchMapping("/placa")
    public ResponseEntity<VeiculoResponse> patchPlaca(@RequestBody @Valid RequestPatchPlaca body){
        Veiculo veic = ALTERAR_PLACA_VEICULO_USECASE.execute(body);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @Override
    @PatchMapping("/cor")
    public ResponseEntity<VeiculoResponse> patchCor(@RequestBody @Valid RequestPatchCor veiculoDTO){
        Veiculo veic = ALTERAR_COR_VEICULO_USECASE.execute(veiculoDTO);
        return ResponseEntity.status(200).body(VeiculoMapper.toResponse(veic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculoById(@PathVariable Integer id){
        DELETAR_VEICULO_USECASE.execute(id);
        return ResponseEntity.status(204).build();
    }
}