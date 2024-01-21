package frc.robot.subsystems;

import frc.robot.nn.proto.NNTrainingData.TrainingDataTimeStep;

public interface Subsystem {
    public TrainingDataTimeStep getTrainingData();
}
