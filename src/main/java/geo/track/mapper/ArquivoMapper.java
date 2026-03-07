package geo.track.mapper;

import geo.track.domain.Arquivo;
import geo.track.domain.Cliente;
import geo.track.domain.Endereco;
import geo.track.domain.Oficinas;
import geo.track.dto.arquivos.ArquivoResponse;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.dto.clientes.response.ClienteResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ArquivoMapper {
    public static ArquivoResponse toResponse(Arquivo entity) {
        return new ArquivoResponse(entity.getId(), entity.getNome(), entity.getFormato().toString(), entity.getUrl(), entity.getDataCriacao().toString(), entity.getDataAtualizacao().toString());
    }
}
