package g56514.stibride.gestionDB.dto;

/**
 * Represents the Data Transfer Object of Favori.
 * 
 * @author yohan
 */
public class FavoriDto extends Dto<Integer> {
    
    private String name;
    private int departure;
    private int arrival;
    
    /**
     * Creates a new instance of <code>FavoriDto</code>.
     *
     * @param id the id of the favori.
     * @param name the name of the favori.
     * @param departure the departure of the favori.
     * @param arrival the destination of the favori.
     */
    public FavoriDto(int id, String name, int departure, int arrival) {
        super(id);
        this.name = name;
        this.departure = departure;
        this.arrival = arrival;
    }
    
    /**
     * Getter of name.
     * 
     * @return the favori's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of name.
     * 
     * @param name the favori's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of departure
     * 
     * @return the departure.
     */
    public int getDeparture() {
        return departure;
    }

    /**
     * Setter of departure.
     * 
     * @param departure the departure.
     */
    public void setDeparture(int departure) {
        this.departure = departure;
    }

    /**
     * Getter of arrival.
     * 
     * @return the arrival.
     */
    public int getArrival() {
        return arrival;
    }

    /**
     * Setter of arrival.
     * 
     * @param arrival the arrival.
     */
    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    /**
     * Returns the string representation of the data.
     * 
     * @return the string representation of the data.
     */
    @Override
    public String toString() {
        return getKey() + "," + name + "," + departure + "," + arrival;
    } 
    
}
