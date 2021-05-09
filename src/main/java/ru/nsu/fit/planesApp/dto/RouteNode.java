package ru.nsu.fit.planesApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import ru.nsu.fit.planesApp.model.Flight;

@Value
@Builder
@JsonDeserialize(builder = RouteNode.RouteNodeBuilder.class)
public class RouteNode {

  String from;
  String to;
  String cityFrom;
  String cityTo;
  Integer flightId;
  Integer price;

  @JsonPOJOBuilder(withPrefix = "")
  public static class RouteNodeBuilder {
  }
}
