package competition.subsystems.neo;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import xbot.common.command.BaseCommand;
import xbot.common.properties.PropertyFactory;

public class SingleWheelSpeedControlCommand extends BaseCommand {

    final DualNeoSubsystem shooter;
    final OperatorInterface oi;

    @Inject 
    public SingleWheelSpeedControlCommand(DualNeoSubsystem shooter, PropertyFactory pf, OperatorInterface oi) {
        this.shooter = shooter;
        this.oi = oi;
        pf.setPrefix(this);
        this.addRequirements(shooter);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        double speed = shooter.getTargetSpeed();
        shooter.setPidGoal(speed);
    }
}