package g56514.stibride.repository;

import g56514.stibride.gestionDB.dto.StationDto;
import g56514.stibride.gestionDB.dto.StopDto;
import g56514.stibride.gestionDB.exception.RepositoryException;
import g56514.stibride.gestionDB.jdbc.StationDao;
import g56514.stibride.gestionDB.repository.StationRepository;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author yohan
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)

public class StationRepositoryTest {

    @Mock
    private StationDao mock;
    private final StationDto station1;
    private final StationDto station2;
    private final StopDto stopStation1;
    private final StopDto stopL;
    private final StationDto stationL;
    private final StopDto stopR;
    private final StationDto stationR;
    private final List<StopDto> stops;
    private final List<Integer> lines;
    private static final int KEY = 12_345;
    private final List<StationDto> all;

    public StationRepositoryTest() {
        System.out.println("FavoriRepositoryTest constructor");
        //Test data
        station1 = new StationDto(KEY, "STATION_1");
        station2 = new StationDto(99_999, "STATION_2");

        stationL = new StationDto(300, "STATION_300");
        stationR = new StationDto(301, "STATION_301");

        all = new ArrayList<>();
        all.add(station1);
        all.add(station2);
        all.add(stationL);
        all.add(stationR);
        stopStation1 = new StopDto(1, station1.getKey(), 10);
        stopL = new StopDto(1, 300, 9);
        stopR = new StopDto(1, 301, 11);
        stops = new ArrayList<>();
        stops.add(stopStation1);
        stops.add(stopL);
        stops.add(stopR);
        lines = new ArrayList<>();
        lines.add(1);
    }

    @BeforeEach
    void init() throws RepositoryException {
        System.out.println("==== BEFORE EACH =====");
        //Mock behaviour
        Mockito.lenient().when(mock.select(station1.getKey())).thenReturn(station1);
        Mockito.lenient().when(mock.select(station2.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectWithName(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectStationsForEveryLines(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectStationsForEveryLines(station1.getKey())).thenReturn(stops);
        Mockito.lenient().when(mock.selectName(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectName(KEY)).thenReturn(station1.getName());
        Mockito.lenient().when(mock.selectLinesForStation(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectLinesForStation(station1.getKey())).thenReturn(lines);
        Mockito.lenient().when(mock.selectNeighbour(null, 10, false)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectNeighbour(1, null, false)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectNeighbour(1, 10, true)).thenReturn(station1);
        Mockito.lenient().when(mock.selectNeighbour(1, 10, false)).thenReturn(station1);
        Mockito.lenient().when(mock.selectId(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectId(station1.getName())).thenReturn(KEY);
    }

    @Test
    public void testGetExist() throws Exception {
        System.out.println("testGetExist");
        //Arrange
        StationDto expected = station1;
        StationRepository repository = new StationRepository(mock);
        //Action
        StationDto result = repository.get(KEY);
        //Assert        
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(KEY);
    }

    @Test
    public void testGetNotExist() throws Exception {
        System.out.println("testGetNotExist");
        //Arrange
        StationRepository repository = new StationRepository(mock);
        //Action
        StationDto result = repository.get(station2.getKey());
        //Assert        
        assertNull(result);
        Mockito.verify(mock, times(1)).select(station2.getKey());
    }

    @Test
    public void testGetIncorrectParameter() throws Exception {
        System.out.println("testGetIncorrectParameter");
        //Arrange
        StationRepository repository = new StationRepository(mock);
        Integer incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.get(incorrect);
        });
        Mockito.verify(mock, times(1)).select(null);
    }

    @Test
    public void testAddWhenExisting() throws Exception {
        System.out.println("testAddWhenExisting");
        //Arrange
        StationRepository repository = new StationRepository(mock);
        //Action
        Integer i = repository.add(station1);
        System.out.println(i);
        //Assert       
        Mockito.verify(mock, times(1)).select(KEY);
        Mockito.verify(mock, times(1)).update(station1);
        Mockito.verify(mock, times(0)).insert(any(StationDto.class));
    }

    @Test
    public void testSelectAll() throws Exception {
        System.out.println("testSelectAll");
        //Arrange
        int expected = 4; //because there are 4 datas
        StationRepository repository = new StationRepository(mock);
        //Action
        List<StationDto> result = repository.getAll();
        //Assert        
        assertEquals(expected, result.size());
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    public void testselectStationsForEveryLinesIncorrectParameter() throws Exception {
        System.out.println("testselectStationsForEveryLinesIncorrectParameter");
        //Arrange
        StationRepository repository = new StationRepository(mock);
        Integer incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.selectStationsForEveryLines(incorrect);
        });
        Mockito.verify(mock, times(1)).selectStationsForEveryLines(null);
    }

    @Test
    public void testselectStationsForEveryLines() throws Exception {
        System.out.println("testselectStationsForEveryLines");
        //Arrange
        int expected = 3; //because there are 3 stops for station1's line
        StationRepository repository = new StationRepository(mock);
        //Action
        List<StopDto> result = repository.selectStationsForEveryLines(station1.getKey());
        //Assert        
        assertEquals(expected, result.size());
        Mockito.verify(mock, times(1)).selectStationsForEveryLines(station1.getKey());
    }

    @Test
    public void testselectNameIncorrectParameter() throws Exception {
        System.out.println("testselectNameIncorrectParameter");
        //Arrange
        StationRepository repository = new StationRepository(mock);
        Integer incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.selectName(incorrect);
        });
        Mockito.verify(mock, times(1)).selectName(null);
    }

    @Test
    public void testselectName() throws Exception {
        System.out.println("testselectName");
        //Arrange
        String expected = station1.getName(); //because there are 2 datas
        StationRepository repository = new StationRepository(mock);
        //Action
        String result = repository.selectName(KEY);
        //Assert        
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).selectName(KEY);
    }

