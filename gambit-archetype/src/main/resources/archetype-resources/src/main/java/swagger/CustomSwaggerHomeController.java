#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.swagger;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2Controller;

/**
 * Gambit's gateway service has a strip-prefix: false configuration. This allows us to
 * call endpoints from Zuul such as http://localhost:10000/users instead of /users/users.
 * 
 * This is needed since for example in our user-service we have three different kinds of
 * paths: /users, /trainees, /trainers.
 * 
 * However, this configuration breaks Swagger docs locations, so we can't access -> 
 * http://localhost:10000/users/v2/api-docs.
 * 
 * This controller overrides the Swagger2Controller behavior so the home of the docs
 * changes to our specified parameter -> SWAGGER_HOME + /v2/api-docs.
 * 
 * After making this change, Gambit's gateway service wil be able to display all API
 * documentations for this particular service since the path is now accessible.
 * 
 * @author Peter Alagna
 *
 */
@Controller
@ApiIgnore
@RequestMapping(value = CustomSwaggerHomeController.SWAGGER_HOME)
public class CustomSwaggerHomeController extends Swagger2Controller {
	
	/**
	 * Change this to match the service name
	 */
	public static final String SWAGGER_HOME = "/serviceName/documentation";
	
	public CustomSwaggerHomeController(Environment environment, DocumentationCache documentationCache,
			ServiceModelToSwagger2Mapper mapper, JsonSerializer jsonSerializer) {
		super(environment, documentationCache, mapper, jsonSerializer);
	}
}
