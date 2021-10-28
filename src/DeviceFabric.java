import jssc.SerialPort;

public class DeviceFabric {
    public Device getDevice(String deviceType, SerialPort port){
        if(deviceType == null){
            return null;
        }
        if(deviceType.equalsIgnoreCase("Servo")){
            return new ServoMotor(port);

        } else if(deviceType.equalsIgnoreCase("DC_Motor")) {
            return new DcMotor(port);
        }
        return null;
    }
}
