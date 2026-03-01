package geo.track.service;

import geo.track.domain.Endereco;
import geo.track.dto.enderecos.request.RequestPostEndereco;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.NotAcepptableException;
import geo.track.exception.constraint.message.EnderecoExceptionMessages;
import geo.track.exception.constraint.message.EnumDomains;
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

    public Endereco findEnderecoById(Integer id) {
        Optional<Endereco> endereco = ENDERECO_REPOSITORY.findById(id);

        if (endereco.isEmpty()) {
            throw new DataNotFoundException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, EnumDomains.ENDERECO);
        }

        return endereco.get();
    }

    public ResponseViacep findEnderecoByVIACEP(String cep) {
        if (cep.length() != 8) {
            throw new NotAcepptableException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, EnumDomains.ENDERECO);
        }

        ResponseViacep response = VIACEP_CONNECTION.consultarCEP(cep);
        if (response == null || response.getCep() == null) { // Assuming consultCEP returns null or a response with null cep if not found
            throw new DataNotFoundException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, EnumDomains.ENDERECO);
        }
        return response;
    }

    public Endereco postEndereco(RequestPostEndereco dtoEndereco) {
        Endereco endereco = EnderecosMapper.RequestToEndereco(dtoEndereco);

        if (endereco.getCep().length() != 8) {
            throw new NotAcepptableException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, EnumDomains.ENDERECO);
        }

        return ENDERECO_REPOSITORY.save(endereco);
    }

    public Endereco patchComplementoEndereco(RequestPatchComplemento enderecoDTO) {
        Optional<Endereco> enderecos = ENDERECO_REPOSITORY.findById(enderecoDTO.getId());

        if (enderecos.isEmpty()) {
            throw new DataNotFoundException(EnderecoExceptionMessages.ENDERECO_NAO_ENCONTRADO, EnumDomains.ENDERECO);
        }

        Endereco endereco = enderecos.get();
        endereco.setComplemento(enderecoDTO.getComplemento());

        return ENDERECO_REPOSITORY.save(endereco);
    }

    public Endereco patchNumeroEndereco(RequestPatchNumero enderecoDTO) {
        Optional<Endereco> enderecos = ENDERECO_REPOSITORY.findById(enderecoDTO.getId());

        if (enderecos.isPresent()) {
            Endereco endereco = enderecos.get();

            endereco.setNumero(enderecoDTO.getNumero());

            ENDERECO_REPOSITORY.save(endereco);

            return endereco;
        } else {
            throw new DataNotFoundException(EnderecoExceptionMessages.ENDERECO_NAO_ENCONTRADO, EnumDomains.ENDERECO);
        }
    }

    public Endereco putEndereco(RequestPutEndereco enderecoDTO) {

        if (enderecoDTO.getCep().length() != 8) {
             throw new NotAcepptableException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, EnumDomains.ENDERECO);
        }

        Optional<Endereco> enderecos = ENDERECO_REPOSITORY.findById(enderecoDTO.getId());

        if (enderecos.isPresent()) {
            Endereco endereco = enderecos.get();

            endereco.setCep(enderecoDTO.getCep());
            endereco.setLogradouro(enderecoDTO.getLogradouro());
            endereco.setNumero(enderecoDTO.getNumero());
            endereco.setComplemento(enderecoDTO.getComplemento());
            endereco.setBairro(enderecoDTO.getBairro());
            endereco.setCidade(enderecoDTO.getCidade());
            endereco.setEstado(enderecoDTO.getEstado());

            ENDERECO_REPOSITORY.save(endereco);

            return endereco;
        } else {
            throw new DataNotFoundException(EnderecoExceptionMessages.ENDERECO_NAO_ENCONTRADO, EnumDomains.ENDERECO);
        }
    }

}
