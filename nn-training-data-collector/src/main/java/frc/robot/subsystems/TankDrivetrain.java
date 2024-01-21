package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.nn.proto.NNTrainingData.TrainingDataTimeStep;
import frc.robot.util.ObjectConvenience;

public class TankDrivetrain extends DifferentialDrive implements Subsystem {
    private TankDrivetrain(CANSparkMax left, CANSparkMax right) {
        super(left, right);
        this.left_mon = new MotorMonitor("dt_left", left.getEncoder()::getPosition, left::get);
        this.right_mon = new MotorMonitor("dt_right", right.getEncoder()::getPosition, right::get);
}
    private static TankDrivetrain instance;
    private MotorMonitor left_mon, right_mon;
    public static TankDrivetrain getInstance() {
        CANSparkMax left, right;
        if (instance == null) {
            left = ObjectConvenience.createSparkGroup(frc.robot.constants.Ports.can.drivetrain.LEFTS);
            left.setInverted(true);

            right = ObjectConvenience.createSparkGroup(frc.robot.constants.Ports.can.drivetrain.RIGHTS);
            right.setInverted(false);

            instance = new TankDrivetrain(left, right);
        }
        return instance;
    }

    public void onLoop(double xSpeed, double zRotation) {
        this.curvatureDrive(xSpeed * Math.abs(xSpeed), zRotation, Math.abs(xSpeed) < 0.1);
    }

    @Override
    public TrainingDataTimeStep getTrainingData() {
        return TrainingDataTimeStep.newBuilder()
            .addMotorReadings(left_mon.takeReading())
            .addMotorReadings(right_mon.takeReading())
            .build();
    }
}
