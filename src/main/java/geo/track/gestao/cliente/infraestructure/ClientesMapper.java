package geo.track.gestao.cliente.infraestructure;

import geo.track.gestao.cliente.infraestructure.request.cliente.RequestPutCliente;
import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.veiculo.infraestructure.VeiculoMapper;
import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.gestao.cliente.infraestructure.request.cliente.RequestPostCliente;
import geo.track.gestao.cliente.infraestructure.response.cliente.ClienteResponse;
import geo.track.gestao.cliente.infraestructure.response.cliente.ClienteVeiculoResponse;
import geo.track.gestao.veiculo.infraestructure.response.VeiculoResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClientesMapper {
    public static ClienteResponse toResponse(Cliente entity) {
        if (entity == null) {
            return null;
        }

        ClienteResponse response = new ClienteResponse();
        response.setIdCliente(entity.getIdCliente());
        response.setNome(entity.getNome());
        response.setCpfCnpj(entity.getCpfCnpj());
        response.setInscricaoEstadual(entity.getInscricaoEstadual());
        response.setTipoCliente(entity.getTipoCliente());

        if (entity.getFkOficina() != null) {
            response.setIdOficina(entity.getFkOficina().getIdOficina());
        }

        if (entity.getEnderecos() != null) {
            response.setEnderecos(entity.getEnderecos().stream().map(EnderecoMapper::toResponse).collect(Collectors.toList()));
        }

        if (entity.getContatos() != null) {
            response.setMeiosContato(entity.getContatos().stream().map(ContatoMapper::toResponse).collect(Collectors.toList()));
        }

        return response;
    }

    public static ClienteVeiculoResponse toResponseVeiculo(Cliente cliente, String placa) {
        if (cliente == null) {
            return null;
        }

        VeiculoResponse veiculoResponse = VeiculoMapper.toResponse(cliente.getVeiculos().stream().filter(v -> v.getPlaca().equals(placa)).findFirst().get());

        return new ClienteVeiculoResponse(cliente.getIdCliente(), cliente.getNome(), cliente.getCpfCnpj(), cliente.getTipoCliente(), veiculoResponse);
    }

    public static List<ClienteResponse> toResponse(List<Cliente> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(ClientesMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static Cliente toEntity(RequestPostCliente dto, Oficina oficina, Endereco endereco) {
        return new Cliente(null, dto.getNome(), dto.getCpfCnpj(), dto.getInscricaoEstadual(), dto.getTipoCliente(), oficina);
    }

    public static Cliente updateEntity(Cliente cliente, RequestPutCliente body) {
        if (cliente == null || body == null) {
            return cliente;
        }

        if (body.getNome() != null ) cliente.setNome(body.getNome());
        if (body.getInscricaoEstadual() != null) cliente.setInscricaoEstadual(body.getInscricaoEstadual());
        if (body.getCpfCnpj() != null) cliente.setCpfCnpj(body.getCpfCnpj());

        return cliente;
    }
}
