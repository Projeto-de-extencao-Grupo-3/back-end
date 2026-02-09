package geo.track.service;

import geo.track.domain.Funcionario;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
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
    private final FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByEmail(email.toLowerCase());

        if (funcionarioOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", email));
        }

        return new UsuarioDetalhesDto(funcionarioOpt.get().getFkOficina(), funcionarioOpt.get());
    }
}
