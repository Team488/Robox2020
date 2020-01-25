package competition.subsystems.motorcontrol.commands;

import com.google.inject.Inject;
import com.revrobotics.ControlType;

import competition.subsystems.motorcontrol.NeoControl20;
import competition.subsystems.motorcontrol.NeoControl35;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import xbot.common.command.BaseCommand;
import xbot.common.properties.PropertyFactory;

public class DualWheeledShooterCommand extends BaseCommand {

    NeoControl20 neo1;
    NeoControl35 neo2;

    double kP;
    double kI;
    double kD;
    double kIz;
    double kFF;
    double kMaxOutput;
    double kMinOutput;
    double maxRPM;

    @Inject
    public DualWheeledShooterCommand(NeoControl20 neo1, NeoControl35 neo2, PropertyFactory pf) {
        this.neo1 = neo1;
        this.neo2 = neo2;
    }

    private void initializeParameters() {
        kP = 5e-5;
        kI = 1e-6;
        kD = 0;
        kIz = 0;
        kFF = 0;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 5700;

        // set PID coefficients
        neo1.neoMotorControl.setP(kP);
        neo1.neoMotorControl.setI(kI);
        neo1.neoMotorControl.setD(kD);
        neo1.neoMotorControl.setIZone(kIz);
        neo1.neoMotorControl.setFF(kFF);
        neo1.neoMotorControl.setOutputRange(kMinOutput, kMaxOutput);

        neo2.neoMotorControl.setP(kP);
        neo2.neoMotorControl.setI(kI);
        neo2.neoMotorControl.setD(kD);
        neo2.neoMotorControl.setIZone(kIz);
        neo2.neoMotorControl.setFF(kFF);
        neo2.neoMotorControl.setOutputRange(kMinOutput, kMaxOutput);

        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);
        SmartDashboard.putNumber("MaxRPM", maxRPM);
        SmartDashboard.putNumber("TargetRPM-1", 0);
        SmartDashboard.putNumber("TargetRPM-2", 0);
    }

    @Override
    public void initialize() {
        initializeParameters();
    }

    public void updateParameters() {
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);

        if ((p != kP)) {
            neo1.neoMotorControl.setP(p);
            neo2.neoMotorControl.setP(p);
            kP = p;
        }
        if ((i != kI)) {
            neo1.neoMotorControl.setI(i);
            neo2.neoMotorControl.setP(p);
            kI = i;
        }
        if ((d != kD)) {
            neo1.neoMotorControl.setD(d);
            neo2.neoMotorControl.setP(p);
            kD = d;
        }
        if ((iz != kIz)) {
            neo1.neoMotorControl.setIZone(iz);
            neo2.neoMotorControl.setP(p);
            kIz = iz;
        }
        if ((ff != kFF)) {
            neo1.neoMotorControl.setFF(ff);
            neo2.neoMotorControl.setP(p);
            kFF = ff;
        }
        if ((max != kMaxOutput) || (min != kMinOutput)) {
            neo1.neoMotorControl.setOutputRange(min, max);
            neo2.neoMotorControl.setP(p);
            kMinOutput = min;
            kMaxOutput = max;
        }

        SmartDashboard.putNumber("Encoder35 Position", neo2.neoMotorControl.getPosition()); // total rotaions
        SmartDashboard.putNumber("Encoder35 Velocity", neo2.neoMotorControl.getVelocity());

        SmartDashboard.putNumber("Encoder20 Position", neo1.neoMotorControl.getPosition());
        SmartDashboard.putNumber("Encoder20 Velocity", neo1.neoMotorControl.getVelocity());
    }

    @Override
    public void execute() {
        updateParameters();

        neo1.neoMotorControl.setReference(SmartDashboard.getNumber("TargetRPM-1", 0), ControlType.kVelocity);
        neo2.neoMotorControl.setReference(SmartDashboard.getNumber("TargetRPM-2", 0), ControlType.kVelocity);
    }

}