package ru.nsu.fit.planesApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = AvailableCitiesDto.AvailableCitiesDtoBuilder.class)
public class AvailableCitiesDto {

  String sourceCity;
  String destinationCity;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AvailableCitiesDtoBuilder {
  }
}
