package ru.nsu.fit.planesApp.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "routes")
public class Route {

  @JoinColumn(name = "flight_no")
  private Flight flight;

  @Column(name = "departure_airport")
  private String departureAirport;

  @Column(name = "departure_airport_name")
  private String departureAirportName;

  @Column(name = "departure_city")
  private String departureCity;

  @Column(name = "arrival_airport")
  private String arrivalAirport;

  @Column(name = "arrival_airport_name")
  private String arrivalAirportName;

  @Column(name = "arrival_city")
  private String arrivalCity;

  @Column(name = "aircraft_code")
  private String aircraftCode;

  @ElementCollection
  @Column(name = "days_of_week")
  private Set<Integer> daysOfWeek;

}
