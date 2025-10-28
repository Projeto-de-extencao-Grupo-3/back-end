package geo.track.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "GROTrack: Back-end",
                description = "API RESTful utilizada para gerenciar as entidades que envolvem o contexto da Geosmar Reformadora de Ônibus " +
                        "<br>Verificar token: https://www.jwt.io/" +
                        "<br>Manual JWT: https://moodle.sptech.school/mod/book/view.php?id=14233&chapterid=1585",
                contact = @Contact(
                        name = "GROTrack",
                        url = "https://github.com/Projeto-de-extencao-Grupo-3/back-end",
                        email = "lucas.marcolino@sptech.school"
                ),
                license = @License(name = "UNLICENSED"),
                version = "1.0.0"
        )
)
@SecurityScheme(
        name = "Bearer", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT"
)


public class OpenApiConfig {
}
