package geo.track.dto.autenticacao;

import geo.track.domain.Empresas;

public class UsuarioMapper {
    public static Empresas of(UsuarioCriacaoDto usuarioCriacaoDto) {
        Empresas usuario = new Empresas();
        usuario.setRazaoSocial(usuarioCriacaoDto.getRazaoSocial());
        usuario.setCnpj(usuarioCriacaoDto.getCnpj());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setDt_criacao(usuarioCriacaoDto.getDt_criacao());
        usuario.setStatus(usuarioCriacaoDto.getStatus());
        usuario.setSenha(usuarioCriacaoDto.getSenha());

        return usuario;
    }

    public static Empresas of(UsuarioLoginDto usuarioLoginDto) {
        Empresas usuario = new Empresas();

        usuario.setCnpj(usuarioLoginDto.getCnpj());
        usuario.setSenha(usuarioLoginDto.getSenha());

        return usuario;
    }

    public static UsuarioTokenDto of(Empresas beneficiario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setIdCliente(beneficiario.getIdEmpresa());
        usuarioTokenDto.setCnpj(beneficiario.getCnpj());
        usuarioTokenDto.setNome(beneficiario.getRazaoSocial());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }

    public static UsuarioListarDto of(Empresas beneficiario) {
        UsuarioListarDto usuarioListarDto = new UsuarioListarDto();

        usuarioListarDto.setIdCliente(beneficiario.getIdEmpresa());
        usuarioListarDto.setCnpj(beneficiario.getCnpj());
        usuarioListarDto.setNome(beneficiario.getRazaoSocial());

        return usuarioListarDto;
    }
}

