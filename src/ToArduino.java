import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ToArduino {
    private static ThreadForPortsUpdate threadForPortsUpdate;
    private static JList<JCheckBox> deviceList;

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
        JPanel centerPane = new JPanel(new BorderLayout());
//todo вставить в центральную панель Box, туда грузить интерфейс выбранных устройств для управления
        deviceList = new JList<JCheckBox>();
//todo сделать листнер для чек-боксов, чтоб выбирать устройства для управления


        centerPane.add(deviceList, BorderLayout.WEST);
        frame.add(centerPane, BorderLayout.CENTER);
    }

    private static void setWest(JFrame frame) { //левая панель со списком портов
        JList listOfPorts = new JList();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listOfPorts.setModel(listModel);
//todo сделать лиснер для порта, по щелчку загружаем в deviceList спиок устройств для выбранного порта в виде чек-боксов
        PortScanner portScanner = new PortScanner();
        listModel.add(0, "1");
        portScanner.startUSBscanner(listModel);
        threadForPortsUpdate = portScanner.getThreadForPortsUpdate();
        listOfPorts.addListSelectionListener(arg->{
//            http://www.java2s.com/Tutorials/Java/Swing_How_to/JList/Create_JList_of_CheckBox.htm
            String selPort = listOfPorts.getSelectedValue().toString();
            System.out.println("Selected port = "+selPort);
            if (!selPort.equals("1")) {
                ArrayList<String> devices = threadForPortsUpdate.getPortDevices(selPort);
                System.out.println("Devices for port = ");
            }
//            deviceList = new JList(new CheckListItem[] {
        });
        frame.add(listOfPorts, BorderLayout.WEST);
    }

}
