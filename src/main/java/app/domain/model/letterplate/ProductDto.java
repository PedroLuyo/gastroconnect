package app.domain.model.letterplate;

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
@Document(collection = "products")
@TypeAlias("product")
public class ProductDto {
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

    @Field("category_identifier")
    @JsonProperty("identificadorCategoria")
    private int categoryIdentifier;

    @Field("name")
    @JsonProperty("nombre")
    private String name;

    @Field("description")
    @JsonProperty("descripcion")
    private String description;

    @Field("presentation")
    @JsonProperty("presentacion")
    private Presentation presentacion;

    @Field("price")
    @JsonProperty("precio")
    private Double price;

    @Field("image")
    @JsonProperty("imagen")
    private String image;

    @Field("ingredients")
    @JsonProperty("ingredientes")
    private List<String> ingredients;

    @Field("status")
    @JsonProperty("estado")
    private boolean status;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Presentation {
        @Field("name")
        @JsonProperty("nombre")
        private String name;

        @Field("unit")
        @JsonProperty("unidad")
        private String unit;
    }
}
