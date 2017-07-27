package top.rainj2013;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-15
 */
@SpringBootApplication
@Controller
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:/swagger-ui.html#/";
    }
}
