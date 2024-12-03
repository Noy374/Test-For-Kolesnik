package org.svarian.testforkolesnik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TestForKolesnikApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestForKolesnikApplication.class , args);
    }

}
