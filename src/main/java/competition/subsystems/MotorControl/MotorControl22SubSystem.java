package competition.subsystems.motorcontrol;

import com.google.inject.Inject;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.XPropertyManager;

public class MotorControl22SubSystem extends BaseSubsystem
{
    public final XCANTalon motor22;

    @Inject
    public MotorControl22SubSystem(CommonLibFactory factory, XPropertyManager prop)
    {
        this.motor22 = factory.createCANTalon(22);
        motor22.configureAsMasterMotor(this.getPrefix(), "motor22", true, false);
    }
    
    public void setMotor1Power(double power)
    {
        motor22.simpleSet(power);
    }
}