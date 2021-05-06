package ru.nsu.fit.planesApp.dto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.sql.Date;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = AirportScheduleOutboundDto.AirportScheduleOutboundDtoBuilder.class)
public class AirportScheduleOutboundDto {

  String dayOfWeek;
  Date scheduledDeparture;
  String flightNo;
  String ArrivalAirport;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AirportScheduleOutboundDtoBuilder {
  }
}
