package org.couchbase.quickstart.springdata.models;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Airline implements Serializable {

    @Id
    @NotBlank(message = "Id is mandatory")
    private String id;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "IATA code is mandatory")
    private String iata;

    @NotBlank(message = "ICAO code is mandatory")
    private String icao;

    @NotBlank(message = "Callsign is mandatory")
    private String callsign;

    @NotBlank(message = "Country is mandatory")
    private String country;


}
