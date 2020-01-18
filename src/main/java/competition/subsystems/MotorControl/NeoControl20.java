package competition.subsystems.motorcontrol;

import xbot.common.command.BaseSubsystem;

import com.google.inject.Inject;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class NeoControl20 extends BaseSubsystem
{
    public final CANSparkMax neoMotorControl;
    public final CANEncoder neoMotorEncoder;

    @Inject
    public NeoControl20()
    {
        neoMotorControl = new CANSparkMax(20, MotorType.kBrushless);
        neoMotorControl.restoreFactoryDefaults();
        neoMotorEncoder = neoMotorControl.getEncoder();
        neoMotorEncoder.setPosition(0);
    }
    
    public void setMotorPower(double power)
    {
        neoMotorControl.set(power);
    }
}