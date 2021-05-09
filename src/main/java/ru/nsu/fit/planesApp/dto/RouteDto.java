package ru.nsu.fit.planesApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import ru.nsu.fit.planesApp.model.Flight;

@Value
@Builder
@JsonDeserialize(builder = RouteDto.RouteDtoBuilder.class)
public class RouteDto {

  List<RouteNode> routeNodes;
  String fareCondition;
  Integer price;
  Date departureDate;
  Date arrivalDate;

  @JsonPOJOBuilder(withPrefix = "")
  public static class RouteDtoBuilder {
  }
}
