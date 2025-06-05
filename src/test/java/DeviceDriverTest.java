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
    public void write_From_Hardware_Normal() {
        when(hardware.read(0xAA))
                .thenReturn((byte) 0xFF);

        DeviceDriver driver = new DeviceDriver(hardware);

        driver.write(0xAA, (byte) 0x55);

        verify(hardware).read(0xAA);
        verify(hardware).write(0xAA, (byte) 0x55);
    }

    @Test
    public void write_From_Hardware_Exception() {
        when(hardware.read(0xAA))
                .thenReturn((byte) 0x11);

        DeviceDriver driver = new DeviceDriver(hardware);

        assertThrows(IllegalStateException.class, () -> driver.write(0xAA, (byte) 0x55));
        verify(hardware).read(0xAA);
        verify(hardware, never()).write(anyLong(), anyByte());
    }

    @Test
    public void test_readAndPrint() {
        // Assuming App class is implemented with readAndPrint method

        DeviceDriver driver = new DeviceDriver(hardware);
        App app = new App(driver);

        app.readAndPrint(0x00, 0x03);

        // Verify that the read method was called for each address
        verify(hardware, times(20)).read(anyLong());
    }

    @Test
    public void writeAll() {
        // Assuming App class is implemented with readAndPrint method
        when(hardware.read(0xAA))
                .thenReturn((byte) 0x11);

        DeviceDriver driver = new DeviceDriver(hardware);
        App app = new App(driver);

        app.writeAll((byte) 0x11);

        // Verify that the read method was called for each address
        //verify(hardware, times(20)).read(anyLong());
    }
}
