package geo.track.gestao.service.endereco.implementations;

import geo.track.gestao.entity.Endereco;
import geo.track.gestao.entity.repository.EnderecoRepository;
import geo.track.gestao.service.EnderecoService;
import geo.track.gestao.service.endereco.CadastrarEnderecoUseCase;
import geo.track.gestao.util.EnderecoMapper;
import geo.track.dto.enderecos.request.RequestPostEndereco;
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
    private final Log log;

    public Endereco execute(RequestPostEndereco body) {
        if (ENDERECO_SERVICE.existeEnderecoPorCep(body.getCep())) {
            log.warn("Falha ao criar cliente: Endereço com CEP {} já cadastrado", body.getCep());
            throw new ConflictException(EnderecoExceptionMessages.ENDERECO_JA_EXISTENTE, Domains.ENDERECO);
        }

        log.info("Cadastrando novo endereco para o CEP: {}", body.getCep());
        Endereco endereco = EnderecoMapper.RequestToEndereco(body);

        Endereco salvo = ENDERECO_REPOSITORY.save(endereco);
        log.info("Endereco cadastrado com sucesso. ID gerado: {}", salvo.getIdEndereco());
        return salvo;
    }
}

