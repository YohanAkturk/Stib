package g56514.stibride.model;

import java.util.List;

/**
 * Represents the data to be displayed into the table view.
 * 
 * @author yohan
 */
public class DataToDisplay {

    private String stationName;
    private List<Integer> lines;

    /**
     * Creates a new instance of <code>DataToDisplay</code>.
     *
     * @param stationName the name of the station.
     * @param lines the lines.
     */
    public DataToDisplay(String stationName, List<Integer> lines) {
        this.stationName = stationName;
        this.lines = lines;
    }

    /**
     * Getter of station's name.
     * 
     * @return the station's name.
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * Setter of station's name.
     * 
     * @param stationName the station's name.
     */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    /**
     * Getter of lines.
     * 
     * @return the lines.
     */
    public List<Integer> getLines() {
        return lines;
    }

    /**
     * Setter of lines.
     * 
     * @param lines the lines.
     */
    public void setLines(List<Integer> lines) {
        this.lines = lines;
    }
}
