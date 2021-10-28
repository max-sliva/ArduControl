import jssc.SerialPort;

import javax.swing.*;

public class ServoMotor extends Device {
    private SerialPort port;
    private Box box;

    public ServoMotor(SerialPort port) {
        this.port = port;
        box = new Box(BoxLayout.Y_AXIS);
        add(box);
        createGUIForDevice();
    }

    private void createGUIForDevice(){
        
    }

    @Override
    public void sendData(String data) {

    }
}
