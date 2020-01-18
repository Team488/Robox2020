package competition.subsystems.motorcontrol;

import xbot.common.command.BaseSubsystem;

import com.google.inject.Inject;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class NeoControl35 extends BaseSubsystem
{
    public final CANSparkMax neoMotorControl;
    public final CANEncoder neoMotorEncoder;
    public final CANPIDController motor35PIDController;

    @Inject
    public NeoControl35()
    {
        neoMotorControl = new CANSparkMax(35, MotorType.kBrushless);
        neoMotorControl.restoreFactoryDefaults();
        neoMotorEncoder = neoMotorControl.getEncoder();
        neoMotorEncoder.setPosition(0);
        motor35PIDController = neoMotorControl.getPIDController();
    }
    
    public void setMotorPower(double power)
    {
        neoMotorControl.set(power);
    }
}