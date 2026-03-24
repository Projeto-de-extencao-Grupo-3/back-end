package geo.track.external.service;

import geo.track.dto.plate.PlateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

// O name é um identificador interno, url aponta para o seu Flask
@FeignClient(name = "recognize-plate-client", url = "${plate.model.url}")
public interface RecognizePlate {

    @PostMapping(value = "/recognize", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    PlateResponse reconhecer(@RequestPart("file") MultipartFile file);
}