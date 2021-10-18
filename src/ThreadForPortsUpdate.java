import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadForPortsUpdate extends Thread{
    private ArrayList<String> portNames;
    private ArrayList<String> portNames2;
    private HashMap<String, SerialPort> portsHashMap;
    private DefaultListModel<String> listModel;

    public ThreadForPortsUpdate(ArrayList<String> portNames, DefaultListModel<String> listModel) {
        this.portNames = portNames;
        portNames2 = new ArrayList<>(Arrays.asList(SerialPortList.getPortNames()));
        this.listModel = listModel;
        portsHashMap = new HashMap<>();
    }

    @Override
    public void run(){
        System.out.println("in thread for update");
        while (true){ //в бесконечном цикле будем раз в 3 сек сканировать порты
            AtomicInteger num = new AtomicInteger();
            //получаем список активных портов
            portNames =  new ArrayList<>(Arrays.asList(SerialPortList.getPortNames()));
            if (portNames.size() != portNames2.size()) { //если размер прошлого списка и нового разные
                printPortsArray();
                checkPorts();
                portNames2 = portNames;
            } else { //если списки равны по размеру
                num.set(0);
                portNames.forEach ( it -> {
                    String tempPort = it;
                    portNames2.forEach ( it2 -> {
                        if (it2.equals(tempPort)) num.getAndIncrement();
                    });
                });
                if (num.get() != portNames.size()) { //если кол-во одинаковых портов меньше кол-ва портов
                    num.set(0);
                    System.out.println("Ports changed 2");
                    checkPorts();
                    portNames2 = portNames;
                    printPortsArray();
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("End thread for update");
    }

    public void printPortsArray() { //вывод списка портов
        System.out.println("Ports changed");
        if (portNames.size() == 0) System.out.println("No arduinos");
        else portNames.forEach ( it ->
                System.out.println(it)
        );
    }

    private void checkPorts(){
        portNames.forEach ( it -> {
            if (portNames2.contains(it)){
                System.out.println(""+it+" - new");
            }
            try {
                setListnerForArdu(it);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
        listModel.addAll(portNames);
    }

    public void setListnerForArdu(String port) throws SerialPortException {
        SerialPort tempPort = new SerialPort(port);
        portsHashMap.put(port, tempPort);
        System.out.println("Set listner for port = "+port);
//    if (tempPort.isOpened) tempPort.closePort()
        tempPort.openPort(); //открываем порт
        tempPort.setParams(
                9600,
                8,
                1,
                0
        ); //задаем параметры порта, 9600 - скорость, такую же нужно задать для Serial.begin в Arduino
        AtomicBoolean first = new AtomicBoolean(true);
        AtomicReference<String> str = new AtomicReference<>("");
        tempPort.addEventListener( event ->{
            if (event.isRXCHAR()) {
                try {
                    String temp = tempPort.readString();
                    if (temp!= null) {
//                        System.out.println("from port = "+temp);
                        if (temp.contains("\n")) str.set(str.get()+temp);
                        else {
//                            str.get().concat(temp);
                            str.set(str.get()+temp);
//                            System.out.println("str progress = "+str);
                        }
//                        else str += temp.trim { it < ' ' && it !='\n'}

                        if (str.get().contains("end devList")) {
                            System.out.println("str = "+str);
                            int i = 0;
                            int size = str.get().split("\n").length;
                            System.out.println("DeviceList (size = "+size+" ): ");
//todo сформировать список устройств и связать с портом
                            for (String s: str.get().split("\n")){
                                if (i>0 && i<size-1)
                                    System.out.println(i+": "+s);
                                i++;
                            }

                            str.set("");
                            if (first.get()) {
                                tempPort.writeString("1");
                                first.set(false);
                            }

                        }

                    } else System.out.println("received null from "+port);
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
