package app.domain.model.kafka;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class RestaurantUpdateMessage {
    @JsonProperty("identifier")
    private int identifier;

    @JsonProperty("name")
    private String name;

    @JsonProperty("logo")
    private String logo;
}