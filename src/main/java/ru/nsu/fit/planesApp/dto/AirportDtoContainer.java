package ru.nsu.fit.planesApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import ru.nsu.fit.planesApp.model.Airport;

@Value
@Builder
@JsonDeserialize(builder = AirportDtoContainer.AirportDtoContainerBuilder.class)
public class AirportDtoContainer {

  String airportsName;
  String airportCode;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AirportDtoContainerBuilder {
  }
}
