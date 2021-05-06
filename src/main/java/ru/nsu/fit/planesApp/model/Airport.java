package ru.nsu.fit.planesApp.model;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.geo.Point;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "airports")
public class Airport {

  @Id
  @Column(name = "airport_code")
  private String airportCode;

  @Column(name = "airport_name")
  private String airportName;

  @Column(name = "city")
  private String city;

//  @OneToMany(mappedBy = "arrival_airport")
//  private Set<Flight> arrivalFlights;
//
//  @OneToMany(mappedBy = "departure_airport")
//  private Set<Flight> departureFlights;

//  @Column(name = "coordinates")
//  private Point coordinates; //TODO WTF???

  @Column(name = "timezone")
  private String timezone;


}
