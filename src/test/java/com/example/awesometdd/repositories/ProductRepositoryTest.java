package com.example.awesometdd.repositories;

import com.example.awesometdd.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 13:13
 */
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Embedded database kullanmak istemiyoruz
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql");

    @Test
    public void it_should_find_orders(){

        //given
        Product product1 = Product.builder().totalPrice(10).build();
        Product product2 = Product.builder().totalPrice(2).build();

        Object id1 = this.testEntityManager.persistAndGetId(product1);
        Object id2 = this.testEntityManager.persistAndGetId(product2);
        this.testEntityManager.flush();

        //when
        List<Product> products = this.productRepository.findAll();

        //then
        then(products).isNotEmpty();
        Product p1 = products.get(0);
        Product p2 = products.get(1);
        then(p1.getId()).isEqualTo(id1);
        then(p2.getId()).isEqualTo(id2);
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name",mySQLContainer::getDriverClassName);

        /*Burada bunları ekleme sebebimiz, test containers static yani burada  mySQLContainer static oldugu icin
         * buraya test dosyasına ait bir sey koyup calıstıramayız cünkü test container sonradan geliyor.
         * Eklemek icin de su sekilde mySQLContainer ayağa kalkıyor  ayağa kalktıktan sonra DynamicPropertySource
         * altında yazdıklarımızı set ediyor.
         *Dolayısıyla testEntityManager cagırdığımız zaman gidip o datasource üzerinden geliyor. */
    }
}

// Bu test calıstıgında bu testin testcontainersa bağlandığını da DynamicPropertySource üzerinden yapıyoruz.