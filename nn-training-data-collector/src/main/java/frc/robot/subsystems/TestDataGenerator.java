package frc.robot.subsystems;

import frc.robot.nn.TrainingDataProto.TrainingDataTimeStep;
import frc.robot.nn.TrainingDataProto.TrainingDataTimeStep.Builder;

public class TestDataGenerator implements Subsystem {

    private MotorMonitor[] mon;

    public TestDataGenerator(String ...ids) {
        this.mon = new MotorMonitor[ids.length];
        for (int i = 0; i < ids.length; i++)
            this.mon[i] = new MotorMonitor(ids[i], Math::random, Math::random);
    }

    @Override
    public TrainingDataTimeStep getTrainingData() {
        Builder out = TrainingDataTimeStep.newBuilder();
        for (MotorMonitor m : mon)
            out.addMotorReadings(m.takeReading());
        return out.build();
    }
    
}
