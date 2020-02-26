package json.products;

import json.AbstractPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsPayload extends AbstractPayload {
    private List<SingleProductPayload> products;
}
