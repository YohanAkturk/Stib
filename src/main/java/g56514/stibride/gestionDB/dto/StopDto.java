package g56514.stibride.gestionDB.dto;

/**
 * Represents the Data Transfer Object of StopDto.
 * 
 * @author yohan
 */
public class StopDto extends Dto<Pair> {

    private int idOrder;

    /**
     * Creates a new instance of <code>StopDto</code>.
     *
     * @param idLine the id of the line.
     * @param idStation the id of the station.
     * @param idOrder the order of the station.
     */
    public StopDto(Integer idLine, Integer idStation, int idOrder) {
        super(new Pair(idLine, idStation));
        this.idOrder = idOrder;
    }

    /**
     * Getter of order id.
     * @return the order id.
     */
    public int getIdOrder() {
        return idOrder;
    }

    /**
     * Setter of order id.
     * 
     * @param idOrder the order id.
     */
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    /**
     * Returns the string representation of the data.
     * 
     * @return the string representation of the data.
     */
    @Override
    public String toString() {
        return "StopDto{id_line=" + getKey().getFirst() + ", id_station=" + getKey().getSecond() + ", id_order=" + idOrder + '}';
    }
    
    

}
