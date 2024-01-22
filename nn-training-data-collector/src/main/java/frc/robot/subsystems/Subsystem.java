package frc.robot.subsystems;

import frc.robot.nn.TrainingDataProto.TrainingDataTimeStep;

public interface Subsystem {
    public TrainingDataTimeStep getTrainingData();
}
