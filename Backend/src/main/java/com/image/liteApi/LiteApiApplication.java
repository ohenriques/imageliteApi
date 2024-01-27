package com.image.liteApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LiteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiteApiApplication.class, args);
        System.out.println("Online....");
    }

    /**
     *
     * @param respository
     * @return
     * o @Bean está sendo utilizado para inserir um objeto já no inicio do projeto rodando
     * // TODO: 25/01/2024 REMOVER
     */

    /**
     @Bean public CommandLineRunner commandLineRunner(@Autowired ImageRespository respository) {
     return args -> {
     Image image = Image.builder()
     .extension(ImageExtension.PNG)
     .name("myImage")
     .tags("1Tag")
     .size(1000L)
     .build();

     respository.save(image);
     };
     }
     **/
}
