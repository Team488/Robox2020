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

public class IndependentMotorControl extends BaseCommand
{
    final MotorControl21SubSystem motor21;
    final MotorControl22SubSystem motor22;
    final MotorControl23SubSystem motor23;
    final MotorControl34SubSystem motor34;
    final MotorControl33SubSystem motor33;
    final MotorControl32SubSystem motor32;

    final OperatorInterface oi;

    @Inject
    public IndependentMotorControl(OperatorInterface oi,
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
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        motor21.setMotor1Power(oi.gamepad.getLeftVector().y);
        motor22.setMotor1Power(oi.gamepad.getRightVector().y);
        motor23.setMotor1Power(oi.Opgamepad.getLeftVector().y);
        motor32.setMotor1Power(oi.Opgamepad.getRightVector().y);
        motor33.setMotor1Power(0);
        motor34.setMotor1Power(0);
    }
    
}