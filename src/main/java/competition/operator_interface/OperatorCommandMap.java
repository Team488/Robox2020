package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.motorcontrol.commands.DualWheeledShooterCommand;
import competition.subsystems.motorcontrol.commands.IndependentMotorControl;
import competition.subsystems.motorcontrol.commands.MotorControlJoysticks;
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
            DualWheeledShooterCommand dualWheelShoot,
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
}
