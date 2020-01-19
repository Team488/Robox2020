package competition.subsystems.motorcontrol.commands;

import com.google.inject.Inject;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.motorcontrol.MotorControl21SubSystem;
import competition.subsystems.motorcontrol.MotorControl22SubSystem;
import competition.subsystems.motorcontrol.MotorControl23SubSystem;
import competition.subsystems.motorcontrol.MotorControl32SubSystem;
import competition.subsystems.motorcontrol.MotorControl33SubSystem;
import competition.subsystems.motorcontrol.MotorControl34SubSystem;
import competition.subsystems.motorcontrol.NeoControl20;
import competition.subsystems.motorcontrol.NeoControl35;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import xbot.common.command.BaseCommand;

public class ShooterControlPowerset extends BaseCommand {

    final MotorControl21SubSystem motor21;
    final MotorControl22SubSystem motor22;
    final MotorControl23SubSystem motor23;
    final MotorControl34SubSystem motor34;
    final MotorControl33SubSystem motor33;
    final MotorControl32SubSystem motor32;
    boolean cd;
    final OperatorInterface oi;

    final NeoControl20 neoMotor20;
    final NeoControl35 neoMotor35;
    final CANPIDController neoMotor35PID;

    double power = .1;
    double kP;
    double kI;
    double kD;
    double kIz;
    double kFF;
    double kMaxOutput;
    double kMinOutput;
    double maxRPM;

    @Inject
    public ShooterControlPowerset(OperatorInterface oi, MotorControl21SubSystem motor21,
            MotorControl22SubSystem motor22, MotorControl23SubSystem motor23, MotorControl32SubSystem motor32,
            MotorControl33SubSystem motor33, MotorControl34SubSystem motor34, NeoControl20 motor20,
            NeoControl35 motor35) {
        this.oi = oi;
        this.motor21 = motor21;
        this.motor22 = motor22;
        this.motor23 = motor23;
        this.motor32 = motor32;
        this.motor33 = motor33;
        this.motor34 = motor34;
        this.addRequirements(this.motor21);
        this.addRequirements(this.motor22);
        this.addRequirements(this.motor23);
        this.addRequirements(this.motor32);
        this.addRequirements(this.motor33);
        this.addRequirements(this.motor34);

        neoMotor35 = motor35;
        neoMotor20 = motor20;
        this.addRequirements(neoMotor35);
        this.addRequirements(neoMotor20);

        neoMotor35PID = neoMotor35.neoMotorControl.getPIDController();

        cd = false;
    }

    @Override
    public void initialize() {
        log.info("Initializing");

        kP = 5e-5;
        kI = 1e-6;
        kD = 0;
        kIz = 0;
        kFF = 0;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 5700;

        // set PID coefficients
        neoMotor35PID.setP(kP);
        neoMotor35PID.setI(kI);
        neoMotor35PID.setD(kD);
        neoMotor35PID.setIZone(kIz);
        neoMotor35PID.setFF(kFF);
        neoMotor35PID.setOutputRange(kMinOutput, kMaxOutput);

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

    }

    @Override
    public void execute() {

        if (oi.gamepad.getGenericHID().getRawButton(5)) {
            drive(power);
            System.out.println(power);
        } else if (oi.gamepad.getGenericHID().getRawButton(6)) {
            drive(-power);
            System.out.println(power);
        } else {
            drive(0);

        }

        if (oi.gamepad.getGenericHID().getRawButtonPressed(9) && !cd) {
            cd = true;
            if (Math.abs(power) <= 1) {
                power += .05;
                System.out.println(power);
            }
        } else if (oi.gamepad.getGenericHID().getRawButtonReleased(9) && cd) {
            cd = false;
        }

        if (oi.gamepad.getGenericHID().getRawButtonPressed(10) && !cd) {
            cd = true;
            if (Math.abs(power) >= 0) {
                power += -.05;
                System.out.println(power);
            }
        } else if (oi.gamepad.getGenericHID().getRawButtonReleased(10) && cd) {
            cd = false;
        }
    }

    public void drive(double powerr) {

        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to
        // controller
        if ((p != kP)) {
            neoMotor35PID.setP(p);
            kP = p;
        }
        if ((i != kI)) {
            neoMotor35PID.setI(i);
            kI = i;
        }
        if ((d != kD)) {
            neoMotor35PID.setD(d);
            kD = d;
        }
        if ((iz != kIz)) {
            neoMotor35PID.setIZone(iz);
            kIz = iz;
        }
        if ((ff != kFF)) {
            neoMotor35PID.setFF(ff);
            kFF = ff;
        }
        if ((max != kMaxOutput) || (min != kMinOutput)) {
            neoMotor35PID.setOutputRange(min, max);
            kMinOutput = min;
            kMaxOutput = max;
        }
        double setPoint = powerr * maxRPM;
        neoMotor35PID.setReference(setPoint, ControlType.kVelocity);

        SmartDashboard.putNumber("SetPoint", setPoint);
        SmartDashboard.putNumber("ProcessVariable", neoMotor35.neoMotorEncoder.getVelocity());

        // motor21.setMotor1Power(powerr);
        // motor22.setMotor1Power(powerr);
        // motor23.setMotor1Power(powerr);
        // motor32.setMotor1Power(-powerr);
        // motor33.setMotor1Power(-powerr);
        // motor34.setMotor1Power(-powerr);

        // neoMotor35.setMotorPower(powerr);
        // neoMotor20.setMotorPower(-powerr);
        SmartDashboard.putNumber("Encoder35 Position", neoMotor35.neoMotorEncoder.getPosition()); // total rotaions
        SmartDashboard.putNumber("Encoder35 Velocity", neoMotor35.neoMotorEncoder.getVelocity() / 60); // was rpm now
                                                                                                       // rps

        SmartDashboard.putNumber("Encoder20 Position", neoMotor20.neoMotorEncoder.getPosition());
        SmartDashboard.putNumber("Encoder20 Velocity", neoMotor20.neoMotorEncoder.getVelocity() / 60);
    }

}