package geo.track.dto.autenticacao;

import geo.track.domain.Funcionario;
import geo.track.domain.Oficinas;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UsuarioDetalhesDto implements UserDetails {

    private final String nome;
    private final String email;
    private final String senha;
    private final String cnpj;
    private final Integer idOficina;


    public UsuarioDetalhesDto(Oficinas oficina, Funcionario funcionario) {
        this.nome = funcionario.getNome();
        this.email = funcionario.getEmail();
        this.cnpj = oficina.getCnpj();
        this.senha = funcionario.getSenha();
        this.idOficina = oficina.getIdOficina();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
