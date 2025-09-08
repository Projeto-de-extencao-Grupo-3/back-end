package geo.track.service;

import geo.track.domain.Enderecos;
import geo.track.repository.EnderecosRepository;
import geo.track.request.enderecos.RequestPatchComplemento;
import geo.track.request.enderecos.RequestPatchNumero;
import geo.track.request.enderecos.RequestPutEndereco;
import geo.track.request.viacep.ResponseViacep;
import geo.track.util.ViacepConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecosService {
    final ViacepConnection viacepConnection = new ViacepConnection();
    final EnderecosRepository enderecosRepository;

    public ResponseEntity<Enderecos> findEnderecoById(Integer id) {
        Optional<Enderecos> endereco = enderecosRepository.findById(id);

        if (endereco.isPresent()) {
            return ResponseEntity.status(200).body(endereco.get());
        }
        return ResponseEntity.status(404).body(null);
    }

    public ResponseEntity<ResponseViacep> findEnderecoByVIACEP(String cep) {
        if (cep.length() > 8 || cep.length() < 7) {
            return ResponseEntity.status(400).body(null);
        }

        Optional<ResponseViacep> endereco = viacepConnection.consultarCEP(cep);

        if (endereco.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.status(200).body(endereco.get());
    }

    public ResponseEntity<Enderecos> postEndereco(Enderecos endereco) {
        return ResponseEntity.status(201).body(enderecosRepository.save(endereco));
    }

    public ResponseEntity<Enderecos> patchComplementoEndereco(RequestPatchComplemento enderecoDTO) {
        Optional<Enderecos> enderecos = enderecosRepository.findById(enderecoDTO.getId());

        if (enderecos.isPresent()) {
            Enderecos endereco = enderecos.get();

            endereco.setComplemento(enderecoDTO.getComplemento());

            enderecosRepository.save(endereco);

            return ResponseEntity.status(200).body(endereco);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<Enderecos> patchNumeroEndereco(RequestPatchNumero enderecoDTO) {
        Optional<Enderecos> enderecos = enderecosRepository.findById(enderecoDTO.getId());

        if (enderecos.isPresent()) {
            Enderecos endereco = enderecos.get();

            endereco.setNumero(enderecoDTO.getNumero());

            enderecosRepository.save(endereco);

            return ResponseEntity.status(200).body(endereco);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<Enderecos> putEndereco(RequestPutEndereco enderecoDTO) {
        Optional<Enderecos> enderecos = enderecosRepository.findById(enderecoDTO.getId());

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

            return ResponseEntity.status(200).body(endereco);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

}
