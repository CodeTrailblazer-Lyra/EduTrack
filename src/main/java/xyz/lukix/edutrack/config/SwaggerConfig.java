package xyz.lukix.edutrack.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EduTrack API")
                        .version("1.0")
                        .description("教育管理系统API文档")
                        .license(new License().name("MIT").url("http://localhost:8080")))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                        .parameters(getCommonParameters()));
    }

    private Map<String, Parameter> getCommonParameters() {
        Map<String, Parameter> parameters = new HashMap<>();
        
        // 添加通用参数
        parameters.put("page", new Parameter()
                .name("page")
                .in("query")
                .schema(new StringSchema()._default("0"))
                .description("页码"));
                
        parameters.put("size", new Parameter()
                .name("size")
                .in("query")
                .schema(new StringSchema()._default("20"))
                .description("每页大小"));
                
        return parameters;
    }
}