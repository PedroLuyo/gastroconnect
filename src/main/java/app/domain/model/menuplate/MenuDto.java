package app.domain.model.menuplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "menus")
@TypeAlias("menu")
public class MenuDto {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    @Field("identifier")
    @JsonProperty("identificador")
    private int identifier;

    @Field("restaurant_identifier")
    @JsonProperty("identificadorRestaurante")
    private int restaurantIdentifier;

    @Field("restaurant_name")
    @JsonProperty("nombreRestaurante")
    private String restaurantname;

    @Field("name")
    @JsonProperty("nombre")
    private String name;

    @Field("description")
    @JsonProperty("descripcion")
    private String description;

    @Field("price")
    @JsonProperty("precio")
    private Double price;

    @Field("menu_items_identifier")
    @JsonProperty("identificadoresItemsMenu")
    private List<Integer> menuItemsIdentifier;

    @Field("availability")
    @JsonProperty("disponibilidad")
    private Availability availability;

    @Field("status")
    @JsonProperty("estado")
    private boolean status;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Availability {
        @Field("days")
        @JsonProperty("dias")
        private List<String> days;
    }
}
