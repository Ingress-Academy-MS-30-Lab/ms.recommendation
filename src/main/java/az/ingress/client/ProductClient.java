package az.ingress.client;

import az.ingress.client.decoder.CustomErrorDecoder;
import az.ingress.model.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "ms.product",
        path = "internal",
        url = "${client.urls.ms-product}",
        configuration = CustomErrorDecoder.class
)
public interface ProductClient {

    @GetMapping("/v1/products/category")
    List<ProductResponse> getTopProductsByCategory(@RequestParam String category);

    @GetMapping("/v1/products/most-rated-products")
    List<ProductResponse> getMostRatedProducts();
}
