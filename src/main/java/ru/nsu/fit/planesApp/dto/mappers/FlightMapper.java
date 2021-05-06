package ru.nsu.fit.planesApp.dto.mappers;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import org.springframework.stereotype.Component;
import ru.nsu.fit.planesApp.dto.AirportScheduleInboundDto;
import ru.nsu.fit.planesApp.dto.AirportScheduleOutboundDto;
import ru.nsu.fit.planesApp.dto.AvailableAirportsDto;
import ru.nsu.fit.planesApp.dto.AvailableCitiesDto;
import ru.nsu.fit.planesApp.model.Flight;

@Component
public class FlightMapper {

  private final SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");

  public AvailableCitiesDto mapCities(Flight flight) {
    return AvailableCitiesDto.builder()
      .sourceCity(flight.getDepartureCity())
      .destinationCity(flight.getArrivalCity())
      .build();
  }

  public AvailableAirportsDto mapAirports(Flight flight) {
    return AvailableAirportsDto.builder()
      .sourceAirport(flight.getDepartureAirportName())
      .destinationAirport(flight.getArrivalAirportName())
      .build();
  }

  public AirportScheduleInboundDto mapInboundSchedule(Flight flight) {
    return AirportScheduleInboundDto.builder()
      .departureAirport(flight.getDepartureAirportName())
      .flightNo(flight.getFlightNo())
      .scheduledArrival(flight.getScheduledArrival())
      .dayOfWeek(simpleDateformat.format(flight.getScheduledArrival()))
      .build();
  }

  public AirportScheduleOutboundDto mapOutboundSchedule(Flight flight) {
    return AirportScheduleOutboundDto.builder()
      .ArrivalAirport(flight.getArrivalAirportName())
      .flightNo(flight.getFlightNo())
      .scheduledDeparture(flight.getScheduledDeparture())
      .dayOfWeek(simpleDateformat.format(flight.getScheduledDeparture()))
      .build();
  }
}
