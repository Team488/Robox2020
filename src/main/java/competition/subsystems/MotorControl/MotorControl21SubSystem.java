package competition.subsystems.MotorControl;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.XPropertyManager;

public class MotorControl21SubSystem extends BaseSubsystem
{
    public final XCANTalon motor21;

    public MotorControl21SubSystem(CommonLibFactory factory, XPropertyManager prop)
    {
        this.motor21 = factory.createCANTalon(21);
        motor21.configureAsMasterMotor(this.getPrefix(), "motor21", true, false);
    }
    
    public void setMotor1Power(double power)
    {
        motor21.simpleSet(power);
    }
}