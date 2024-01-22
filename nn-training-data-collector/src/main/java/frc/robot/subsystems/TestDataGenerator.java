package frc.robot.subsystems;

import frc.robot.nn.TrainingDataProto.TrainingDataTimeStep;

public class TestDataGenerator implements Subsystem {

    private MotorMonitor mon;

    public TestDataGenerator(String id) {
        this.mon = new MotorMonitor(id, Math::random, Math::random);
    }

    @Override
    public TrainingDataTimeStep getTrainingData() {
        return TrainingDataTimeStep.newBuilder()
            .addMotorReadings(mon.takeReading())
            .build();
    }
    
}
