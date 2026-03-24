package geo.track.infraestructure;

import geo.track.gestao.entity.Funcionario;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.entity.repository.FuncionarioRepository;
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
        Optional<Funcionario> funcionarioOpt = FUNCIONARIO_REPOSITORY.findByEmail(email.toLowerCase());

        if (funcionarioOpt.isEmpty()) {
            log.warn("Falha na autenticação: usuário com email {} não encontrado", email);
            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", email));
        }
        return new UsuarioDetalhesDto(funcionarioOpt.get().getFkOficina(), funcionarioOpt.get());
    }
}
