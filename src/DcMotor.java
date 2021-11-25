import jssc.SerialPort;

import javax.swing.*;

public class DcMotor extends Device{

    public DcMotor(SerialPort port) {
        this.port = port;
        box = new Box(BoxLayout.Y_AXIS);
        add(box);
        createGUIForDevice();
    }

    private void createGUIForDevice() {
        box.add(new JLabel("DC_Motor"));

    }
//    @Override
//    public void sendData(String data) {
//
//    }
}
