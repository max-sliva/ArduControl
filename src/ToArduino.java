import javax.swing.*;
import java.awt.*;

public class ToArduino {
    public static void main(String[] args) {
        createGUI();
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Arduino devices updater");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setWest(frame);
        setCenter(frame);
        frame.setSize(400,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void setCenter(JFrame frame) {
        JList<JCheckBox> deviceList = new JList<>();
//todo вставить в центральную область панель - слева список устройств с чек-боксами, в центре - поле для управления
    }

    private static void setWest(JFrame frame) { //левая панель со списком портов
        JList listOfPorts = new JList();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listOfPorts.setModel(listModel);
        PortScanner portScanner = new PortScanner();
        portScanner.startUSBscanner(listModel);
//        listOfPorts.
        frame.add(listOfPorts, BorderLayout.WEST);
    }

}
