package com.demoproject.demo.config;

import com.demoproject.demo.entities.*;
import com.demoproject.demo.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("dev") // si attiva SOLO se avvii Spring Boot con --spring.profiles.active=dev
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepo,
            ProductRepository productRepo,
            OrderRepository orderRepo
    ) {
        return args -> {

            System.out.println("💾 Popolamento database DEMO...");

            //cancello dati prima di ricreali cosi da non aver duplicati ed errori
            orderRepo.deleteAll();   // per primo!
            productRepo.deleteAll();
            userRepo.deleteAll();


            // ---------- USERS ----------
            User user1 = new User();
            user1.setName("Mario");
            user1.setSurname("Rossi");
            user1.setEmail("mario.rossi@example.com");
            userRepo.save(user1);

            User user2 = new User();
            user2.setName("Giulia");
            user2.setSurname("Bianchi");
            user2.setEmail("giulia.bianchi@example.com");
            userRepo.save(user2);


            // ---------- PRODUCTS ----------
            Product p1 = new Product();
            p1.setName("Laptop");
            p1.setPrice(1200.00);
            p1.setStock(10);
            productRepo.save(p1);

            Product p2 = new Product();
            p2.setName("Smartphone");
            p2.setPrice(800.00);
            p2.setStock(15);
            productRepo.save(p2);

            Product p3 = new Product();
            p3.setName("Mouse");
            p3.setPrice(25.00);
            p3.setStock(50);
            productRepo.save(p3);



            // ---------- ORDER ----------
            Order order = new Order();
            order.setUser(user1);

            OrderItem item1 = new OrderItem();
            item1.setOrder(order);
            item1.setProduct(p1);
            item1.setQuantity(1);

            OrderItem item2 = new OrderItem();
            item2.setOrder(order);
            item2.setProduct(p3);
            item2.setQuantity(2);

            List<OrderItem> items = List.of(item1, item2);
            order.setItems(items);

            // CALCOLO TOTAL
            double total = items.stream()
                    .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                    .sum();

            order.setTotal(total);

            orderRepo.save(order);


            System.out.println("✅ Database popolato!");
        };
    }
}
