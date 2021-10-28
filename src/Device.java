import jssc.SerialPort;
import jssc.SerialPortException;

import javax.swing.*;

public class Device extends JPanel implements DeviceInterface {
    protected SerialPort port;
    protected Box box;
    protected String textToArdu;

    protected void sendData(String data) {
        try {
            port.writeString(data);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
}
