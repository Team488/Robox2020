package competition.subsystems.motorcontrol;

import com.google.inject.Inject;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.XPropertyManager;

public class MotorControl33SubSystem extends BaseSubsystem
{
    public final XCANTalon motor33;

    @Inject
    public MotorControl33SubSystem(CommonLibFactory factory, XPropertyManager prop)
    {
        this.motor33 = factory.createCANTalon(33);
        motor33.configureAsMasterMotor(this.getPrefix(), "motor33", true, false);
    }
    
    public void setMotor1Power(double power)
    {
        motor33.simpleSet(power);
    }
}