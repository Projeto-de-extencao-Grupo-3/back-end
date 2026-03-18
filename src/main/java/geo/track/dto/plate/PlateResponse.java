package geo.track.dto.plate;

import lombok.Data;

@Data
public class PlateResponse {
    private String status;
    private PlateData data;
    private String error;

    @Data
    public static class PlateData {
        private String plate;
        private String accuracy;
        private String state;
        private String city;
        private String country;
    }
}