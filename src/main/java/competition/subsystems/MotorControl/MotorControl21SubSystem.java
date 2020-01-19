package competition.subsystems.motorcontrol;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.XPropertyManager;

@Singleton
public class MotorControl21SubSystem extends BaseSubsystem
{
    public final XCANTalon motor21;

    @Inject
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