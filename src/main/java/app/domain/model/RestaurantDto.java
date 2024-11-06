package app.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "restaurants")
@TypeAlias("restaurant")
public class RestaurantDto {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    @Field("uid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String uid;

    @Field("identifier")
    @JsonProperty("identificador")
    private int identifier;

    @Field("name")
    @JsonProperty("nombre")
    private String name;

    @Field("business_info")
    @JsonProperty("informacionNegocio")
    private BusinessInfo businessInfo;

    @Field("media")
    @JsonProperty("media")
    private Media media;

    @Field("contact")
    @JsonProperty("contacto")
    private Contact contact;

    @Field("location")
    @JsonProperty("ubicacion")
    private Location location;

    @Field("schedule")
    @JsonProperty("horario")
    private Schedule schedule;

    @Field("features")
    @JsonProperty("caracteristicas")
    private Features features;

    @Field("payment_methods")
    @JsonProperty("metodosPago")
    private List<String> paymentMethods;

    @Field("status")
    @JsonProperty("estado")
    private boolean status;

    @Data
    @Builder
    public static class BusinessInfo {
        @Field("ruc")
        @JsonProperty("ruc")
        private Long ruc;

        @Field("business_name")
        @JsonProperty("razonSocial")
        private String businessName;

        @Field("business_type")
        @JsonProperty("tipoNegocio")
        private String businessType;

        @Field("category")
        @JsonProperty("categoria")
        private List<String> category;
    }

    @Data
    @Builder
    public static class Media {
        @Field("logo")
        @JsonProperty("logo")
        private String logo;

        @Field("background")
        @JsonProperty("fondo")
        private String background;
    }

    @Data
    @Builder
    public static class Contact {
        @Field("phone")
        @JsonProperty("telefono")
        private Long phone;

        @Field("email")
        @JsonProperty("email")
        private String email;

        @Field("website")
        @JsonProperty("sitioWeb")
        private String website;

        @Field("social_media")
        @JsonProperty("redesSociales")
        private SocialMedia socialMedia;
    }

    @Data
    @Builder
    public static class SocialMedia {
        @Field("facebook")
        @JsonProperty("facebook")
        private String facebook;

        @Field("instagram")
        @JsonProperty("instagram")
        private String instagram;

        @Field("x")
        @JsonProperty("x")
        private String x;

        @Field("youtube")
        @JsonProperty("youtube")
        private String youtube;
    }

    @Data
    @Builder
    public static class Location {
        @Field("address")
        @JsonProperty("direccion")
        private String address;

        @Field("district")
        @JsonProperty("distrito")
        private String district;

        @Field("city")
        @JsonProperty("ciudad")
        private String city;

        @Field("country")
        @JsonProperty("pais")
        private String country;

        @Field("reference")
        @JsonProperty("referencias")
        private String reference;
    }

    @Data
    @Builder
    public static class Schedule {
        @Field("monday")
        @JsonProperty("lunes")
        private DaySchedule monday;

        @Field("tuesday")
        @JsonProperty("martes")
        private DaySchedule tuesday;

        @Field("wednesday")
        @JsonProperty("miercoles")
        private DaySchedule wednesday;

        @Field("thursday")
        @JsonProperty("jueves")
        private DaySchedule thursday;

        @Field("friday")
        @JsonProperty("viernes")
        private DaySchedule friday;

        @Field("saturday")
        @JsonProperty("sabado")
        private DaySchedule saturday;

        @Field("sunday")
        @JsonProperty("domingo")
        private DaySchedule sunday;

        @Field("holidays")
        @JsonProperty("feriados")
        private DaySchedule holidays;
    }

    @Data
    @Builder
    public static class DaySchedule {
        @Field("is_open")
        @JsonProperty("abierto")
        private Boolean isOpen;

        @Field("shifts")
        @JsonProperty("turnos")
        private List<TimeRange> shifts;
    }

    @Data
    @Builder
    public static class TimeRange {
        @Field("opening")
        @JsonProperty("apertura")
        private String opening;

        @Field("closing")
        @JsonProperty("cierre")
        private String closing;
    }

    @Data
    @Builder
    public static class Features {
        @Field("capacity")
        @JsonProperty("aforo")
        private Integer capacity;

        @Field("has_parking")
        @JsonProperty("estacionamiento")
        private Boolean hasParking;

        @Field("has_wifi")
        @JsonProperty("wifi")
        private Boolean hasWifi;

        @Field("accepts_reservations")
        @JsonProperty("reservas")
        private Boolean acceptsReservations;

        @Field("has_delivery")
        @JsonProperty("delivery")
        private Boolean hasDelivery;

        @Field("has_takeout")
        @JsonProperty("recojo")
        private Boolean hasTakeout;

        @Field("has_menu")
        @JsonProperty("menu")
        private Boolean hasMenu;

        @Field("has_carta")
        @JsonProperty("carta")
        private Boolean hasCarta;
    }
}