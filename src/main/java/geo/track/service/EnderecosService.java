package geo.track.service;

import geo.track.domain.Enderecos;
import geo.track.repository.EnderecosRepository;
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

    public ResponseEntity<Enderecos> findEnderecoByVIACEP(String cep) {
        if (cep.length() > 8 || cep.length() < 7) {
            return ResponseEntity.status(400).body(null);
        }

        Enderecos endereco = viacepConnection.consultarCEP(cep);

        if (endereco == null) {
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.status(200).body(endereco);
    }

    public ResponseEntity<Enderecos> postEndereco(Enderecos endereco) {



        return ResponseEntity.status(201).body(enderecosRepository.save(endereco));
    }

}
