package az.ingress.model.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartEvent {
    private Long userId;
    private String category;
    private LocalDateTime createdAt;
}
