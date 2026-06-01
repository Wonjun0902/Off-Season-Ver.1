package frc.robot.subsystems.Indexer;

import static edu.wpi.first.units.Units.*;
import static frc.robot.subsystems.Indexer.IndexerConstants.Configurations.*;
import frc.robot.subsystems.Indexer.IndexerConstants.Configurations.*;
import frc.robot.subsystems.Indexer.IndexerConstants.Configurations.Canranges;

import com.ctre.phoenix6.configs.CANrangeConfiguration;
import com.ctre.phoenix6.configs.FovParamsConfigs;
import com.ctre.phoenix6.configs.ProximityParamsConfigs;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.CAN;
import frc.robot.lib.LazyTalon;
import frc.robot.lib.LazyTalonBuilder;

public class IndexerIOReal implements IndexerIO{

    public LazyTalon indexerLeft, indexerRight;
    public CANrange leftCanrange, rightCanrange;
    
    public IndexerIOReal(){
        indexerLeft = new LazyTalonBuilder(
            0, 0, null, 0, 0);
    }
}
