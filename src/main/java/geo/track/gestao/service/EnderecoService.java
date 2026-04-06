package geo.track.gestao.service;

import geo.track.gestao.entity.Endereco;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.EnderecoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.entity.repository.EnderecoRepository;
import geo.track.dto.viacep.response.ResponseViacep;
import geo.track.util.ViacepConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final ViacepConnection VIACEP_CONNECTION;
    private final EnderecoRepository ENDERECO_REPOSITORY;
    private final Log log;

    public Endereco buscarEnderecoPorId(Integer id) {
        log.info("Buscando endereco com ID: {}", id);
        Optional<Endereco> endereco = ENDERECO_REPOSITORY.findById(id);

        if (endereco.isEmpty()) {
            log.error("Endereco com ID {} nao encontrado", id);
            throw new DataNotFoundException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, Domains.ENDERECO);
        }

        log.info("Endereco encontrado com sucesso para o ID: {}", id);
        return endereco.get();
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
