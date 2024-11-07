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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "categories")
@TypeAlias("category")
public class CategoryDto {
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

    @Field("status")
    @JsonProperty("estado")
    private boolean status;
}
