package app.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantCreateMessage {
    private int identifier;

    private String email;

    private String name;

    private String businessName;

    private String businessType;

    private String address;

    private Long phone;

    private String logo;

    public static RestaurantCreateMessage fromRestaurantDto(RestaurantDto restaurantDto) {
        return RestaurantCreateMessage.builder()
                .identifier(restaurantDto.getIdentifier())
                .email(restaurantDto.getContact().getEmail())
                .name(restaurantDto.getName())
                .businessName(restaurantDto.getBusinessInfo().getBusinessName())
                .businessType(restaurantDto.getBusinessInfo().getBusinessType())
                .address(restaurantDto.getLocation().getAddress())
                .phone(restaurantDto.getContact().getPhone())
                .logo(restaurantDto.getMedia() != null ? restaurantDto.getMedia().getLogo() : null)
                .build();
    }
}
