package ru.nsu.fit.planesApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.sql.Date;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = AirportScheduleInboundDto.AirportScheduleInboundDtoBuilder.class)
public class AirportScheduleInboundDto {

  String dayOfWeek;
  Date scheduledArrival;
  String flightNo;
  String departureAirport;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AirportScheduleInboundDtoBuilder {
  }
}
