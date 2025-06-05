import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeviceDriverTest {
    @Mock
    FlashMemoryDevice hardware;

    /*@Test
    public void read_From_Hardware() {
        // TODO: replace hardware with a Test Double
        FlashMemoryDevice hardware = null;
        DeviceDriver driver = new DeviceDriver(hardware);
        byte data = driver.read(0xFF);
        assertEquals(0, data);
    }*/

    @Test
    public void read_From_Hardware_equal() {
        when(hardware.read(0xFF)).thenReturn((byte) 42);

        DeviceDriver driver = new DeviceDriver(hardware);
        byte data = driver.read(0xFF);

        assertEquals(42, data);
        verify(hardware, times(5)).read(0xFF);
    }

    @Test
    public void read_From_Hardware_exception() {
        when(hardware.read(0xFF))
                .thenReturn((byte) 1, (byte) 1, (byte) 2, (byte) 1, (byte) 1);

        DeviceDriver driver = new DeviceDriver(hardware);

        assertThrows(ReadFailException.class, () -> driver.read(0xFF));
        //verify(hardware, times(5)).read(0xFF);
    }

    @Test
    public void write_From_Hardware_normal() {
        when(hardware.read(0xFF))
                .thenReturn(null);

        DeviceDriver driver = new DeviceDriver(hardware);


         //verify(hardware, times(5)).read(0xFF);
    }
}
