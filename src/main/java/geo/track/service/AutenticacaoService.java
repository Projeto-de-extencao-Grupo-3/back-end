package geo.track.service;

import geo.track.domain.Funcionario;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.log.Log;
import geo.track.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {
    private final FuncionarioRepository FUNCIONARIO_REPOSITORY;
    private final Log log;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Iniciando tentativa de carregamento de usuário pelo email: {}", email);

        Optional<Funcionario> funcionarioOpt = FUNCIONARIO_REPOSITORY.findByEmail(email.toLowerCase());

        if (funcionarioOpt.isEmpty()) {
            log.warn("Falha na autenticação: usuário com email {} não encontrado", email);
            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", email));
        }

        log.info("Usuário {} encontrado com sucesso. Retornando detalhes do usuário.", email);
        return new UsuarioDetalhesDto(funcionarioOpt.get().getFkOficina(), funcionarioOpt.get());
    }
}
