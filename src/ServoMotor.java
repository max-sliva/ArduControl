import jssc.SerialPort;
import javax.swing.*;

public class ServoMotor extends Device {

    public ServoMotor(SerialPort port) {
        this.port = port;
        box = new Box(BoxLayout.Y_AXIS);
        add(box);
        createGUIForDevice();
    }

    private void createGUIForDevice(){
        System.out.println("Created Servo");
        JSlider slider = new JSlider(1, 170, 1);
//        slider.setEnabled(false);
        slider.addChangeListener( arg -> {
            System.out.println(slider.getValue());
            textToArdu = "{'device' : 'servo1Angle',  'angle1': "+slider.getValue()+"}";
//        val serialPort = usbScanner.getSerialPortByName(choosenPort)
            sendData(textToArdu);
        });
        box.add(new JLabel("ServoMotor"));
        box.add(slider);
    }

//    @Override
//    public void sendData(String data) {
//        try {
//            port.writeString(data);
//        } catch (SerialPortException e) {
//            e.printStackTrace();
//        }
//    }
}
