package competition.subsystems.neo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revrobotics.ControlType;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class DualNeoSubsystem extends BaseSubsystem {

    XCANSparkMax leader;
    //XCANSparkMax follower;
    
    final DoubleProperty targetRpmProp;
    final DoubleProperty currentRpmProp;

    @Inject
    public DualNeoSubsystem(CommonLibFactory clf, PropertyFactory pf) {

        leader = clf.createCANSparkMax(20, this.getPrefix(), "Leader");
        //follower= clf.createCANSparkMax(35, this.getPrefix(), "Follower");
        //follower.foll
        pf.setPrefix(this);

        targetRpmProp = pf.createEphemeralProperty("TargetRPM", 0);
        currentRpmProp = pf.createEphemeralProperty("CurrentRPM", 0);
    }

    public void setTargetSpeed(double speed) {
        targetRpmProp.set(speed);
    }

    public double getTargetSpeed() {
        return targetRpmProp.get();
    }

    public void changeTargetSpeed(double amount) {
        double speed = getTargetSpeed();
        speed += amount;
        setTargetSpeed(speed);
    }

    public void setPidGoal(double speed) {
        leader.setReference(speed, ControlType.kVelocity);
    }

    public void stop() {
        leader.set(0);
    }

    @Override
    public void periodic() {
        leader.periodic();
        currentRpmProp.set(leader.getVelocity());
    }
}