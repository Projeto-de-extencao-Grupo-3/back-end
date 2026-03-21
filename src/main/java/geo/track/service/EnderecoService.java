package geo.track.service;

import geo.track.entity.Endereco;
import geo.track.dto.enderecos.request.RequestPostEndereco;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.NotAcepptableException;
import geo.track.exception.constraint.message.EnderecoExceptionMessages;
import geo.track.exception.constraint.message.Domains;
import geo.track.log.Log;
import geo.track.mapper.EnderecosMapper;
import geo.track.repository.EnderecoRepository;
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
        log.info("Buscando endereço com ID: {}", id);
        Optional<Endereco> endereco = ENDERECO_REPOSITORY.findById(id);

        if (endereco.isEmpty()) {
            log.error("Endereço com ID {} não encontrado", id);
            throw new DataNotFoundException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, Domains.ENDERECO);
        }

        log.info("Endereço encontrado com sucesso para o ID: {}", id);
        return endereco.get();
    }

    public ResponseViacep findEnderecoByVIACEP(String cep) {
        log.info("Iniciando consulta de CEP via integração: {}", cep);
        if (cep.length() != 8) {
            log.warn("Tentativa de consulta com CEP inválido: {}", cep);
            throw new NotAcepptableException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, Domains.ENDERECO);
        }

        ResponseViacep response = VIACEP_CONNECTION.consultarCEP(cep);
        if (response == null || response.getCep() == null) { // Assuming consultCEP returns null or a response with null cep if not found
            log.error("CEP {} não encontrado na base do ViaCEP", cep);
            throw new DataNotFoundException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, Domains.ENDERECO);
        }
        log.info("CEP {} consultado com sucesso", cep);
        return response;
    }

    public Endereco postEndereco(RequestPostEndereco body) {
        log.info("Cadastrando novo endereço para o CEP: {}", body.getCep());
        Endereco endereco = EnderecosMapper.RequestToEndereco(body);

        if (endereco.getCep().length() != 8) {
            log.warn("Falha ao cadastrar: CEP {} possui formato inválido", endereco.getCep());
            throw new NotAcepptableException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, Domains.ENDERECO);
        }

        Endereco salvo = ENDERECO_REPOSITORY.save(endereco);
        log.info("Endereço cadastrado com sucesso. ID gerado: {}", salvo.getIdEndereco());
        return salvo;
    }

    public Endereco saveVazio() {
        Endereco e = new Endereco();
        e.setCep("00000000");
        e.setLogradouro("A PREENCHER");
        e.setNumero(0);
        e.setBairro("A PREENCHER");
        e.setCidade("A PREENCHER");
        e.setEstado("AA");
        e.setComplemento("-");

        return ENDERECO_REPOSITORY.save(e);
    }

    public Endereco patchComplementoEndereco(RequestPatchComplemento body) {
        log.info("Atualizando complemento do endereço ID: {}", body.getIdEndereco());
        Optional<Endereco> enderecos = ENDERECO_REPOSITORY.findById(body.getIdEndereco());

        if (enderecos.isEmpty()) {
            log.error("Falha ao atualizar complemento: ID {} não encontrado", body.getIdEndereco());
            throw new DataNotFoundException(EnderecoExceptionMessages.ENDERECO_NAO_ENCONTRADO, Domains.ENDERECO);
        }

        Endereco endereco = enderecos.get();
        endereco.setComplemento(body.getComplemento());

        Endereco salvo = ENDERECO_REPOSITORY.save(endereco);
        log.info("Complemento do endereço ID {} atualizado com sucesso", salvo.getIdEndereco());
        return salvo;
    }

    public Endereco patchNumeroEndereco(RequestPatchNumero body) {
        log.info("Atualizando número do endereço ID: {}", body.getId());
        Optional<Endereco> enderecos = ENDERECO_REPOSITORY.findById(body.getId());

        if (enderecos.isPresent()) {
            Endereco endereco = enderecos.get();

            endereco.setNumero(body.getNumero());

            ENDERECO_REPOSITORY.save(endereco);

            log.info("Número do endereço ID {} atualizado para: {}", endereco.getIdEndereco(), endereco.getNumero());
            return endereco;
        } else {
            log.error("Falha ao atualizar número: ID {} não encontrado", body.getId());
            throw new DataNotFoundException(EnderecoExceptionMessages.ENDERECO_NAO_ENCONTRADO, Domains.ENDERECO);
        }
    }

    public Endereco putEndereco(RequestPutEndereco body) {
        log.info("Atualização completa solicitada para o endereço ID: {}", body.getIdEndereco());
        if (body.getCep().length() != 8) {
             log.warn("Falha na atualização: CEP {} inválido", body.getCep());
             throw new NotAcepptableException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, Domains.ENDERECO);
        }

        Optional<Endereco> enderecos = ENDERECO_REPOSITORY.findById(body.getIdEndereco());

        if (enderecos.isPresent()) {
            Endereco endereco = enderecos.get();

            endereco.setCep(body.getCep());
            endereco.setLogradouro(body.getLogradouro());
            endereco.setNumero(body.getNumero());
            endereco.setComplemento(body.getComplemento());
            endereco.setBairro(body.getBairro());
            endereco.setCidade(body.getCidade());
            endereco.setEstado(body.getEstado());

            ENDERECO_REPOSITORY.save(endereco);

            log.info("Endereço ID {} atualizado completamente com sucesso", endereco.getIdEndereco());
            return endereco;
        } else {
            log.error("Falha na atualização completa: ID {} não encontrado", body.getIdEndereco());
            throw new DataNotFoundException(EnderecoExceptionMessages.ENDERECO_NAO_ENCONTRADO, Domains.ENDERECO);
        }
    }

}
