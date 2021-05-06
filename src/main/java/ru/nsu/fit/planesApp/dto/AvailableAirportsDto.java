package ru.nsu.fit.planesApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
@JsonDeserialize(builder = AvailableAirportsDto.AvailableAirportsDtoBuilder.class)
public class AvailableAirportsDto {

  String sourceAirport;
  String destinationAirport;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AvailableAirportsDtoBuilder {
  }
}