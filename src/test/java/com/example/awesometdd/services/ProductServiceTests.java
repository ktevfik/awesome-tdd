package com.example.awesometdd.services;

import com.example.awesometdd.models.Product;
import com.example.awesometdd.repositories.ProductRepository;
import com.example.awesometdd.dtos.ProductDto;
import com.example.awesometdd.clients.PaymentClient;
import com.example.awesometdd.services.requests.CreateProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 00:20
 */


@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService; //hangi classı test ediyorsak inject mock deriz, hangi classı kullanıyorsak da mock deriz.

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PaymentClient paymentClient;

    public static Stream<Arguments> product_requests() { /*MethodSource*/
        return Stream.of(
                Arguments.of("code1", 5, 13, 65,
                        Arguments.of("code2", 10, 15, 150)));
    }

    @Test
    public void it_should_create_product_with_5_items(){
        // given
        CreateProductRequest request = CreateProductRequest.builder()
                .productCode("code1")
                .amount(5)
                .unitPrice(13)
                .build();

        // when
        Product product = new Product();
        when(productRepository.save(any())).thenReturn(product);
        ProductDto productDto = productService.createProduct(request);

        // then
        then(productDto).isNotNull();   /*test 1*/
        then(productDto.getTotalPrice()).isEqualTo(65); /* test2*/

    }

    @Test
    public void it_should_create_order_with_10_items(){
        // given
        CreateProductRequest request = CreateProductRequest.builder()
                .productCode("code1")
                .amount(10)
                .unitPrice(15)
                .build();

        // when
        Product product = new Product();
        when(productRepository.save(any())).thenReturn(product);
        ProductDto productDto = productService.createProduct(request);

        // then
        then(productDto).isNotNull();   /*test 3*/
        then(productDto.getTotalPrice()).isEqualTo(150); /* test4*/

    }

    @ParameterizedTest
    @MethodSource("product_requests") //order_requests adında yukarıda olusturup argümanları yukarıda belirledik.
    public void it_should_create_orders(String productCode, Integer amount, Integer unitPrice,
                                        Integer totalPrice){ // buraya methodSource icerisinde olusturduğumuz argümanların
        // degiskenlerini geciyoruz.(adını)
        //given
        CreateProductRequest request = CreateProductRequest.builder()
                .productCode(productCode)
                .unitPrice(unitPrice)
                .amount(amount)
                .build();

        Product product = new Product();
        when(productRepository.save(any())).thenReturn(product);

        //when
        ProductDto productDto = productService.createProduct(request);  /*OrderDto order = local variable.*/

        //then
        then(productDto.getTotalPrice()).isEqualTo(totalPrice);
    }

    @Test
    public void it_should_fail_order_creation_when_payment_failed(){
        //given
        CreateProductRequest request = CreateProductRequest.builder()
                .productCode("code1")
                .unitPrice(12)
                .amount(3)
                .build();

        //when
        doThrow(new IllegalArgumentException()).when(paymentClient).pay(any()); /*doThrow1*/

        Throwable throwable = catchThrowable(() -> { productService.createProduct(request);}); /*burada cıktıyı alıp.*/

        //then
        then(throwable).isInstanceOf(IllegalArgumentException.class); // burada illegalexception alması gerek.
        verifyNoInteractions(productRepository); /*veriyf1*/
    }
}



/*ParameterizedTest bir test icinde farklı parametreler girip farklı cıktılar beklenilen test.
 * Yani logic aynı pattern aynı olduğu zaman bu testi uygulayabiliriz.*/


//verify1 = Yukarıda payment method fail ederse orderRepository'nin save methodunu hic cagırmasın demis oluyoruz.
/*doThrow1  Yukarıda payment methodun pay metodunu çağırınca exception fırlatacak.
void methodlarda doThrow ile bunu yaparız.*/