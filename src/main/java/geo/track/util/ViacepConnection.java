package geo.track.util;

import geo.track.exception.DataNotFoundException;
import geo.track.dto.viacep.request.RequestViacep;
import geo.track.dto.viacep.response.ResponseViacep;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ViacepConnection {
    public ResponseViacep consultarCEP(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RequestViacep> res = restTemplate.getForEntity("https://viacep.com.br/ws/" + cep + "/json/", RequestViacep.class);

        RequestViacep viacep = res.getBody();

        System.out.println(viacep);

        if (viacep.getErro() != null) {
            throw new DataNotFoundException("CEP: %s não foi encontrado".formatted(cep), "Endereços");
        }

        ResponseViacep responseViacep = new ResponseViacep(viacep.getCep() ,viacep.getLogradouro(), viacep.getBairro(), viacep.getLocalidade(), viacep.getUf());

        return responseViacep;
    }
}
