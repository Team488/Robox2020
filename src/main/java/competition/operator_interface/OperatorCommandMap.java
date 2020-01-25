package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.motorcontrol.commands.IndependentMotorControl;
import competition.subsystems.motorcontrol.commands.MotorControlJoysticks;
import competition.subsystems.neo.DualNeoSubsystem;
import competition.subsystems.neo.SingleWheelSpeedControlCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import xbot.common.subsystems.pose.commands.SetRobotHeadingCommand;

/**
 * Maps operator interface buttons to commands
 */
@Singleton
public class OperatorCommandMap {
    
    // Example for setting up a command to fire when a button is pressed:
    @Inject
    public void setupMyCommands(
            OperatorInterface operatorInterface,
            SetRobotHeadingCommand resetHeading, 
            MotorControlJoysticks joysticks, 
            TankDriveWithJoysticksCommand tank,
            IndependentMotorControl ind)
            //DualWheeledShooterCommandWControl dualWheelShootWControl)
    {
        resetHeading.setHeadingToApply(90);
        // operatorInterface.gamepad.getifAvailable(1).whenPressed(joysticks);
        // operatorInterface.gamepad.getifAvailable(2).whenPressed(tank);
        // operatorInterface.gamepad.getifAvailable(3).whenPressed(shoot);
        // operatorInterface.gamepad.getifAvailable(4).whenPressed(ind);
        //operatorInterface.gamepad.getifAvailable(1).whenPressed(dualWheelShootWControl);
        //operatorInterface.gamepad.getifAvailable(4).whenPressed(dualWheelShoot);
    }

    @Inject
    public void setupShootercommands(
        OperatorInterface operatorInterface,
        DualNeoSubsystem shooter,
        SingleWheelSpeedControlCommand singleWheel) {
            Command speedUp = new InstantCommand(() -> shooter.changeTargetSpeed(100));
            Command slowDown = new InstantCommand(() -> shooter.changeTargetSpeed(-100));
            Command stop = new RunCommand(() -> shooter.stop(), shooter);

            operatorInterface.gamepad.getifAvailable(1).whenPressed(singleWheel);
            operatorInterface.gamepad.getifAvailable(2).whenPressed(stop);
            operatorInterface.gamepad.getifAvailable(5).whenPressed(speedUp);
            operatorInterface.gamepad.getifAvailable(6).whenPressed(slowDown);
    }
}
