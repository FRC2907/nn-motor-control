package frc.robot.util;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.google.protobuf.Duration;
import com.google.protobuf.Timestamp;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;


public class ObjectConvenience {

    public static TalonFX createTalonGroup(int[] ids) {
        TalonFX[] mcs = new TalonFX[ids.length];
        for (int i = 0; i < ids.length; i++) {
            mcs[i] = new TalonFX(ids[i]);
            if (i > 0) mcs[i].setControl(new Follower(ids[0], false));
        }
        return mcs[0];
    }

    public static CANSparkMax createSparkGroup(int[] ids) {
        CANSparkMax[] mcs = new CANSparkMax[ids.length];
        for (int i = 0; i < ids.length; i++) {
            mcs[i] = new CANSparkMax(ids[i], MotorType.kBrushless);
            if (i > 0) mcs[i].follow(mcs[0]);
        }
        return mcs[0];
    }

    public static Timestamp getTimestamp() {
        long millis = System.currentTimeMillis();
        return Timestamp.newBuilder()
                .setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000))
                .build();
    }

    public static Duration getDuration(Timestamp before, Timestamp after) {
        return Duration.newBuilder()
            .setSeconds(after.getSeconds() - before.getSeconds())
            .setNanos(after.getNanos() - before.getNanos())
            .build();
    }

    public static double getElapsedTimeFromDuration(Duration d) {
        return d.getSeconds() + d.getNanos() / 1e-9;
    }
}
