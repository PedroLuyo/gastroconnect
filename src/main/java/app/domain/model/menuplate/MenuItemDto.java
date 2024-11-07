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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "menu_items")
@TypeAlias("menu_item")
public class MenuItemDto {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    @Field("identifier")
    @JsonProperty("identificador")
    private int identifier;

    @Field("restaurant_identifier")
    @JsonProperty("identificadorRestaurante")
    private int restaurantIdentifier;

    @Field("name")
    @JsonProperty("nombre")
    private String name;

    @Field("description")
    @JsonProperty("descripcion")
    private String description;

    @Field("type")
    @JsonProperty("tipo")
    private String type; // ENTRADA, SEGUNDO, POSTRE, BEBIDA

    @Field("status")
    @JsonProperty("estado")
    private boolean status;
}
