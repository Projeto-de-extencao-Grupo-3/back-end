package geo.track.gestao.cliente.domain.endereco;

import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Endereco;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.EnderecoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.cliente.infraestructure.persistence.EnderecoRepository;
import geo.track.externo.viacep.response.ResponseViacep;
import geo.track.externo.viacep.ViacepConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final ViacepConnection VIACEP_CONNECTION;
    private final EnderecoRepository ENDERECO_REPOSITORY;
    private final Log log;

    public Endereco buscarEnderecoPorId(Integer idCliente, Integer idEndereco) {
        log.info("Buscando endereco com ID: {}", idEndereco);
        Optional<Endereco> endereco = ENDERECO_REPOSITORY.findByIdEnderecoAndFkCliente_IdCliente(idEndereco, idCliente);

        if (endereco.isEmpty()) {
            log.error("Endereco com ID {} nao encontrado", idEndereco);
            throw new DataNotFoundException(EnderecoExceptionMessages.ENDERECO_NAO_ENCONTRADO, Domains.ENDERECO);
        }

        log.info("Endereco encontrado com sucesso para o ID: {}", idEndereco);
        return endereco.get();
    }

    public Boolean existeEnderecoPorCep(String cep) {
        return ENDERECO_REPOSITORY.existsByCep(cep);
    }

    public Boolean existeEnderecoPorCepENumero(String cep, Integer numero) {
        return ENDERECO_REPOSITORY.existsByCepAndNumero(cep, numero);
    }

    public ResponseViacep buscarEnderecoPorCep(String cep) {
        log.info("Iniciando consulta de CEP via integracao: {}", cep);

        ResponseViacep response = VIACEP_CONNECTION.consultarCEP(cep);
        if (response == null || response.getCep() == null) {
            log.error("CEP {} nao encontrado na base do ViaCEP", cep);
            throw new DataNotFoundException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, Domains.ENDERECO);
        }
        log.info("CEP {} consultado com sucesso", cep);
        return response;
    }

}
