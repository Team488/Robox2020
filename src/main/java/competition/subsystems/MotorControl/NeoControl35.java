package competition.subsystems.motorcontrol;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.injection.wpi_factories.CommonLibFactory;

@Singleton
public class NeoControl35 extends BaseSubsystem
{
    public final XCANSparkMax neoMotorControl;

    @Inject
    public NeoControl35(CommonLibFactory clf)
    {
        neoMotorControl = clf.createCANSparkMax(35, "NeoControl20", "Main");
        neoMotorControl.restoreFactoryDefaults();
        neoMotorControl.setPosition(0);
        neoMotorControl.setInverted(false);
    }
    
    public void setMotorPower(double power)
    {
        neoMotorControl.set(power);
    }
}