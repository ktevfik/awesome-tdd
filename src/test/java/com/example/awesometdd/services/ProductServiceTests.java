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
import org.mockito.ArgumentCaptor;
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
                Arguments.of("code1", 5, 13, 65), // Removed duplicate Arguments.of
                Arguments.of("code2", 10, 15, 150)
        );
    }

    @Test
    public void it_should_create_product_with_5_items() {
        // given
        final int expectedId = 1;
        CreateProductRequest request = CreateProductRequest.builder()
                .productCode("code1")
                .amount(5)
                .unitPrice(13)
                .build();
        Integer expectedTotalPrice = request.getUnitPrice() * request.getAmount();

        Product savedProduct = Product.builder()
                .id(expectedId)
                .unitPrice(request.getUnitPrice())
                .amount(request.getAmount())
                .totalPrice(expectedTotalPrice)
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // when
        ProductDto productDto = productService.createProduct(request);

        // then
        ArgumentCaptor<Product> productCaptorForPay = ArgumentCaptor.forClass(Product.class);
        verify(paymentClient).pay(productCaptorForPay.capture());
        Product capturedProductForPay = productCaptorForPay.getValue();
        then(capturedProductForPay.getId()).isNull();
        then(capturedProductForPay.getUnitPrice()).isEqualTo(request.getUnitPrice());
        then(capturedProductForPay.getAmount()).isEqualTo(request.getAmount());
        then(capturedProductForPay.getTotalPrice()).isEqualTo(expectedTotalPrice);

        ArgumentCaptor<Product> productCaptorForSave = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptorForSave.capture());
        Product capturedProductForSave = productCaptorForSave.getValue();
        then(capturedProductForSave.getId()).isNull();
        then(capturedProductForSave.getUnitPrice()).isEqualTo(request.getUnitPrice());
        then(capturedProductForSave.getAmount()).isEqualTo(request.getAmount());
        then(capturedProductForSave.getTotalPrice()).isEqualTo(expectedTotalPrice);

        then(productDto).isNotNull();
        then(productDto.getId()).isEqualTo(expectedId);
        then(productDto.getUnitPrice()).isEqualTo(request.getUnitPrice());
        then(productDto.getAmount()).isEqualTo(request.getAmount());
        then(productDto.getTotalPrice()).isEqualTo(expectedTotalPrice);
    }

    @Test
    public void it_should_create_order_with_10_items() {
        // given
        final int expectedId = 2; // Using a different ID for variety
        CreateProductRequest request = CreateProductRequest.builder()
                .productCode("code2") // Corrected product code
                .amount(10)
                .unitPrice(15)
                .build();
        Integer expectedTotalPrice = request.getUnitPrice() * request.getAmount();

        Product savedProduct = Product.builder()
                .id(expectedId)
                .unitPrice(request.getUnitPrice())
                .amount(request.getAmount())
                .totalPrice(expectedTotalPrice)
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // when
        ProductDto productDto = productService.createProduct(request);

        // then
        ArgumentCaptor<Product> productCaptorForPay = ArgumentCaptor.forClass(Product.class);
        verify(paymentClient).pay(productCaptorForPay.capture());
        Product capturedProductForPay = productCaptorForPay.getValue();
        then(capturedProductForPay.getId()).isNull();
        then(capturedProductForPay.getUnitPrice()).isEqualTo(request.getUnitPrice());
        then(capturedProductForPay.getAmount()).isEqualTo(request.getAmount());
        then(capturedProductForPay.getTotalPrice()).isEqualTo(expectedTotalPrice);

        ArgumentCaptor<Product> productCaptorForSave = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptorForSave.capture());
        Product capturedProductForSave = productCaptorForSave.getValue();
        then(capturedProductForSave.getId()).isNull();
        then(capturedProductForSave.getUnitPrice()).isEqualTo(request.getUnitPrice());
        then(capturedProductForSave.getAmount()).isEqualTo(request.getAmount());
        then(capturedProductForSave.getTotalPrice()).isEqualTo(expectedTotalPrice);

        then(productDto).isNotNull();
        then(productDto.getId()).isEqualTo(expectedId);
        then(productDto.getUnitPrice()).isEqualTo(request.getUnitPrice());
        then(productDto.getAmount()).isEqualTo(request.getAmount());
        then(productDto.getTotalPrice()).isEqualTo(expectedTotalPrice);
    }

    @ParameterizedTest
    @MethodSource("product_requests")
    public void it_should_create_orders(String productCode, Integer amount, Integer unitPrice,
                                        Integer expectedTotalPrice) {
        //given
        final int expectedId = 3; // Using a different ID for variety
        CreateProductRequest request = CreateProductRequest.builder()
                .productCode(productCode)
                .unitPrice(unitPrice)
                .amount(amount)
                .build();

        Product savedProduct = Product.builder()
                .id(expectedId)
                .unitPrice(request.getUnitPrice())
                .amount(request.getAmount())
                .totalPrice(expectedTotalPrice)
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        //when
        ProductDto productDto = productService.createProduct(request);

        //then
        ArgumentCaptor<Product> productCaptorForPay = ArgumentCaptor.forClass(Product.class);
        verify(paymentClient).pay(productCaptorForPay.capture());
        Product capturedProductForPay = productCaptorForPay.getValue();
        then(capturedProductForPay.getId()).isNull();
        then(capturedProductForPay.getUnitPrice()).isEqualTo(request.getUnitPrice());
        then(capturedProductForPay.getAmount()).isEqualTo(request.getAmount());
        then(capturedProductForPay.getTotalPrice()).isEqualTo(expectedTotalPrice);

        ArgumentCaptor<Product> productCaptorForSave = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptorForSave.capture());
        Product capturedProductForSave = productCaptorForSave.getValue();
        then(capturedProductForSave.getId()).isNull();
        then(capturedProductForSave.getUnitPrice()).isEqualTo(request.getUnitPrice());
        then(capturedProductForSave.getAmount()).isEqualTo(request.getAmount());
        then(capturedProductForSave.getTotalPrice()).isEqualTo(expectedTotalPrice);

        then(productDto).isNotNull();
        then(productDto.getId()).isEqualTo(expectedId);
        then(productDto.getUnitPrice()).isEqualTo(request.getUnitPrice());
        then(productDto.getAmount()).isEqualTo(request.getAmount());
        then(productDto.getTotalPrice()).isEqualTo(expectedTotalPrice);
    }

    @Test
    public void it_should_fail_order_creation_when_payment_failed() {
        //given
        CreateProductRequest request = CreateProductRequest.builder()
                .productCode("code1")
                .unitPrice(12)
                .amount(3)
                .build();

        //when
        // Ensure Product class is used for type matching if service method expects Product
        doThrow(new IllegalArgumentException()).when(paymentClient).pay(any(Product.class));

        Throwable throwable = catchThrowable(() -> productService.createProduct(request));

        //then
        then(throwable).isInstanceOf(IllegalArgumentException.class);
        verifyNoInteractions(productRepository);
    }
}



/*ParameterizedTest bir test icinde farklı parametreler girip farklı cıktılar beklenilen test.
 * Yani logic aynı pattern aynı olduğu zaman bu testi uygulayabiliriz.*/


//verify1 = Yukarıda payment method fail ederse orderRepository'nin save methodunu hic cagırmasın demis oluyoruz.
/*doThrow1  Yukarıda payment methodun pay metodunu çağırınca exception fırlatacak.
void methodlarda doThrow ile bunu yaparız.*/