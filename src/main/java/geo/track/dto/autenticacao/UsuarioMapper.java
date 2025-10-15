package geo.track.dto.autenticacao;

import geo.track.domain.Oficinas;

public class UsuarioMapper {
    public static Oficinas of(UsuarioCriacaoDto usuarioCriacaoDto) {
        Oficinas usuario = new Oficinas();
        usuario.setRazaoSocial(usuarioCriacaoDto.getRazaoSocial());
        usuario.setCnpj(usuarioCriacaoDto.getCnpj());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setDtCriacao(usuarioCriacaoDto.getDt_criacao());
        usuario.setStatus(usuarioCriacaoDto.getStatus());
        usuario.setSenha(usuarioCriacaoDto.getSenha());

        return usuario;
    }

    public static Oficinas of(UsuarioLoginDto usuarioLoginDto) {
        Oficinas usuario = new Oficinas();

        usuario.setCnpj(usuarioLoginDto.getCnpj());
        usuario.setSenha(usuarioLoginDto.getSenha());

        return usuario;
    }

    public static UsuarioTokenDto of(Oficinas beneficiario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setIdCliente(beneficiario.getIdEmpresa());
        usuarioTokenDto.setCnpj(beneficiario.getCnpj());
        usuarioTokenDto.setNome(beneficiario.getRazaoSocial());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }

    public static UsuarioListarDto of(Oficinas beneficiario) {
        UsuarioListarDto usuarioListarDto = new UsuarioListarDto();

        usuarioListarDto.setIdCliente(beneficiario.getIdEmpresa());
        usuarioListarDto.setCnpj(beneficiario.getCnpj());
        usuarioListarDto.setNome(beneficiario.getRazaoSocial());

        return usuarioListarDto;
    }
}

