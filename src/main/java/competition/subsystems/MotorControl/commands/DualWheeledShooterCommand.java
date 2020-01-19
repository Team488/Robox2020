package competition.subsystems.motorcontrol.commands;

import com.google.inject.Inject;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import competition.subsystems.motorcontrol.NeoControl20;
import competition.subsystems.motorcontrol.NeoControl35;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import xbot.common.command.BaseCommand;
import xbot.common.properties.PropertyFactory;

public class DualWheeledShooterCommand extends BaseCommand {

    NeoControl20 neo1;
    NeoControl35 neo2;

    final CANPIDController neo1PID;
    final CANPIDController neo2PID;

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

        neo1PID = neo1.neoMotorControl.getPIDController();
        neo2PID = neo2.neoMotorControl.getPIDController();
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
        neo1PID.setP(kP);
        neo1PID.setI(kI);
        neo1PID.setD(kD);
        neo1PID.setIZone(kIz);
        neo1PID.setFF(kFF);
        neo1PID.setOutputRange(kMinOutput, kMaxOutput);

        neo2PID.setP(kP);
        neo2PID.setI(kI);
        neo2PID.setD(kD);
        neo2PID.setIZone(kIz);
        neo2PID.setFF(kFF);
        neo2PID.setOutputRange(kMinOutput, kMaxOutput);

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

        neo2.neoMotorControl.setInverted(true);
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
            neo1PID.setP(p);
            neo2PID.setP(p);
            kP = p;
        }
        if ((i != kI)) {
            neo1PID.setI(i);
            neo2PID.setP(p);
            kI = i;
        }
        if ((d != kD)) {
            neo1PID.setD(d);
            neo2PID.setP(p);
            kD = d;
        }
        if ((iz != kIz)) {
            neo1PID.setIZone(iz);
            neo2PID.setP(p);
            kIz = iz;
        }
        if ((ff != kFF)) {
            neo1PID.setFF(ff);
            neo2PID.setP(p);
            kFF = ff;
        }
        if ((max != kMaxOutput) || (min != kMinOutput)) {
            neo1PID.setOutputRange(min, max);
            neo2PID.setP(p);
            kMinOutput = min;
            kMaxOutput = max;
        }

        SmartDashboard.putNumber("Encoder35 Position", neo2.neoMotorEncoder.getPosition()); // total rotaions
        SmartDashboard.putNumber("Encoder35 Velocity", neo2.neoMotorEncoder.getVelocity()); // was rpm now rps

        SmartDashboard.putNumber("Encoder20 Position", neo1.neoMotorEncoder.getPosition());
        SmartDashboard.putNumber("Encoder20 Velocity", neo1.neoMotorEncoder.getVelocity());
    }

    @Override
    public void execute() {
        updateParameters();

        neo1PID.setReference(SmartDashboard.getNumber("TargetRPM-1", 0), ControlType.kVelocity);
        neo2PID.setReference(SmartDashboard.getNumber("TargetRPM-2", 0), ControlType.kVelocity);
    }

}