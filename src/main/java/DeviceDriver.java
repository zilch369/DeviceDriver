/**
 * This class is used by the operating system to interact with the hardware 'FlashMemoryDevice'.
 */
public class DeviceDriver {

    FlashMemoryDevice hw;

    public DeviceDriver(FlashMemoryDevice hardware) {
        // TODO: implement this method
        hw = hardware;
    }

    public byte read(long address) {
        byte returnData = 0;
        returnData = hw.read(address);
        postConditionOfRead(address, returnData);
        return returnData;
    }

    private void postConditionOfRead(long address, byte returnData) {
        for (int i = 0; i < 4; i++) {
            if (returnData != hw.read(address)) {
                throw new ReadFailException("Read failed at address: " + address);
            }
        }
    }

    public void write(long address, byte data) {
        if (hw.read(address) != (byte) 0xFF) {;
            throw new IllegalStateException("Cannot write to address " + address + " as it is not empty.");
        }
        hw.write(address, data);
    }
}

class ReadFailException extends RuntimeException {
    public ReadFailException(String message) {
        super(message);
    }
}

class App {

    private DeviceDriver driver;

    public App(DeviceDriver driver) {
        this.driver = driver;
    }

    void readAndPrint(long addressFrom, long addressTo){
        for (long address = addressFrom; address <= addressTo; address++) {
            byte data = driver.read(address);
            System.out.println("Address: " + address + ", Data: " + data);
        }
    }

    void writeAll(byte data){
        for (long address = 0x00; address <= 0x03; address++) {
            driver.write(address, data);
        }
    }
}