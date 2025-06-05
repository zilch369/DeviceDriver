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
        for (int i = 0; i < 4; i++) {
            if (returnData != hw.read(address)) {
                throw new ReadFailException("Read failed at address: " + address);
            }
        }
        return returnData;
    }

    public void write(long address, byte data) {
        // TODO: implement this method
        hw.write(address, data);
    }
}

class ReadFailException extends RuntimeException {
    public ReadFailException(String message) {
        super(message);
    }
}