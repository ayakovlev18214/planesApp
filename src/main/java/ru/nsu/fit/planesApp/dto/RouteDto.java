package ru.nsu.fit.planesApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import ru.nsu.fit.planesApp.model.Flight;

@Value
@Builder
@JsonDeserialize(builder = RouteDto.RouteDtoBuilder.class)
public class RouteDto {

  String from;
  String to;
  String cityFrom;
  String cityTo;

  @JsonPOJOBuilder(withPrefix = "")
  public static class RouteDtoBuilder {
  }
}
