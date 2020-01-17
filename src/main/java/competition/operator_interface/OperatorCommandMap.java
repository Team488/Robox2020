package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.motorcontrol.commands.MotorControlJoysticks;
import competition.subsystems.motorcontrol.commands.ShooterControlPowerset;
import xbot.common.subsystems.pose.commands.SetRobotHeadingCommand;
import competition.subsystems.motorcontrol.commands.IndependentMotorControl;

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
            ShooterControlPowerset shoot,
            IndependentMotorControl ind)
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).whenPressed(joysticks);
        operatorInterface.gamepad.getifAvailable(2).whenPressed(tank);
        operatorInterface.gamepad.getifAvailable(3).whenPressed(shoot);
        operatorInterface.gamepad.getifAvailable(4).whenPressed(ind);
        operatorInterface.Opgamepad.getifAvailable(1).whenPressed(ind);
    }
}
