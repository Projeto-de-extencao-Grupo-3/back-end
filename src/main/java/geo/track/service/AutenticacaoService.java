package geo.track.service;

import geo.track.domain.Empresas;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.repository.EmpresasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private EmpresasRepository empresasRepository;

    @Override
    public UserDetails loadUserByUsername(String cnpj) throws UsernameNotFoundException {
        Optional<Empresas> empresaOpt = empresasRepository.findByCnpj(cnpj);

        if (empresaOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", cnpj));
        }

        return new UsuarioDetalhesDto(empresaOpt.get());
    }
}
