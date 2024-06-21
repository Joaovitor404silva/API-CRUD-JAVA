package com.me.desafio.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.me.desafio.model.Vendedor;
import com.me.desafio.service.VendedorService;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(VendedorService service) {

        return args -> {
            log.info("Preloading " + service.save(new Vendedor("Bilbo Baggins", "12345678901", "bilbo@middleearth.com","17-02-2000", "OUTSOURCING", "2")));
            log.info("Preloading " + service.save(new Vendedor("Frodo Baggins", "10987654321", "frodo@middleearth.com", "20-05-1995", "CLT", "1")));
        };
    }
}
