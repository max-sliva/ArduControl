import jssc.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ToArduino {
    private static ThreadForPortsUpdate threadForPortsUpdate;
    private static Box deviceList;
    private static JPanel centerPane;
    private static Box boxWithDevices;

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
        centerPane = new JPanel(new BorderLayout());
        deviceList = new Box(BoxLayout.Y_AXIS);
//        deviceList.add(new JCheckBox("1"));
        boxWithDevices = new Box(BoxLayout.Y_AXIS);

        centerPane.add(deviceList, BorderLayout.WEST);
        centerPane.add(boxWithDevices, BorderLayout.CENTER);
        frame.add(centerPane, BorderLayout.CENTER);
    }

    private static void setWest(JFrame frame) { //левая панель со списком портов
        DeviceFabric devFab = new DeviceFabric();
        JList listOfPorts = new JList();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listOfPorts.setModel(listModel);
        PortScanner portScanner = new PortScanner();
        listModel.add(0, "1");
        portScanner.startUSBscanner(listModel);
        threadForPortsUpdate = portScanner.getThreadForPortsUpdate();
        listOfPorts.addListSelectionListener(arg->{ //при выборе порта
            String selPort = listOfPorts.getSelectedValue().toString();
            System.out.println("Selected port = "+selPort);
            if (!selPort.equals("1")) {
                ArrayList<String> devices = threadForPortsUpdate.getPortDevices(selPort);
                System.out.println("Devices for port = "+devices.size());
                deviceList = new Box(BoxLayout.Y_AXIS);
                devices.forEach(it->{ //для всех устройств
                    System.out.println("device = "+it);
                    JCheckBox checkBoxForDevice = new JCheckBox(it);
                    deviceList.add(checkBoxForDevice);
                    checkBoxForDevice.addActionListener(arg1->{
                        Device curDevice = new Device();
                        if (checkBoxForDevice.isSelected()){
                            SerialPort tempPort = threadForPortsUpdate.getPortByName(selPort);
//todo сделать хешмап для устройств, добавлять и создавать их при выборе порта, а потом проверять видимость
//                            if (!curDevice.isVisible())
                            curDevice = devFab.getDevice(checkBoxForDevice.getText(), tempPort);
                            boxWithDevices.add(curDevice);
                            boxWithDevices.validate();
                            System.out.println("on "+checkBoxForDevice.getText());
                        } else {
                            System.out.println("off "+checkBoxForDevice.getText());
                            curDevice.setVisible(false);
                            boxWithDevices.remove(curDevice);
                        }
                    });

                });
                centerPane.remove(deviceList);
                centerPane.add(deviceList, BorderLayout.WEST);
                centerPane.validate();
//                centerPane.setVisible(false);
//                centerPane.setVisible(true);
            }
        });
        frame.add(listOfPorts, BorderLayout.WEST);
    }

}
