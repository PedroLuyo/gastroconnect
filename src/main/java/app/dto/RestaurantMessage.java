package app.dto;

import lombok.Data;

@Data
public class RestaurantMessage {
    private Long identifier;
    private String email;
    private String name;
    private String businessName;
    private String businessType;
    private String address;
    private Long phone;
    private String logo;
}
