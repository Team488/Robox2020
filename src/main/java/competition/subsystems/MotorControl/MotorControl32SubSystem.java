package competition.subsystems.motorcontrol;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.XPropertyManager;

@Singleton
public class MotorControl32SubSystem extends BaseSubsystem
{
    public final XCANTalon motor32;

    @Inject
    public MotorControl32SubSystem(CommonLibFactory factory, XPropertyManager prop)
    {
        this.motor32 = factory.createCANTalon(32);
        motor32.configureAsMasterMotor(this.getPrefix(), "motor32", true, false);
    }
    
    public void setMotor1Power(double power)
    {
        motor32.simpleSet(power);
    }
}