package geo.track.service;

import geo.track.domain.Enderecos;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.NotAcepptableException;
import geo.track.repository.EnderecosRepository;
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
public class EnderecosService {
    final ViacepConnection viacepConnection = new ViacepConnection();
    final EnderecosRepository enderecosRepository;

    public Enderecos findEnderecoById(Integer id) {
        Optional<Enderecos> endereco = enderecosRepository.findById(id);

        if (endereco.isEmpty()) {
            throw new DataNotFoundException("ID %d não foi encontrado".formatted(id), "Endereços");
        }

        return endereco.get();
    }

    public ResponseViacep findEnderecoByVIACEP(String cep) {
        if (cep.length() != 8) {
            throw new NotAcepptableException("Envie um CEP que possua 8 caracteres", "Endereços");
        }

        return viacepConnection.consultarCEP(cep);
    }

    public Enderecos postEndereco(Enderecos endereco) {
        if (endereco.getCep().length() != 8) {
            throw new NotAcepptableException("Envie um CEP que possua 8 caracteres", "Endereços");
        }

        return enderecosRepository.save(endereco);
    }

    public Enderecos patchComplementoEndereco(RequestPatchComplemento enderecoDTO) {
        Optional<Enderecos> enderecos = enderecosRepository.findById(enderecoDTO.getId());

        if (enderecos.isEmpty()) {
            return null;
        }

        Enderecos endereco = enderecos.get();
        endereco.setComplemento(enderecoDTO.getComplemento());

        return enderecosRepository.save(endereco);
    }

    public Enderecos patchNumeroEndereco(RequestPatchNumero enderecoDTO) {
        Optional<Enderecos> enderecos = enderecosRepository.findById(enderecoDTO.getId());

        if (enderecos.isPresent()) {
            Enderecos endereco = enderecos.get();

            endereco.setNumero(enderecoDTO.getNumero());

            enderecosRepository.save(endereco);

            return endereco;
        } else {
            return null;
        }
    }

    public Enderecos putEndereco(RequestPutEndereco enderecoDTO) {
        Optional<Enderecos> enderecos = enderecosRepository.findById(enderecoDTO.getId());

        if (enderecoDTO.getCep().length() != 8) {
             throw new NotAcepptableException("Envie um CEP que possua 8 caracteres", "Endereços");
        }

        if (enderecos.isPresent()) {
            Enderecos endereco = enderecos.get();

            endereco.setCep(enderecoDTO.getCep());
            endereco.setLogradouro(enderecoDTO.getLogradouro());
            endereco.setNumero(enderecoDTO.getNumero());
            endereco.setComplemento(enderecoDTO.getComplemento());
            endereco.setBairro(enderecoDTO.getBairro());
            endereco.setCidade(enderecoDTO.getCidade());
            endereco.setEstado(enderecoDTO.getEstado());

            enderecosRepository.save(endereco);

            return endereco;
        } else {
            return null;
        }
    }

}
