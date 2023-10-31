package g56514.stibride.gestionDB.dto;

/**
 * Represents the Data Transfer Object of Station.
 * 
 * @author yohan
 */
public class StationDto extends Dto<Integer> {

    private String name;

    /**
     * Creates a new instance of <code>StationDto</code>.
     *
     * @param id the id of the station.
     * @param name the name of the station.
     */
    public StationDto(Integer id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * Getter of name.
     * 
     * @return the station's name.
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
     * Returns the string representation of the data.
     * 
     * @return the string representation of the data.
     */
    @Override
    public String toString() {
        return "StationDto{id=" + getKey() + ", name=" + name + '}';
    }

}
