package ru.nsu.fit.planesApp.service.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.planesApp.dto.AirportScheduleInboundDto;
import ru.nsu.fit.planesApp.dto.AirportScheduleOutboundDto;
import ru.nsu.fit.planesApp.dto.AvailableAirportsDto;
import ru.nsu.fit.planesApp.dto.AvailableCitiesDto;
import ru.nsu.fit.planesApp.dto.RouteDto;
import ru.nsu.fit.planesApp.dto.mappers.FlightMapper;
import ru.nsu.fit.planesApp.model.Flight;
import ru.nsu.fit.planesApp.repository.BookingRepository;
import ru.nsu.fit.planesApp.repository.FlightRepository;
import ru.nsu.fit.planesApp.service.FlightService;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
  private final FlightRepository flightRepository;
  private final FlightMapper mapper;
  private final Set<String> availableFlights = Set.of("Scheduled");
  private final BookingRepository bookingRepository;

  @Override
  public Set<AvailableAirportsDto> getAllAvailableAirports() {
    return flightRepository.findAllByStatusIn(availableFlights).stream()
      .map(mapper::mapAirports).collect(Collectors.toSet());
  }

  @Override
  public Set<AvailableCitiesDto> getAllAvailableCities() {
    return flightRepository.findAllByStatusIn(availableFlights).stream()
      .map(mapper::mapCities).collect(Collectors.toSet());
  }

  @Override
  public List<AirportScheduleInboundDto> getInboundAirportFlights(String airport) {

    return flightRepository.findAllByArrivalAirport(airport).stream()
      .map(mapper::mapInboundSchedule).collect(Collectors.toList());
  }

  @Override
  public List<RouteDto> getRoute(String from,
                                 String to,
                                 String date,
                                 String flightClass,
                                 Integer numberOfConn) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    try {
      Date departureDate = new Date(simpleDateFormat.parse(date).getTime());
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(departureDate);
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      List<Flight> routeDtoList = innerStep(
        new ArrayList<>(),
        new ArrayList<>(),
        0,
        numberOfConn == null ? 999 : numberOfConn,
        from,
        to,
        flightClass,
        departureDate,
        new Date(calendar.getTime().getTime())
      );
      assert routeDtoList != null;
      return routeDtoList.stream().map(mapper::mapRoute).collect(Collectors.toList());
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void bookRout(Collection<RouteDto> list) {
    bookingRepository.bookRoute(list);
  }

  @Override
  public List<AirportScheduleOutboundDto> getOutboundAirportFlights(String airport) {
    return flightRepository.findAllByDepartureAirport(airport).stream()
      .map(mapper::mapOutboundSchedule).collect(Collectors.toList());
  }


  private List<Flight> innerStep(List<Flight> given,
                                 List<String> visited,
                                 int iteration,
                                 int maxIters,
                                 String from,
                                 String to,
                                 String flightClass,
                                 Date departureDate,
                                 Date departureDatePlusOne
  ) {
    if (iteration > maxIters) {
      return null;
    }
    List<Flight> flights;
    if (iteration == 0) {
      if (from.length() == 3) {
        flights = flightRepository.findAllByDepartureAirportAndScheduledDepartureBetween(
          from,
          departureDate,
          departureDatePlusOne);
      } else {
        flights = flightRepository.findAllByDepartureCityAndScheduledDepartureBetween(
          from,
          departureDate,
          departureDatePlusOne);
      }
    } else {
      if (from.length() == 3) {
        flights = flightRepository.findAllByDepartureAirport(from);
      } else {
        flights = flightRepository.findAllByDepartureCity(from);
      }
    }
    for(Flight flight : flights) {
      if (flight.getArrivalCity().equals(to) || flight.getArrivalAirport().equals(to)) {
        given.add(flight);
        return given;
      }
    }
    for (Flight flight : flights) {
      List<Flight> newFlights;
      if (!visited.contains(flight.getArrivalCity())) {
        given.add(flight);
        visited.add(flight.getArrivalCity());
        newFlights = innerStep(
          given,
          visited,
          iteration + 1,
          maxIters,
          flight.getArrivalCity(),
          to,
          flightClass,
          departureDate,
          departureDatePlusOne);
        if (newFlights != null) return newFlights;
        given.remove(flight);
      }
    }
    return given;
  }

}
