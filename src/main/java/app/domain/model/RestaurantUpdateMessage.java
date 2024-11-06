package app.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantUpdateMessage {

    private int identifier;

    private String name;

    private String logo;

    public static RestaurantUpdateMessage fromRestaurantDto(RestaurantDto restaurantDto) {
        return RestaurantUpdateMessage.builder()
                .identifier(restaurantDto.getIdentifier())
                .name(restaurantDto.getName())
                .logo(restaurantDto.getMedia() != null ? restaurantDto.getMedia().getLogo() : null)
                .build();
    }
}