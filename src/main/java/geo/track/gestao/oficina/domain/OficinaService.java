package geo.track.gestao.oficina.domain;

import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.infraestructure.config.GerenciadorTokenJwt;
import geo.track.infraestructure.auth.model.UsuarioLoginDto;
import geo.track.infraestructure.auth.UsuarioMapper;
import geo.track.infraestructure.auth.model.UsuarioTokenDto;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.OficinaExceptionMessages;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.funcionario.infraestructure.persistence.FuncionarioRepository;
import geo.track.gestao.oficina.infraestructure.persistence.OficinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OficinaService {
    private final OficinaRepository OFICINA_REPOSITORY;
    private final GerenciadorTokenJwt GERENCIADOR_TOKEN_JWT;
    private final AuthenticationManager AUTHENTICATION_MANAGER;
    private final FuncionarioRepository FUNCIONARIO_REPOSITORY;
    private final Log log;

    public UsuarioTokenDto autenticarUsuario(UsuarioLoginDto body) {
        log.info("Iniciando processo de autenticacao para o email: {}", body.getEmail());

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                body.getEmail(), body.getSenha());

        final Authentication authentication = this.AUTHENTICATION_MANAGER.authenticate(credentials);
        log.info("Credenciais validas para: {}", body.getEmail());

        var funcionarioAutenticado =
                FUNCIONARIO_REPOSITORY.findByEmail(body.getEmail())
                        .orElseThrow(() -> {
                            log.error("Usuario autenticado, mas registro nao encontrado no banco para email: {}", body.getEmail());
                            return new DataNotFoundException("Email do usuario nao cadastrado", Domains.FUNCIONARIO);
                        });

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Contexto de seguranca atualizado para o usuario.");

        final String token = GERENCIADOR_TOKEN_JWT.generateToken(authentication, funcionarioAutenticado);
        log.info("Token JWT gerado com sucesso para: {}", funcionarioAutenticado.getNome());

        return UsuarioMapper.of(funcionarioAutenticado, token);
    }

    public List<Oficina> listarOficinas() {
        log.info("Buscando lista completa de oficinas no banco de dados.");
        return OFICINA_REPOSITORY.findAll();
    }

    public Oficina buscarOficinaPorId(Integer id) {
        log.info("Pesquisando oficina por ID: {}", id);
        return OFICINA_REPOSITORY.findById(id).orElseThrow(() -> {
            log.warn("Oficina com ID {} nao foi encontrada.", id);
            return new DataNotFoundException(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_GENERICO, Domains.OFICINA);
        });
    }

    public List<Oficina> buscarOficinaPorRazaoSocial(String razaoSocial) {
        log.info("Pesquisando oficinas contendo a razao social: {}", razaoSocial);
        return OFICINA_REPOSITORY.findByrazaoSocialContainingIgnoreCase(razaoSocial);
    }

    public Oficina buscarOficinaPorCnpj(String cnpj) {
        log.info("Pesquisando oficina pelo CNPJ: {}", cnpj);
        return OFICINA_REPOSITORY.findByCnpj(cnpj).orElseThrow(() -> {
            log.warn("Nenhuma oficina encontrada para o CNPJ: {}", cnpj);
            return new DataNotFoundException(String.format(OficinaExceptionMessages.OFICINA_NAO_ENCONTRADA_CNPJ, cnpj), Domains.OFICINA);
        });
    }
}