package ru.nsu.fit.planesApp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "pricing_rules")
public class Price {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  @Column(name = "departure_airport")
  private String departureAirport;

  @Column(name = "arrival_airport")
  private String arrivalAirport;

  @Column(name = "max")
  private Integer max;

  @Column(name = "fare_conditions")
  private String fareConditions;
}
