package geo.track.gestao.oficina.infraestructure.web;

import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.infraestructure.auth.model.UsuarioCriacaoDto;
import geo.track.infraestructure.auth.model.UsuarioLoginDto;
import geo.track.infraestructure.auth.UsuarioMapper;
import geo.track.infraestructure.auth.model.UsuarioTokenDto;
import geo.track.gestao.oficina.infraestructure.request.OficinaPatchStatusDTO;
import geo.track.gestao.oficina.infraestructure.request.RequestPutOficina;
import geo.track.gestao.oficina.infraestructure.response.OficinaResponse;
import geo.track.gestao.oficina.application.AlterarStatusOficinaUseCase;
import geo.track.gestao.oficina.application.AtualizarOficinaUseCase;
import geo.track.gestao.oficina.application.CadastrarOficinaUseCase;
import geo.track.gestao.oficina.application.DeletarOficinaUseCase;
import geo.track.gestao.oficina.infraestructure.OficinaMapper;
import geo.track.gestao.oficina.domain.OficinaService;
import geo.track.infraestructure.log.Log;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oficinas")
@RequiredArgsConstructor
public class OficinaController implements OficinaSwagger {
    private final OficinaService OFICINA_SERVICE;
    private final CadastrarOficinaUseCase CADASTRAR_OFICINA_USECASE;
    private final AtualizarOficinaUseCase ATUALIZAR_OFICINA_USECASE;
    private final AlterarStatusOficinaUseCase ALTERAR_STATUS_OFICINA_USECASE;
    private final DeletarOficinaUseCase DELETAR_OFICINA_USECASE;
    private final Log log;

    @Override
    @PostMapping
    public ResponseEntity<Void> cadastrarEmpresa(@RequestBody @Valid UsuarioCriacaoDto empresa){
        log.info("Iniciando cadastro de nova empresa: {}", empresa.getRazaoSocial());
        final Oficina novaEmpresa = UsuarioMapper.of(empresa);
        this.CADASTRAR_OFICINA_USECASE.execute(novaEmpresa);
        log.info("Empresa cadastrada com sucesso!");
        return ResponseEntity.status(201).build();
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto) {
        log.info("Tentativa de login para o usuário: {}", usuarioLoginDto.getEmail());
        UsuarioTokenDto usuarioTokenDto = this.OFICINA_SERVICE.autenticarUsuario(usuarioLoginDto);
        log.info("Login realizado com sucesso para: {}", usuarioLoginDto.getEmail());
        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<OficinaResponse>> listarEmpresas(){
        log.info("Listando todas as oficinas cadastradas.");
        List<Oficina> lista = OFICINA_SERVICE.listarOficinas();
        log.info("Total de oficinas encontradas: {}", lista.size());
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(lista));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OficinaResponse> getEmpresaById(@PathVariable Integer id){
        log.info("Buscando oficina com ID: {}", id);
        Oficina emp = OFICINA_SERVICE.buscarOficinaPorId(id);
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(emp));
    }

    @Override
    @GetMapping("/razao-social")
    public ResponseEntity<List<OficinaResponse>> getEmpresaByRazaoSocial(@RequestParam String razaoSocial){
        log.info("Buscando oficinas pela Razão Social: {}", razaoSocial);
        List<Oficina> lista = OFICINA_SERVICE.buscarOficinaPorRazaoSocial(razaoSocial);
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(lista));
    }

    @Override
    @GetMapping("/cnpj")
    public ResponseEntity<OficinaResponse> findEmpresaByCNPJ(@RequestParam String cnpj){
        log.info("Buscando oficina pelo CNPJ: {}", cnpj);
        Oficina emp = OFICINA_SERVICE.buscarOficinaPorCnpj(cnpj);
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(emp));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<OficinaResponse> atualizarEmpresa(@PathVariable Integer id, @RequestBody @Valid RequestPutOficina empresa){
        log.info("Atualizando dados da oficina com ID: {}", id);
        empresa.setIdOficina(id);
        Oficina emp = ATUALIZAR_OFICINA_USECASE.execute(empresa);
        log.info("Oficina ID {} atualizada com sucesso.", id);
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(emp));
    }

    @Override
    @PatchMapping("/status")
    public ResponseEntity<OficinaResponse> patchStatus(@RequestBody @Valid OficinaPatchStatusDTO dto){
        log.info("Alterando status da oficina ID: {}", dto.getId());
        Oficina emp = ALTERAR_STATUS_OFICINA_USECASE.execute(dto);
        return ResponseEntity.status(200).body(OficinaMapper.toResponse(emp));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerEmpresa(@PathVariable Integer id){
        log.warn("Solicitação para remover oficina com ID: {}", id);
        DELETAR_OFICINA_USECASE.execute(id);
        log.info("Oficina ID {} removida com sucesso.", id);
        return ResponseEntity.status(204).build();
    }
}
