package geo.track.util;

import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.dto.viacep.request.RequestViacep;
import geo.track.dto.viacep.response.ResponseViacep;
import geo.track.infraestructure.exception.constraint.message.EnderecoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViacepConnection {
    public ResponseViacep consultarCEP(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RequestViacep> res = restTemplate.getForEntity("https://viacep.com.br/ws/" + cep + "/json/", RequestViacep.class);

        RequestViacep viacep = res.getBody();


        if (viacep.getErro() != null) {
            throw new DataNotFoundException(EnderecoExceptionMessages.FORMATACAO_CEP_INCORRETA, Domains.ENDERECO);
        }

        ResponseViacep responseViacep = new ResponseViacep(viacep.getCep() ,viacep.getLogradouro(), viacep.getBairro(), viacep.getLocalidade(), viacep.getUf());

        return responseViacep;
    }
}
