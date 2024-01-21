package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.google.protobuf.Timestamp;
import com.google.protobuf.Duration;

import frc.robot.nn.proto.NNTrainingData.MotorInput;
import frc.robot.nn.proto.NNTrainingData.MotorState;
import frc.robot.nn.proto.NNTrainingData.MotorTimeStep;
import frc.robot.util.ObjectConvenience;

public class MotorMonitor {
    private String id;
    private DoubleSupplier x, u;
    private Timestamp last_reading_time;
    private MotorState last_state;
    public MotorMonitor(String id, DoubleSupplier x, DoubleSupplier u) { this.id = id; this.x = x; this.u = u; }

    public MotorTimeStep takeReading() {
        Timestamp now = ObjectConvenience.getTimestamp();
        Duration dur = ObjectConvenience.getDuration(last_reading_time, now);
        double elapsedTime = ObjectConvenience.getElapsedTimeFromDuration(dur);

        double position = this.x.getAsDouble();
        double velocity = (position - this.last_state.getPosition()) / elapsedTime;
        double acceleration = (velocity - this.last_state.getVelocity()) / elapsedTime;
        double jerk = (acceleration - this.last_state.getAcceleration()) / elapsedTime;

        MotorInput latestInput = MotorInput.newBuilder().setVoltage(this.u.getAsDouble()).build();
        MotorState newState = MotorState.newBuilder()
            .setId(this.id)
            .setTimestamp(now)
            .setPosition(position)
            .setVelocity(velocity)
            .setAcceleration(acceleration)
            .setJerk(jerk)
            .build();
        MotorTimeStep newReading = MotorTimeStep.newBuilder()
            .setDuration(dur)
            .setBefore(last_state)
            .setInput(latestInput)
            .setAfter(newState)
            .build();
        this.last_state = newState;
        return newReading;
    }
}