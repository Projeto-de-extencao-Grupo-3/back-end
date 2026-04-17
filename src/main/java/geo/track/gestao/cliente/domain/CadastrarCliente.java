package geo.track.gestao.cliente.domain;

import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.oficina.infraestructure.persistence.entity.Oficina;
import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.gestao.cliente.application.CadastrarClienteUseCase;
import geo.track.gestao.cliente.application.contato.CadastrarContatoUseCase;
import geo.track.gestao.cliente.application.endereco.CadastrarEnderecoUseCase;
import geo.track.gestao.cliente.infraestructure.ClientesMapper;
import geo.track.gestao.cliente.infraestructure.request.cliente.RequestPostCliente;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.constraint.message.ClienteExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.oficina.domain.OficinaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarCliente implements CadastrarClienteUseCase {
    private final ClienteService CLIENTE_SERVICE;
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final OficinaService OFICINA_SERVICE;
    private final CadastrarEnderecoUseCase CADASTRAR_ENDERECO_USE_CASE;
    private final CadastrarContatoUseCase CADASTRAR_CONTATO_USE_CASE;
    private final Log log;

    @Transactional
    public Cliente execute(RequestPostCliente body) {
        log.info("Iniciando criacao de novo cliente com CPF/CNPJ: {}", body.getCpfCnpj());


        if (CLIENTE_REPOSITORY.existsByCpfCnpjAndAtivoTrue(body.getCpfCnpj())) {
            log.warn("Falha ao criar cliente: CPF/CNPJ {} ja cadastrado", body.getCpfCnpj());
            throw new ConflictException(ClienteExceptionMessages.CPF_EXISTENTE, Domains.CLIENTE);
        }
        Oficina oficina = OFICINA_SERVICE.buscarOficinaPorId(body.getFkOficina());

        Cliente cliente = ClientesMapper.toEntity(body, oficina, null);
        cliente.setAtivo(true);

        Cliente finalCliente = CLIENTE_REPOSITORY.save(cliente);
        body.getContatos().forEach(contato -> CADASTRAR_CONTATO_USE_CASE.execute(finalCliente.getIdCliente(), contato));
        CADASTRAR_ENDERECO_USE_CASE.execute(body.getEndereco(), cliente.getIdCliente());

        log.info("Cliente criado com sucesso para a oficina ID: {}", body.getFkOficina());
        return CLIENTE_SERVICE.buscarClientePorId(finalCliente.getIdCliente());
    }
}
