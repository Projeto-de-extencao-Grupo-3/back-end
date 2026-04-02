package geo.track.gestao.util;

import geo.track.dto.clientes.request.RequestPutCliente;
import geo.track.gestao.entity.Cliente;
import geo.track.gestao.entity.Endereco;
import geo.track.gestao.entity.Oficina;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.dto.clientes.response.ClienteVeiculoResponse;
import geo.track.dto.veiculos.response.VeiculoResponse;

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
        response.setTelefone(entity.getTelefone());
        response.setEmail(entity.getEmail());
        response.setTipoCliente(entity.getTipoCliente());

        if (entity.getFkOficina() != null) {
            response.setIdOficina(entity.getFkOficina().getIdOficina());
        }

        if (entity.getFkEndereco() != null) {
            response.setIdEndereco(entity.getFkEndereco().getIdEndereco());
        }

        return response;
    }

    public static ClienteVeiculoResponse toResponseVeiculo(Cliente cliente, String placa) {
        if (cliente == null) {
            return null;
        }

        VeiculoResponse veiculoResponse = VeiculoMapper.toResponse(cliente.getVeiculo().stream().filter(v -> v.getPlaca().equals(placa)).findFirst().get());

        return new ClienteVeiculoResponse(cliente.getIdCliente(), cliente.getNome(), cliente.getCpfCnpj(), cliente.getTelefone(), cliente.getEmail(), cliente.getTipoCliente(), veiculoResponse);
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
        return new Cliente(null, dto.getNome(), dto.getCpfCnpj(), dto.getTelefone(), dto.getEmail(), dto.getTipoCliente() , oficina, endereco);
    }

    public static Cliente updateEntity(Cliente cliente, RequestPutCliente body) {
        if (cliente == null || body == null) {
            return cliente;
        }

        if (body.getNome() != null ) {
            cliente.setNome(body.getNome());
        }
        if (body.getCpfCnpj() != null) {
            cliente.setCpfCnpj(body.getCpfCnpj());
        }
        if (body.getTelefone() != null) {
            cliente.setTelefone(body.getTelefone());
        }
        if (body.getEmail() != null) {
            cliente.setEmail(body.getEmail());
        }
        if (body.getTipoCliente() != null) {
            cliente.setTipoCliente(body.getTipoCliente().name());
        }

        return cliente;
    }
}
