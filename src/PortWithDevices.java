import java.util.ArrayList;

/** Класс для хранения названия устройств, связанных с портом
 *
 */
public class PortWithDevices {
    private ArrayList<String> devices;
    private String portName;

    public PortWithDevices(String portName) {
        devices = new ArrayList<>();
        this.portName = portName;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public void addDevice(String deviceName){
        devices.add(deviceName);
    }

    public ArrayList<String> getDevices() {
        return devices;
    }
}
