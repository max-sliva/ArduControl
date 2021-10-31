import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChBoxTester {
    public static void main(String[] args) {
        createGUI();
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Arduino devices updater");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setWest(frame);
        setCenter(frame);
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static void setCenter(JFrame frame) {
        JPanel centerPane = new JPanel(new BorderLayout());
        Box deviceList = new Box(BoxLayout.Y_AXIS);
        ArrayList<String> devices = new ArrayList<>();
        devices.add("11");
        devices.add("22");
        devices.add("33");
        for (String device : devices) {
            deviceList.add(new JCheckBox(device));
        }

        centerPane.add(deviceList, BorderLayout.WEST);
        frame.add(centerPane, BorderLayout.CENTER);
    }

}
