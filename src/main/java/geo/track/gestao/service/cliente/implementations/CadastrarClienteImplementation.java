package geo.track.gestao.service.cliente.implementations;

import geo.track.gestao.entity.Cliente;
import geo.track.gestao.entity.Endereco;
import geo.track.gestao.entity.Oficina;
import geo.track.gestao.entity.repository.ClienteRepository;
import geo.track.gestao.service.cliente.CadastrarClienteUseCase;
import geo.track.gestao.util.ClientesMapper;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.constraint.message.ClienteExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.service.EnderecoService;
import geo.track.gestao.service.OficinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarClienteImplementation implements CadastrarClienteUseCase {
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final OficinaService OFICINA_SERVICE;
    private final EnderecoService ENDERECO_SERVICE;
    private final Log log;

    public Cliente execute(RequestPostCliente body) {
        log.info("Iniciando criacao de novo cliente com CPF/CNPJ: {}", body.getCpfCnpj());
        if (CLIENTE_REPOSITORY.existsByCpfCnpj(body.getCpfCnpj())) {
            log.warn("Falha ao criar cliente: CPF/CNPJ {} ja cadastrado", body.getCpfCnpj());
            throw new ConflictException(ClienteExceptionMessages.CPF_EXISTENTE, Domains.CLIENTE);
        }
        Oficina oficina = OFICINA_SERVICE.findOficinasById(body.getFkOficina());
        Endereco endereco = ENDERECO_SERVICE.findEnderecoById(body.getFkEndereco());

        Cliente cliente = ClientesMapper.toEntity(body, oficina, endereco);
        log.info("Cliente criado com sucesso para a oficina ID: {}", body.getFkOficina());
        return CLIENTE_REPOSITORY.save(cliente);
    }
}

