package az.ingress.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long productId;
    private Long variantId;
    private Long categoryId;
    private String categoryName;
    private String imageUrl;
    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal salePrice;
    private Boolean onSale;
    private Double rating;
    private Long reviewCount;
}
