package ru.nsu.fit.planesApp.dto.mappers;

import org.springframework.stereotype.Component;
import ru.nsu.fit.planesApp.dto.AirportDtoContainer;
import ru.nsu.fit.planesApp.dto.AirportScheduleInboundDto;
import ru.nsu.fit.planesApp.dto.AvailableCitiesDto;
import ru.nsu.fit.planesApp.model.Airport;
import ru.nsu.fit.planesApp.model.Flight;

@Component
public class AirportMapper {

  public AirportDtoContainer mapAirportName(Airport airport) {
    return AirportDtoContainer.builder()
      .airportsName(airport.getAirportName())
      .airportCode(airport.getAirportCode())
      .build();
  }

}
