package geo.track.dto.autenticacao;

import geo.track.domain.Funcionario;
import geo.track.domain.Oficinas;

public class UsuarioMapper {
    public static Oficinas of(UsuarioCriacaoDto usuarioCriacaoDto) {
        Oficinas usuario = new Oficinas();
        usuario.setRazaoSocial(usuarioCriacaoDto.getRazaoSocial());
        usuario.setCnpj(usuarioCriacaoDto.getCnpj());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setDtCriacao(usuarioCriacaoDto.getDt_criacao());
        usuario.setStatus(usuarioCriacaoDto.getStatus());
        return usuario;
    }

    public static Oficinas of(UsuarioLoginDto usuarioLoginDto) {
        Oficinas usuario = new Oficinas();

        usuario.setEmail(usuarioLoginDto.getEmail());

        return usuario;
    }

    public static UsuarioTokenDto of(Funcionario funcionario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setIdOficina(funcionario.getFkOficina().getIdOficina());
        usuarioTokenDto.setCnpj(funcionario.getFkOficina().getCnpj());
        usuarioTokenDto.setNome(funcionario.getFkOficina().getRazaoSocial());
        usuarioTokenDto.setEmail(funcionario.getEmail());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }

    public static UsuarioListarDto of(Oficinas beneficiario) {
        UsuarioListarDto usuarioListarDto = new UsuarioListarDto();

        usuarioListarDto.setIdCliente(beneficiario.getIdOficina());
        usuarioListarDto.setCnpj(beneficiario.getCnpj());
        usuarioListarDto.setNome(beneficiario.getRazaoSocial());

        return usuarioListarDto;
    }
}

