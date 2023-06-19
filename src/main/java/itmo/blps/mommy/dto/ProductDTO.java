package itmo.blps.mommy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ProductDTO {

    private Integer id;

    @NonNull
    @JsonProperty(value = "name", required = true)
    private String name;

    private Float weight;

    private String consumerInfo;

}
