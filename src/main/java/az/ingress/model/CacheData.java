package az.ingress.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CacheData implements Serializable {
    private static final Long serialVersionUID = 1L;
    private String name;
}
