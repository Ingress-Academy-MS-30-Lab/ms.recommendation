package az.ingress.client.mock;

import az.ingress.client.ProductClient;
import az.ingress.model.client.response.ProductResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Profile("local")
@Component
public class MockProductClient implements ProductClient {

    @Override
    public List<ProductResponseDto> getTopProductsByCategory(Long categoryId) {
        return List.of(
                new ProductResponseDto(1L),
                new ProductResponseDto(2L),
                new ProductResponseDto(3L),
                new ProductResponseDto(4L),
                new ProductResponseDto(5L),
                new ProductResponseDto(6L),
                new ProductResponseDto(7L),
                new ProductResponseDto(8L),
                new ProductResponseDto(9L),
                new ProductResponseDto(10L)
        );
    }
}
