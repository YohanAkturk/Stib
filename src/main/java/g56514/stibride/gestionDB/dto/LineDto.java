package g56514.stibride.gestionDB.dto;

/**
 * Represents the Data Transfer Object of Line.
 * 
 * @author yohan
 */
public class LineDto extends Dto<Integer> {

    /**
     * Creates a new instance of <code>LineDto</code>.
     *
     * @param id the key of the line.
     */
    public LineDto(Integer id) {
        super(id);
    }

    /**
     * Returns the string representation of the data.
     *
     * @return the string representation of the data.
     */
    @Override
    public String toString() {
        return "LineDto{id= " + getKey() + '}';
    }

}
