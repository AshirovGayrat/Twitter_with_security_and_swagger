package uz.pdp.config.security;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        security = @SecurityRequirement(name = "bearerAuth"),
        info = @Info(
                title = "PDP G62 Post Project",
                version = "10",
                contact = @Contact(
                        name = "Ali Aliyev",
                        email = "john.lgd65@gmail.com",
                        url = "https://github.com/jlkesh"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://springdoc.org"),
                termsOfService = "http://swagger.io/terms/",
                description = "Spring 6 Swagger Simple Application"
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring 6 Wiki Documentation Twitter project doc",
                url = "https://springshop.wiki.github.org/docs"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Production-Server"
                ),
                @Server(
                        url = "http://localhost:9090",
                        description = "Test-Server"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi postGroupAPI() {
        return GroupedOpenApi.builder()
                .group("PostController")
                .pathsToMatch("/api/posts/**")
                .build();
    }

    @Bean
    public GroupedOpenApi commentGroupAPI() {
        return GroupedOpenApi.builder()
                .group("CommentController")
                .pathsToMatch("/api/comments/**")
                .build();
    }

    @Bean
    public GroupedOpenApi likeGroupAPI() {
        return GroupedOpenApi.builder()
                .group("LikeController")
                .pathsToMatch("/api/likes/**")
                .build();
    }


    @Bean
    public GroupedOpenApi homeGroupAPI() {
        return GroupedOpenApi.builder()
                .group("HomeController")
                .pathsToMatch("/api/home/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authGroupAPI() {
        return GroupedOpenApi.builder()
                .group("AuthController")
                .pathsToMatch("/api/auth/**")
                .build();
    }


}
