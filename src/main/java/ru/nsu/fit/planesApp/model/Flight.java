package ru.nsu.fit.planesApp.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "flights_v")
public class Flight {

  @Id
  @Column(name = "flight_id")
  private Integer flightId;

  @Column(name = "flight_no")
  private String flightNo;

  @Column(name = "scheduled_departure")
  private Date scheduledDeparture;

  @Column(name = "scheduled_arrival")
  private Date scheduledArrival;

  @Column(name = "departure_airport")
  private String departureAirport;

  @Column(name = "arrival_airport")
  private String arrivalAirport;

  @Column(name = "arrival_city")
  private String arrivalCity;

  @Column(name = "departure_city")
  private String departureCity;

  @Column(name = "arrival_airport_name")
  private String arrivalAirportName;

  @Column(name = "departure_airport_name")
  private String departureAirportName;

  @Column(name = "status")
  private String status;

  @Column(name = "aircraft_code")
  private String aircraftCode;

  @Column(name = "actual_departure")
  private Date actualDeparture;

  @Column(name = "actual_arrival")
  private Date actualArrival;
}
