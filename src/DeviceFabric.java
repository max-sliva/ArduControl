import jssc.SerialPort;

public class DeviceFabric {
    public Device getDevice(String deviceType, SerialPort port){
        System.out.println("devType = "+deviceType);
        if(deviceType == null){
            System.out.println("Null in fabric");
            return null;
        }
        if(deviceType.contains("Servo")){
            System.out.println("Servo in fabric");
            return new ServoMotor(port);

        } else if(deviceType.contains("DC_Motor")) {
            return new DcMotor(port);
        }
        System.out.println("Null2 in fabric");
        return null;
    }
}