    @Test
    public void testSelectLinesForStationIncorrectParameter() throws Exception {
        System.out.println("testselectStationsForEveryLinesIncorrectParameter");
        //Arrange
        StationRepository repository = new StationRepository(mock);
        Integer incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.selectLinesForStation(incorrect);
        });
        Mockito.verify(mock, times(1)).selectLinesForStation(null);
    }

    @Test
    public void testSelectLinesForStation() throws Exception {
        System.out.println("testselectStationsForEveryLines");
        //Arrange
        int expected = 1; //because there is 1 line for station1
        StationRepository repository = new StationRepository(mock);
        //Action
        List<Integer> result = repository.selectLinesForStation(station1.getKey());
        //Assert        
        assertEquals(expected, result.size());
        Mockito.verify(mock, times(1)).selectLinesForStation(station1.getKey());
    }

    @Test
    public void testselectNeighbourIncorrectFirstParameter() throws Exception {
        System.out.println("testselectNeighbourIncorrectFirstParameter");
        //Arrange
        StationRepository repository = new StationRepository(mock);
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.selectNeighbour(null, 10, false);
        });
        Mockito.verify(mock, times(1)).selectNeighbour(null, 10, false);
    }

    @Test
    public void testselectNeighbourIncorrectSecondParameter() throws Exception {
        System.out.println("testselectNeighbourIncorrectSecondParameter");
        //Arrange
        StationRepository repository = new StationRepository(mock);
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.selectNeighbour(1, null, false);
        });
        Mockito.verify(mock, times(1)).selectNeighbour(1, null, false);
    }

    @Test
    public void testSelectNeighbourR() throws Exception {
        System.out.println("testSelectNeighbour");
        //Arrange
        int expected = (int) stopStation1.getKey().getSecond(); //to compare the next order neighbour
        StationRepository repository = new StationRepository(mock);
        //Action
        StationDto result = repository.selectNeighbour(1, 10, true);
        //Assert        
        assertEquals(expected, (int) result.getKey());
        Mockito.verify(mock, times(1)).selectNeighbour(1, 10, true);
    }

    @Test
    public void testSelectNeighbourL() throws Exception {
        System.out.println("testSelectNeighbour");
        //Arrange
        int expected = (int) stopStation1.getKey().getSecond(); //to compare the next order neighbour
        StationRepository repository = new StationRepository(mock);
        //Action
        StationDto result = repository.selectNeighbour(1, 10, false);
        System.out.println(result);
        //Assert        
        assertEquals(expected, (int) result.getKey());
        Mockito.verify(mock, times(1)).selectNeighbour(1, 10, false);
    }

    @Test
    public void testSelectIdIncorrectParameter() throws Exception {
        System.out.println("testSelectIdIncorrectParameter");
        //Arrange
        StationRepository repository = new StationRepository(mock);
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.selectId(null);
        });
        Mockito.verify(mock, times(1)).selectId(null);
    }

    @Test
    public void testSelectId() throws Exception {
        System.out.println("testSelectId");
        //Arrange
        int expected = KEY;
        StationRepository repository = new StationRepository(mock);
        //Action
        int result = repository.selectId(station1.getName());
        //Assert        
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).selectId(station1.getName());
    }

}
