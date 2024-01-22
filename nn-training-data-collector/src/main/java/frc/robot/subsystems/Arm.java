package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.nn.TrainingDataProto.TrainingDataTimeStep;
import frc.robot.nn.TrainingDataProto.TrainingDataTimeStep.Builder;

public class Arm  implements Subsystem {
    private Arm(CANSparkMax ...mcs) {
        this.motor_monitors = new MotorMonitor[mcs.length];
        for (int i = 0; i < mcs.length; i++)
            this.motor_monitors[i] = new MotorMonitor("arm_"+i, mcs[i].getEncoder()::getPosition, mcs[i]::get);
    }
    private Arm(int ...ids) {
        this.motor_monitors = new MotorMonitor[ids.length];
        for (int i = 0; i < ids.length; i++) {
            // The Machine suggested this try-thing because it doesn't like taking out the garbage I guess
            try (CANSparkMax mc = new CANSparkMax(ids[i], MotorType.kBrushless)) {
                this.motor_monitors[i] = new MotorMonitor("arm_"+i, mc.getEncoder()::getPosition, mc::get);
            }
        }
    }
    private static Arm instance;
    MotorMonitor[] motor_monitors;
    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm(frc.robot.constants.Ports.can.arm.MOTORS);
        }
        return instance;
    }


    @Override
    public TrainingDataTimeStep getTrainingData() {
        Builder out = TrainingDataTimeStep.newBuilder();
        for (MotorMonitor m : this.motor_monitors)
            out.addMotorReadings(m.takeReading());
        return out.build();
    }
    
}
