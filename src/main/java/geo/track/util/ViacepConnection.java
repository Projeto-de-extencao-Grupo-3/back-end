package geo.track.util;

import geo.track.domain.Enderecos;
import geo.track.request.viacep.RequestViacep;
import geo.track.request.viacep.ResponseViacep;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class ViacepConnection {
    public Optional<ResponseViacep> consultarCEP(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RequestViacep> res = restTemplate.getForEntity("https://viacep.com.br/ws/" + cep + "/json/", RequestViacep.class);

        RequestViacep viacep = res.getBody();

        System.out.println(viacep);

        if (viacep.getErro() != null) {
            return Optional.empty();
        }

        ResponseViacep responseViacep = new ResponseViacep(viacep.getCep() ,viacep.getLogradouro(), viacep.getBairro(), viacep.getLocalidade(), viacep.getUf());

        return Optional.of(responseViacep);
    }
}
