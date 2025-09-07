package geo.track.util;

import geo.track.domain.Enderecos;
import geo.track.request.viacep.ViacepDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ViacepConnection {
    public Enderecos consultarCEP(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ViacepDTO> res = restTemplate.getForEntity("https://viacep.com.br/ws/" + cep + "/json/", ViacepDTO.class);

        ViacepDTO response_viacep = res.getBody();

        if (response_viacep.getErro() != null) {
            return null;
        }
        Enderecos enderecos = new Enderecos(response_viacep.getLogradouro(), response_viacep.getBairro(), response_viacep.getLocalidade(), response_viacep.getUf());

        return enderecos;
    }
}
