package competition.subsystems;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.neo.DualNeoSubsystem;
import edu.wpi.first.wpilibj2.command.RunCommand;
import xbot.common.command.XScheduler;

@Singleton
public class SubsystemDefaultCommandMap {
    // For setting the default commands on subsystems

    @Inject
    public void setupDriveSubsystem(DriveSubsystem driveSubsystem, TankDriveWithJoysticksCommand command) {
        driveSubsystem.setDefaultCommand(command);
    }

    @Inject
    public void setupDualShooter(XScheduler scheduler, DualNeoSubsystem shooter) {
        scheduler.setDefaultCommand(shooter, new RunCommand(() -> shooter.stop(), shooter));
    }
}
