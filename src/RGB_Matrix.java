import jssc.SerialPort;

import javax.swing.*;

public class RGB_Matrix extends Device{
    public RGB_Matrix(SerialPort port) {
        this.port = port;
        box = new Box(BoxLayout.Y_AXIS);
        add(box);
        createGUIForDevice();
    }

    private void createGUIForDevice() {
        box.add(new JLabel("RGB_Matrix"));

    }

}
