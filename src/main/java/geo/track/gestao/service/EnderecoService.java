package geo.track.gestao.service;

import geo.track.gestao.entity.Endereco;
import geo.track.dto.enderecos.request.RequestPostEndereco;
import geo.track.gestao.service.endereco.AlterarComplementoEnderecoUseCase;
import geo.track.gestao.service.endereco.AlterarNumeroEnderecoUseCase;
import geo.track.gestao.service.endereco.AtualizarEnderecoUseCase;
import geo.track.gestao.service.endereco.CadastrarEnderecoUseCase;
import geo.track.gestao.service.endereco.CriarEnderecoVazioUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.NotAcepptableException;
import geo.track.infraestructure.exception.constraint.message.EnderecoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.entity.repository.EnderecoRepository;
import geo.track.dto.enderecos.request.RequestPatchComplemento;
import geo.track.dto.enderecos.request.RequestPatchNumero;
import geo.track.dto.enderecos.request.RequestPutEndereco;
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

    public Endereco findEnderecoById(Integer id) {
        log.info("Buscando endereco com ID: {}", id);
        Optional<Endereco> endereco = ENDERECO_REPOSITORY.findById(id);

        if (endereco.isEmpty()) {
            log.error("Endereco com ID {} nao encontrado", id);
            throw new DataNotFoundException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, Domains.ENDERECO);
        }

        log.info("Endereco encontrado com sucesso para o ID: {}", id);
        return endereco.get();
    }

    public ResponseViacep findEnderecoByVIACEP(String cep) {
        log.info("Iniciando consulta de CEP via integracao: {}", cep);
        if (cep.length() != 8) {
            log.warn("Tentativa de consulta com CEP invalido: {}", cep);
            throw new NotAcepptableException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, Domains.ENDERECO);
        }

        ResponseViacep response = VIACEP_CONNECTION.consultarCEP(cep);
        if (response == null || response.getCep() == null) {
            log.error("CEP {} nao encontrado na base do ViaCEP", cep);
            throw new DataNotFoundException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, Domains.ENDERECO);
        }
        log.info("CEP {} consultado com sucesso", cep);
        return response;
    }

}
