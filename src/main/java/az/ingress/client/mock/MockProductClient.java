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
    public List<ProductResponseDto> getTopProductsByCategory(String category) {
        return List.of(
                new ProductResponseDto(1L, 101L, 11L, category, "https://example.com/img1.jpg", "iPhone 15 Pro", "Latest iPhone model", new BigDecimal("1999.99"), new BigDecimal("1799.99"), true, 4.8, 2400L),
                new ProductResponseDto(2L, 102L, 11L, category, "https://example.com/img2.jpg", "Samsung Galaxy S24", "Flagship Android phone", new BigDecimal("1699.99"), new BigDecimal("1599.99"), true, 4.7, 1850L),
                new ProductResponseDto(3L, 103L, 11L, category, "https://example.com/img3.jpg", "Google Pixel 9", "Best Android camera", new BigDecimal("1499.99"), null, false, 4.6, 1320L),
                new ProductResponseDto(4L, 104L, 11L, category, "https://example.com/img4.jpg", "Xiaomi 14 Ultra", "Performance-focused smartphone", new BigDecimal("999.99"), new BigDecimal("899.99"), true, 4.5, 980L),
                new ProductResponseDto(5L, 105L, 11L, category, "https://example.com/img5.jpg", "OnePlus 13", "Balanced performance and price", new BigDecimal("1099.99"), null, false, 4.4, 870L),
                new ProductResponseDto(6L, 106L, 11L, category, "https://example.com/img6.jpg", "Huawei P70 Pro", "High-end camera and battery", new BigDecimal("1299.99"), new BigDecimal("1199.99"), true, 4.3, 920L),
                new ProductResponseDto(7L, 107L, 11L, category, "https://example.com/img7.jpg", "Asus ROG Phone 8", "Gaming smartphone", new BigDecimal("1399.99"), null, false, 4.8, 740L),
                new ProductResponseDto(8L, 108L, 11L, category, "https://example.com/img8.jpg", "Sony Xperia 1 VI", "Professional camera experience", new BigDecimal("1599.99"), new BigDecimal("1399.99"), true, 4.2, 650L),
                new ProductResponseDto(9L, 109L, 11L, category, "https://example.com/img9.jpg", "Nothing Phone 3", "Unique transparent design", new BigDecimal("899.99"), new BigDecimal("799.99"), true, 4.5, 560L),
                new ProductResponseDto(10L, 110L, 11L, category, "https://example.com/img10.jpg", "Realme GT 7 Pro", "Budget-friendly performance", new BigDecimal("699.99"), null, false, 4.1, 420L)
        );
    }

    @Override
    public List<ProductResponseDto> getMostRatedProducts() {
        return List.of(
                new ProductResponseDto(11L, 201L, 22L, "electronics", "https://example.com/img11.jpg", "MacBook Pro 16", "High-performance laptop", new BigDecimal("3499.99"), new BigDecimal("3299.99"), true, 4.9, 5200L),
                new ProductResponseDto(12L, 202L, 22L, "electronics", "https://example.com/img12.jpg", "Dell XPS 15", "Premium ultrabook", new BigDecimal("2899.99"), null, false, 4.8, 4800L),
                new ProductResponseDto(13L, 203L, 22L, "electronics", "https://example.com/img13.jpg", "HP Spectre x360", "Convertible laptop", new BigDecimal("2199.99"), new BigDecimal("1999.99"), true, 4.7, 4200L),
                new ProductResponseDto(14L, 204L, 33L, "home", "https://example.com/img14.jpg", "Dyson V15 Vacuum", "Cordless smart vacuum cleaner", new BigDecimal("899.99"), null, false, 4.8, 3900L),
                new ProductResponseDto(15L, 205L, 33L, "home", "https://example.com/img15.jpg", "iRobot Roomba J7+", "Automatic cleaning robot", new BigDecimal("1199.99"), new BigDecimal("999.99"), true, 4.7, 3400L),
                new ProductResponseDto(16L, 206L, 44L, "gaming", "https://example.com/img16.jpg", "PlayStation 5", "Next-gen gaming console", new BigDecimal("899.99"), null, false, 4.9, 8100L),
                new ProductResponseDto(17L, 207L, 44L, "gaming", "https://example.com/img17.jpg", "Xbox Series X", "High-end console for gamers", new BigDecimal("849.99"), new BigDecimal("799.99"), true, 4.8, 7700L),
                new ProductResponseDto(18L, 208L, 44L, "gaming", "https://example.com/img18.jpg", "Nintendo Switch OLED", "Portable hybrid console", new BigDecimal("699.99"), null, false, 4.7, 7100L),
                new ProductResponseDto(19L, 209L, 55L, "wearables", "https://example.com/img19.jpg", "Apple Watch Ultra 2", "Smartwatch for adventurers", new BigDecimal("1299.99"), null, false, 4.8, 5600L),
                new ProductResponseDto(20L, 210L, 55L, "wearables", "https://example.com/img20.jpg", "Samsung Galaxy Watch 7", "Advanced fitness tracking", new BigDecimal("899.99"), new BigDecimal("799.99"), true, 4.7, 4900L)
        );
    }
}
