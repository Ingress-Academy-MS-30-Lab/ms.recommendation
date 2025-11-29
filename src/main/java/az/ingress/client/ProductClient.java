package az.ingress.client;

import az.ingress.client.decoder.CustomErrorDecoder;
import az.ingress.model.client.response.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Profile("!local")
@FeignClient(
        name = "ms.product",
        path = "internal",
        url = "${client.urls.ms-product}",
        configuration = CustomErrorDecoder.class
)
public interface ProductClient {

    @GetMapping("/v1/products/recommendations")
    List<ProductResponseDto> getTopProductsByCategory(Long categoryId);
}
