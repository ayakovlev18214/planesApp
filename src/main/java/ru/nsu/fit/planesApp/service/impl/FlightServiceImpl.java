package ru.nsu.fit.planesApp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.planesApp.dto.*;
import ru.nsu.fit.planesApp.dto.mappers.FlightMapper;
import ru.nsu.fit.planesApp.model.Airport;
import ru.nsu.fit.planesApp.model.Flight;
import ru.nsu.fit.planesApp.model.Price;
import ru.nsu.fit.planesApp.repository.AirportRepository;
import ru.nsu.fit.planesApp.repository.BookingRepository;
import ru.nsu.fit.planesApp.repository.FlightRepository;
import ru.nsu.fit.planesApp.repository.PriceRepository;
import ru.nsu.fit.planesApp.service.FlightService;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
  private final FlightRepository flightRepository;
  private final FlightMapper mapper;
  private final Set<String> availableFlights = Set.of("Scheduled");
  private final BookingRepository bookingRepository;
  private final PriceRepository priceRepository;
  private final AirportRepository airportRepository;

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
      List<List<Flight>> routes = new ArrayList<>();
      Set<String> visited = new HashSet<>();
      String city = from;
      if (from.length() == 3 && from.chars().allMatch(Character::isUpperCase)) {
        Airport airport = airportRepository.findFirstByAirportCode(from);
        assert airport != null;
        city = airport.getCity();
      }
      visited.add(city);
      findRoutes(routes,
          new LinkedList<>(),
          visited,
          numberOfConn,
          city,
          from,
          to,
          flightClass,
          0,
          departureDate
          );
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

  private void findRoutes(
      List<List<Flight>> routes,
      LinkedList<Flight> routeAccum,
      Set<String> visited,
      int maxIters,
      String fromCity,
      String from,
      String to,
      String flightClass,
      Integer sum,
      Date departureDate
  ) {
    if (visited.size() - 2 > maxIters) {
      return;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(departureDate);
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    Date departureDatePlusOne = new Date(calendar.getTimeInMillis());
    if (routeAccum.size() > 0) {
      //noinspection ConstantConditions
      if (routeAccum.peekLast().getArrivalAirport().equals(to)
          || routeAccum.peekLast().getArrivalCity().equals(to)) {
        routes.add(new ArrayList<>(routeAccum));
        visited.remove(fromCity);
        routeAccum.removeLast();
        return;
      }
    }
    Set<Flight> flights;
    if (from.length() == 3 && from.chars().allMatch(Character::isUpperCase)) {
      flights = new HashSet<>(flightRepository.findByAirport(
          from,
          departureDate,
          departureDatePlusOne));
    } else {
      flights = new HashSet<>(flightRepository.findByCity(
          from,
          departureDate,
          departureDatePlusOne));
    }
    flights.forEach(flight -> {
      Price p = priceRepository.findPrice(
          flight.getArrivalAirport(),
          flight.getDepartureAirport(),
          flightClass);
      if (p != null) {
        routeAccum.add(flight);
        visited.add(flight.getArrivalCity());
        innerStep(
            routes,
            routeAccum,
            visited,
            maxIters,
            flight.getArrivalCity(),
            flight.getArrivalCity(),
            to,
            flightClass,
            sum + p.getMax(),
            flight.getScheduledArrival()
        );
      }
    });
    routeAccum.removeLast();
    visited.remove(fromCity);
  }

}
