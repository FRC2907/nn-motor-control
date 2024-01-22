// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.nn.TrainingDataCollection;
import frc.robot.nn.TrainingDataServer;
import frc.robot.subsystems.Subsystem;
//import frc.robot.subsystems.Arm;
//import frc.robot.subsystems.TankDrivetrain;
import frc.robot.subsystems.TestDataGenerator;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private Subsystem[] subsystems = {
    //TankDrivetrain.getInstance()
    //, Arm.getInstance()
    new TestDataGenerator("testy_mctestface")
  };
  private TrainingDataCollection data_for_export;

  @Override
  public void robotInit() {
    this.data_for_export = new TrainingDataCollection();
    new TrainingDataServer(5800, this.data_for_export).start();
  }

  @Override
  public void robotPeriodic() {
    for (Subsystem s : subsystems)
    this.data_for_export.add(s.getTrainingData());
  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {}

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
