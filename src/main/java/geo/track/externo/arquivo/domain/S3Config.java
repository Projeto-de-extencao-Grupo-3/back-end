package geo.track.externo.arquivo.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${aws.s3.access-key}")
    private String accessKey;

    @Value("${aws.s3.secret-key}")
    private String secretKey;

    @Value("${aws.s3.session-token:}") // O ":" evita erro se a propriedade estiver vazia
    private String sessionToken;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.endpoint-url:}")
    private String endpoint;

    @Bean
    public S3Client s3Client() {
        // 1. Configuração de Credenciais (Funciona para ambos)
        StaticCredentialsProvider credentialsProvider;
        if (sessionToken != null && !sessionToken.isBlank()) {
            credentialsProvider = StaticCredentialsProvider.create(
                    AwsSessionCredentials.create(accessKey, secretKey, sessionToken)
            );
        } else {
            credentialsProvider = StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey)
            );
        }

        S3ClientBuilder builder = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider);

        // 2. Lógica de Redirecionamento (LocalStack vs AWS)
        if (endpoint != null && !endpoint.isBlank()) {
            // Se houver um endpoint definido (LocalStack), sobrepomos a URL padrão
            builder.endpointOverride(URI.create(endpoint))
                    .forcePathStyle(true);
        }
        // Se o endpoint estiver vazio, o SDK usa automaticamente a URL real da AWS S3

        return builder.build();
    }
}