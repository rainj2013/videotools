package top.rainj2013;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.rainj2013.utils.Constants;

@Configuration
@EnableSwagger2
public class Swagger2 {
    @Value("${host}")
    private String host;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.rainj2013.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("videoTools API")
                .description("一个视频下载/处理/播放程序")
                .version("1.0")
                .contact(new Contact(Constants.NAME, Constants.URL, Constants.EMAIL))
                .build();
    }
}
