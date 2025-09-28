package geo.track.dto.autenticacao;

import geo.track.domain.Empresas;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UsuarioDetalhesDto implements UserDetails {

    private final String nome;

    private final String email;

    private final String cnpj;

    private final String senha;

    public UsuarioDetalhesDto(Empresas beneficiario) {
        this.nome = beneficiario.getRazaoSocial();
        this.email = beneficiario.getEmail();
        this.cnpj = beneficiario.getCnpj();
        this.senha = beneficiario.getSenha();
    }

    public String getNome() {
        return nome;
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
        return cnpj;
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
