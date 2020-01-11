package competition.subsystems.motorcontrol;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.XPropertyManager;

public class MotorControl34SubSystem extends BaseSubsystem
{
    public final XCANTalon motor34;

    public MotorControl34SubSystem(CommonLibFactory factory, XPropertyManager prop)
    {
        this.motor34 = factory.createCANTalon(34);
        motor34.configureAsMasterMotor(this.getPrefix(), "motor34", true, false);
    }
    
    public void setMotor1Power(double power)
    {
        motor34.simpleSet(power);
    }
}