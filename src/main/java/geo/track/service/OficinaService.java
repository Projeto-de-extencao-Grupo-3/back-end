package geo.track.service;

import geo.track.gestao.entity.Oficina;
import geo.track.infraestructure.annotation.ToRefactor;
import geo.track.infraestructure.config.GerenciadorTokenJwt;
import geo.track.dto.autenticacao.UsuarioLoginDto;
import geo.track.dto.autenticacao.UsuarioMapper;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.oficinas.request.OficinaPatchEmailDTO;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.dto.oficinas.request.RequestPutOficina;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.OficinaExceptionMessages;
import geo.track.gestao.util.OficinaMapper;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.entity.repository.FuncionarioRepository;
import geo.track.gestao.entity.repository.OficinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OficinaService {
    private final OficinaRepository OFICINA_REPOSITORY;
    private final GerenciadorTokenJwt GERENCIADOR_TOKEN_JWT;
    private final AuthenticationManager AUTHENTICATION_MANAGER;
    private final FuncionarioRepository FUNCIONARIO_REPOSITORY;
    private final Log log;

    @ToRefactor
    public Oficina cadastrar(Oficina body){
        log.info("Iniciando persistência de nova oficina: {}", body.getRazaoSocial());
        if (OFICINA_REPOSITORY.findByCnpj(body.getCnpj()).isPresent()){
            log.error("Falha ao cadastrar: CNPJ {} já existe no sistema.", body.getCnpj());
            throw new ConflictException(String.format(OficinaExceptionMessages.CNPJ_JA_CADASTRADO, body.getCnpj()), Domains.OFICINA);
        }
        Oficina salva = OFICINA_REPOSITORY.save(body);
        log.info("Oficina salva com sucesso. ID gerado: {}", salva.getIdOficina());
        return salva;
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto body) {
        log.info("Iniciando processo de autenticação para o email: {}", body.getEmail());

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                body.getEmail(), body.getSenha());

        final Authentication authentication = this.AUTHENTICATION_MANAGER.authenticate(credentials);
        log.info("Credenciais válidas para: {}", body.getEmail());

        var funcionarioAutenticado =
                FUNCIONARIO_REPOSITORY.findByEmail(body.getEmail())
                        .orElseThrow(() -> {
                            log.error("Usuário autenticado, mas registro não encontrado no banco para email: {}", body.getEmail());
                            return new DataNotFoundException("Email do usuário não cadastrado", Domains.FUNCIONARIO);
                        });

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Contexto de segurança atualizado para o usuário.");

        final String token = GERENCIADOR_TOKEN_JWT.generateToken(authentication, funcionarioAutenticado);
        log.info("Token JWT gerado com sucesso para: {}", funcionarioAutenticado.getNome());

        return UsuarioMapper.of(funcionarioAutenticado, token);
    }

    public List<Oficina> listar(){
        log.info("Buscando lista completa de oficinas no banco de dados.");
        return OFICINA_REPOSITORY.findAll();
    }

    public Oficina findOficinasById(Integer id){
        log.info("Pesquisando oficina por ID: {}", id);
        return OFICINA_REPOSITORY.findById(id).orElseThrow(() -> {
            log.warn("Oficina com ID {} não foi encontrada.", id);
            return new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, id), Domains.OFICINA);
        });
    }

    public List<Oficina> findOficinasByRazaoSocial(String razaoSocial){
        log.info("Pesquisando oficinas contendo a razão social: {}", razaoSocial);
        return OFICINA_REPOSITORY.findByrazaoSocialContainingIgnoreCase(razaoSocial);
    }

    public Oficina findOficinasByCnpj(String cnpj){
        log.info("Pesquisando oficina pelo CNPJ: {}", cnpj);
        return OFICINA_REPOSITORY.findByCnpj(cnpj).orElseThrow(() -> {
            log.warn("Nenhuma oficina encontrada para o CNPJ: {}", cnpj);
            return new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_CNPJ, cnpj), Domains.OFICINA);
        });
    }

    @ToRefactor
    public Oficina atualizar(RequestPutOficina body){
        log.info("Iniciando atualização completa da oficina ID: {}", body.getIdOficina());
        if (!OFICINA_REPOSITORY.existsById(body.getIdOficina())){
            log.error("Impossível atualizar: Oficina ID {} inexistente.", body.getIdOficina());
            throw new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, body.getIdOficina()), Domains.OFICINA);
        }

        Oficina existente = this.findOficinasById(body.getIdOficina());
        Oficina atualizada = OficinaMapper.toEntityUpdate(existente, body);

        atualizada = OFICINA_REPOSITORY.save(atualizada);
        log.info("Dados da oficina ID {} atualizados com sucesso.", body.getIdOficina());
        return atualizada;
    }

    @ToRefactor
    public Oficina patchEmail(OficinaPatchEmailDTO dto){
        log.info("Iniciando atualização parcial de email para oficina ID: {}", dto.getId());
        Optional<Oficina> Oficinas = OFICINA_REPOSITORY.findById(dto.getId());

        if(Oficinas.isEmpty()){
            log.warn("Falha no patch: Oficina ID {} não encontrada.", dto.getId());
            throw new DataNotFoundException(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, Domains.OFICINA);
        }

        Oficina emp = Oficinas.get();
        emp.setEmail(dto.getEmail());

        log.info("Email da oficina ID {} alterado para: {}", dto.getId(), dto.getEmail());
        return OFICINA_REPOSITORY.save(emp);
    }


    @ToRefactor
    public Oficina patchStatus(OficinaPatchStatusDTO dto){
        log.info("Iniciando atualização de status para oficina ID: {}", dto.getId());
        Optional<Oficina> Oficinas = OFICINA_REPOSITORY.findById(dto.getId());

        if(Oficinas.isEmpty()){
            log.warn("Falha no patch status: Oficina ID {} não encontrada.", dto.getId());
            throw new DataNotFoundException(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, Domains.OFICINA);
        }

        Oficina emp = Oficinas.get();
        emp.setStatus(dto.getStatus());
        log.info("Status da oficina ID {} alterado para: {}", dto.getId(), dto.getStatus());
        return OFICINA_REPOSITORY.save(emp);
    }

    @ToRefactor
    public void remover(Integer id){
        log.info("Iniciando exclusão da oficina ID: {}", id);
        if (!OFICINA_REPOSITORY.existsById(id)){
            log.error("Falha ao remover: Oficina ID {} não existe.", id);
            throw new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, id), Domains.OFICINA);
        }
        OFICINA_REPOSITORY.deleteById(id);
        log.info("Oficina ID {} excluída com sucesso.", id);
    }
}