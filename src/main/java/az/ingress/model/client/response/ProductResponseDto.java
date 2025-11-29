package az.ingress.model.client.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto implements Serializable {
    @Serial
    private static final Long serialVersionUID = 1L;
    private Long productId;
}
