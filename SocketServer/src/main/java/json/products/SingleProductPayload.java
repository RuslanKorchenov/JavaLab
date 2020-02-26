package json.products;

import json.AbstractPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingleProductPayload extends AbstractPayload {
    private Long id;
    private String name;
    private int price;
}
