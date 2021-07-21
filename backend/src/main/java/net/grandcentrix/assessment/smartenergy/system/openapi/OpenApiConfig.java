package net.grandcentrix.assessment.smartenergy.system.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * This configuration is used by the Swagger UI to create the "authorize" button with the two different basic
 * authentication types that can be used in the application.
 */
@OpenAPIDefinition(
        info = @Info(title = "Smart Energy API", version = "v1"),
        security = {
                @SecurityRequirement(name = OpenApiConfig.USER_AUTH),
                @SecurityRequirement(name = OpenApiConfig.DEVICE_AUTH)
        }
)
@SecurityScheme(
        name = OpenApiConfig.USER_AUTH,
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@SecurityScheme(
        name = OpenApiConfig.DEVICE_AUTH,
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class OpenApiConfig {

    public static final String USER_AUTH = "userAuth";
    public static final String DEVICE_AUTH = "deviceAuth";

}
