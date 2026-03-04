package geo.track.service;

import geo.track.config.GerenciadorTokenJwt;
import geo.track.domain.Funcionario;
import geo.track.domain.Oficinas;
import geo.track.dto.autenticacao.UsuarioLoginDto;
import geo.track.dto.autenticacao.UsuarioMapper;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.oficinas.request.OficinaPatchEmailDTO;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.exception.constraint.message.EnumDomains;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.OficinaExceptionMessages;
import geo.track.repository.FuncionarioRepository;
import geo.track.repository.OficinaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OficinaService {
    private final OficinaRepository OFICINA_REPOSITORY;
    private final GerenciadorTokenJwt GERENCIADOR_TOKEN_JWT;
    private final AuthenticationManager AUTHENTICATION_MANAGER;
    private final FuncionarioRepository FUNCIONARIO_REPOSITORY;

    public Oficinas cadastrar(Oficinas Oficinas){
        log.info("Iniciando persistência de nova oficina: {}", Oficinas.getRazaoSocial());
        if (OFICINA_REPOSITORY.findByCnpj(Oficinas.getCnpj()).isPresent()){
            log.error("Falha ao cadastrar: CNPJ {} já existe no sistema.", Oficinas.getCnpj());
            throw new ConflictException(String.format(OficinaExceptionMessages.CNPJ_JA_CADASTRADO, Oficinas.getCnpj()), EnumDomains.OFICINA);
        }
        Oficinas salva = OFICINA_REPOSITORY.save(Oficinas);
        log.info("Oficina salva com sucesso. ID gerado: {}", salva.getIdOficina());
        return salva;
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto body) {
        log.info("Iniciando processo de autenticação para o email: {}", body.getEmail());

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                body.getEmail(), body.getSenha());

        final Authentication authentication = this.AUTHENTICATION_MANAGER.authenticate(credentials);

        Funcionario funcionarioAutenticado =
                FUNCIONARIO_REPOSITORY.findByEmail(body.getEmail())
                        .orElseThrow(
                                () -> {
                                    log.error("Usuário não encontrado após autenticação: {}", body.getEmail());
                                    return new DataNotFoundException(OficinaExceptionMessages.EMAIL_NAO_CADASTRADO, EnumDomains.FUNCIONARIO);
                                }
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = GERENCIADOR_TOKEN_JWT.generateToken(authentication, funcionarioAutenticado);
        log.info("Token JWT gerado com sucesso para: {}", funcionarioAutenticado.getNome());

        return UsuarioMapper.of(funcionarioAutenticado, token);
    }

    public List<Oficinas> listar(){
        log.info("Buscando lista completa de oficinas no banco de dados.");
        return OFICINA_REPOSITORY.findAll();
    }

    public Oficinas findOficinasById(Integer id){
        log.info("Pesquisando oficina por ID: {}", id);
        return OFICINA_REPOSITORY.findById(id).orElseThrow(() -> {
            log.warn("Oficina com ID {} não foi encontrada.", id);
            return new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, id), EnumDomains.OFICINA);
        });
    }

    public List<Oficinas> findOficinasByRazaoSocial(String razaoSocial){
        log.info("Pesquisando oficinas contendo a razão social: {}", razaoSocial);
        return OFICINA_REPOSITORY.findByrazaoSocialContainingIgnoreCase(razaoSocial);
    }

    public Oficinas findOficinasByCnpj(String cnpj){
        log.info("Pesquisando oficina pelo CNPJ: {}", cnpj);
        return OFICINA_REPOSITORY.findByCnpj(cnpj).orElseThrow(() -> {
            log.warn("Nenhuma oficina encontrada para o CNPJ: {}", cnpj);
            return new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_CNPJ, cnpj), EnumDomains.OFICINA);
        });
    }

    public Oficinas atualizar(Integer id, Oficinas oficinas){
        log.info("Iniciando atualização completa da oficina ID: {}", id);
        if (OFICINA_REPOSITORY.existsById(id)){
            oficinas.setIdOficina(id);
            Oficinas empSalva = OFICINA_REPOSITORY.save(oficinas);
            log.info("Dados da oficina ID {} atualizados com sucesso.", id);
            return empSalva;
        }
        log.error("Impossível atualizar: Oficina ID {} inexistente.", id);
        throw new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, id), EnumDomains.OFICINA);
    }

    public Oficinas patchEmail(OficinaPatchEmailDTO dto){
        log.info("Iniciando atualização parcial de email para oficina ID: {}", dto.getId());
        Optional<Oficinas> Oficinas = OFICINA_REPOSITORY.findById(dto.getId());

        if(Oficinas.isEmpty()){
            log.warn("Falha no patch: Oficina ID {} não encontrada.", dto.getId());
            throw new DataNotFoundException(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, EnumDomains.OFICINA);
        }
        Oficinas emp = Oficinas.get();

        emp.setIdOficina(dto.getId());
        emp.setEmail(dto.getEmail());
        log.info("Email da oficina ID {} alterado para: {}", dto.getId(), dto.getEmail());
        return OFICINA_REPOSITORY.save(emp);
    }


    public Oficinas patchStatus(OficinaPatchStatusDTO dto){
        log.info("Iniciando atualização de status para oficina ID: {}", dto.getId());
        Optional<Oficinas> Oficinas = OFICINA_REPOSITORY.findById(dto.getId());

        if(Oficinas.isEmpty()){
            log.warn("Falha no patch status: Oficina ID {} não encontrada.", dto.getId());
            throw new DataNotFoundException(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, EnumDomains.OFICINA);
        }
        Oficinas emp = Oficinas.get();

        emp.setIdOficina(dto.getId());
        emp.setStatus(dto.getStatus());
        log.info("Status da oficina ID {} alterado para: {}", dto.getId(), dto.getStatus());
        return OFICINA_REPOSITORY.save(emp);
    }

    public void remover(Integer id){
        log.info("Iniciando exclusão da oficina ID: {}", id);
        if (!OFICINA_REPOSITORY.existsById(id)){
            log.error("Falha ao remover: Oficina ID {} não existe.", id);
            throw new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, id), EnumDomains.OFICINA);
        }
        OFICINA_REPOSITORY.deleteById(id);
        log.info("Oficina ID {} removida permanentemente.", id);
    }
}