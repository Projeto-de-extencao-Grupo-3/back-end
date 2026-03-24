package geo.track.dto.autenticacao;

import geo.track.gestao.entity.Funcionario;
import geo.track.gestao.entity.Oficina;

public class UsuarioMapper {
    public static Oficina of(UsuarioCriacaoDto usuarioCriacaoDto) {
        Oficina usuario = new Oficina();
        usuario.setRazaoSocial(usuarioCriacaoDto.getRazaoSocial());
        usuario.setCnpj(usuarioCriacaoDto.getCnpj());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setDataCriacao(usuarioCriacaoDto.getDt_criacao());
        usuario.setStatus(usuarioCriacaoDto.getStatus());
        return usuario;
    }

    public static Oficina of(UsuarioLoginDto usuarioLoginDto) {
        Oficina usuario = new Oficina();

        usuario.setEmail(usuarioLoginDto.getEmail());

        return usuario;
    }

    public static UsuarioTokenDto of(Funcionario funcionario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setIdOficina(funcionario.getFkOficina().getIdOficina());
        usuarioTokenDto.setCnpj(funcionario.getFkOficina().getCnpj());
        usuarioTokenDto.setNome(funcionario.getNome());
        usuarioTokenDto.setCargo(funcionario.getCargo());
        usuarioTokenDto.setEmail(funcionario.getEmail());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }

    public static UsuarioListarDto of(Oficina beneficiario) {
        UsuarioListarDto usuarioListarDto = new UsuarioListarDto();

        usuarioListarDto.setIdCliente(beneficiario.getIdOficina());
        usuarioListarDto.setCnpj(beneficiario.getCnpj());
        usuarioListarDto.setNome(beneficiario.getRazaoSocial());

        return usuarioListarDto;
    }
}

