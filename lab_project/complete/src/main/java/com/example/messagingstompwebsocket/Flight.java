package com.example.messagingstompwebsocket;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;


@Table(name = "Flight")
@Entity
public class Flight{

    @Id
    @Column(name = "icao24")
    private String icao24;

    @Column(name = "firstSeen")
    private Integer firstSeen;

    @Column(name = "estDepartureAirport")
    private String  estDepartureAirport;

    @Column(name = "lastSeen")
    private Integer  lastSeen;

    @Column(name = "estArrivalAirport")
    private String  estArrivalAirport;


    @Column(name = "callsign")
    private String  callsign;

    /**
     * @return the icao24
     */
    public String getIcao24() {
        return icao24;
    }

    /**
     * @param icao24 the icao24 to set
     */
    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    /**
     * @return the firstSeen
     */
    public Integer getFirstSeen() {
        return firstSeen;
    }

    public String getFirstSeenFormated(){
        int timestamp = firstSeen;
        java.util.Date timeFormated = new java.util.Date((long)timestamp*1000);
        
        return timeFormated.toString();
    }

    /**
     * @param firstSeen the firstSeen to set
     */
    public void setFirstSeen(Integer firstSeen) {
        this.firstSeen = firstSeen;
    }

    /**
     * @return the estDepartureAirport
     */
    public String getEstDepartureAirport() {
        return estDepartureAirport;
    }

    /**
     * @param estDepartureAirport the estDepartureAirport to set
     */
    public void setEstDepartureAirport(String estDepartureAirport) {
        this.estDepartureAirport = estDepartureAirport;
    }

    /**
     * @return the lastSeen
     */
    public Integer getLastSeen() {
        return lastSeen;
    }
    
    public String getLastSeenFormated(){
        int timestamp = lastSeen;
        java.util.Date timeFormated = new java.util.Date((long)timestamp*1000);
        
        return timeFormated.toString();
    }

    /**
     * @param lastSeen the lastSeen to set
     */
    public void setLastSeen(Integer lastSeen) {
        this.lastSeen = lastSeen;
    }

    /**
     * @return the estArrivalAirport
     */
    public String getEstArrivalAirport() {
        return estArrivalAirport;
    }

    /**
     * @param estArrivalAirport the estArrivalAirport to set
     */
    public void setEstArrivalAirport(String estArrivalAirport) {
        this.estArrivalAirport = estArrivalAirport;
    }

    /**
     * @return the callsign
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * @param callsign the callsign to set
     */
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    
    @Override
    public String toString() {
        return "Flight [callsign=" + callsign + ", estArrivalAirport=" + estArrivalAirport + ", estDepartureAirport="
                + estDepartureAirport + ", firstSeen=" + firstSeen + ", icao24=" + icao24 + ", lastSeen=" + lastSeen
                + "]";
    }

    
    
}
