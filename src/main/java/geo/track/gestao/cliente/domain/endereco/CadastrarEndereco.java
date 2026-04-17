package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.gestao.cliente.domain.ClienteService;
import geo.track.gestao.cliente.application.endereco.CadastrarEnderecoUseCase;
import geo.track.gestao.cliente.infraestructure.EnderecoMapper;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPostEndereco;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.EnderecoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarEndereco implements CadastrarEnderecoUseCase {
    private final EnderecoRepository ENDERECO_REPOSITORY;
    private final EnderecoService ENDERECO_SERVICE;
    private final ClienteService CLIENTE_SERVICE;
    private final Log log;

    public Endereco execute(RequestPostEndereco body, Integer fkCliente) {
        if (ENDERECO_SERVICE.existeEnderecoPorCep(body.getCep())) {
            log.warn("Falha ao criar cliente: Endereço com CEP {} já cadastrado", body.getCep());
            throw new ConflictException(EnderecoExceptionMessages.ENDERECO_JA_EXISTENTE, Domains.ENDERECO);
        }

        Cliente cliente = CLIENTE_SERVICE.buscarClientePorId(fkCliente);

        log.info("Cadastrando novo endereco para o CEP: {}", body.getCep());
        Endereco endereco = EnderecoMapper.RequestToEndereco(body, cliente);

        Endereco salvo = ENDERECO_REPOSITORY.save(endereco);
        log.info("Endereco cadastrado com sucesso. ID gerado: {}", salvo.getIdEndereco());
        return salvo;
    }
}

