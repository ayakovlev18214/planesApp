package ru.nsu.fit.planesApp.model;

import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

  @Column(name = "days_of_week")
  private String departureAirport;

  @Column(name = "days_of_week")
  private String departureAirportName;

  @Column(name = "days_of_week")
  private String departureCity;

  @Column(name = "days_of_week")
  private String arrivalAirport;

  @Column(name = "days_of_week")
  private String arrivalAirportName;

  @Column(name = "days_of_week")
  private String arrivalCity;

  @Column(name = "days_of_week")
  private String aircraftCode;

  @ElementCollection
  @Column(name = "days_of_week")
  private Set<Integer> daysOfWeek;

}
