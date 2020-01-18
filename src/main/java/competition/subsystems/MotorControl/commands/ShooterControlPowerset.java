package competition.subsystems.motorcontrol.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.motorcontrol.MotorControl21SubSystem;
import competition.subsystems.motorcontrol.MotorControl22SubSystem;
import competition.subsystems.motorcontrol.MotorControl23SubSystem;
import competition.subsystems.motorcontrol.MotorControl32SubSystem;
import competition.subsystems.motorcontrol.MotorControl33SubSystem;
import competition.subsystems.motorcontrol.MotorControl34SubSystem;
import xbot.common.command.BaseCommand;

public class ShooterControlPowerset extends BaseCommand
{

    final MotorControl21SubSystem motor21;
    final MotorControl22SubSystem motor22;
    final MotorControl23SubSystem motor23;
    final MotorControl34SubSystem motor34;
    final MotorControl33SubSystem motor33;
    final MotorControl32SubSystem motor32;
    boolean cd;
    final OperatorInterface oi;

    @Inject
    public ShooterControlPowerset(  OperatorInterface oi,
                                    MotorControl21SubSystem motor21,
                                    MotorControl22SubSystem motor22, 
                                    MotorControl23SubSystem motor23, 
                                    MotorControl32SubSystem motor32, 
                                    MotorControl33SubSystem motor33, 
                                    MotorControl34SubSystem motor34)
    {
        this.oi = oi;
        this.motor21 = motor21;
        this.motor22 = motor22;
        this.motor23 = motor23;
        this.motor32 = motor32;
        this.motor33 = motor33;
        this.motor34 = motor34;
        this.requires(this.motor21);
        this.requires(this.motor22);
        this.requires(this.motor23);
        this.requires(this.motor32);
        this.requires(this.motor33);
        this.requires(this.motor34);

        cd = false;
    }

    @Override
    public void initialize() {
        log.info("Initializing");

    }

    @Override
    public void execute() {
        
        double power = .5;
        if(oi.gamepad.getRawWPILibJoystick().getRawButton(5))
        {
            drive(power);
        }
        else if(oi.gamepad.getRawWPILibJoystick().getRawButton(6))
        {
            drive(-power);
        }
        else
        {
            drive(0);
        }


        if(oi.gamepad.getRawWPILibJoystick().getRawButtonPressed(9) && !cd)
        {
            cd = true;
            if(Math.abs(power) <= 1)
            {
                power += -.05;
                System.out.println(power);
            }
        }
        else if(oi.gamepad.getRawWPILibJoystick().getRawButtonReleased(9) && cd)
        {
            cd = false;
        }

        if(oi.gamepad.getRawWPILibJoystick().getRawButtonPressed(10) && !cd)
        {
            cd = true;
            if(Math.abs(power) <= 1)
            {
                power += -.05;
                System.out.println(power);
            }
        }
        else if(oi.gamepad.getRawWPILibJoystick().getRawButtonReleased(10) && cd)
        {
            cd = false;
        }

    }

    public void drive(double power)
    {
        motor21.setMotor1Power(power);
        motor22.setMotor1Power(power);
        motor23.setMotor1Power(power);
        motor32.setMotor1Power(-power);
        motor33.setMotor1Power(-power);
        motor34.setMotor1Power(-power);
    }
    
}