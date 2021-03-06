package competition.subsystems.motorcontrol;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.XPropertyManager;

@Singleton
public class MotorControl23SubSystem extends BaseSubsystem
{
    public final XCANTalon motor23;

    @Inject
    public MotorControl23SubSystem(CommonLibFactory factory, XPropertyManager prop)
    {
        this.motor23 = factory.createCANTalon(23);
        motor23.configureAsMasterMotor(this.getPrefix(), "motor23", true, false);
    }
    
    public void setMotor1Power(double power)
    {
        motor23.simpleSet(power);
    }
}