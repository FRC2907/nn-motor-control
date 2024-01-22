package frc.robot.nn;

import frc.robot.nn.TrainingDataProto.TrainingDataRequest;
import frc.robot.nn.TrainingDataProto.TrainingDataTimeStep;

import frc.robot.nn.TrainingDataGrpc.TrainingDataImplBase;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class TrainingDataServer {

    private class TrainingDataService extends TrainingDataImplBase {
        private TrainingDataCollection data_source;
        TrainingDataService(TrainingDataCollection data_source) { this.data_source = data_source; }


        @Override
        public void getTrainingData(TrainingDataRequest unused, StreamObserver<TrainingDataTimeStep> responseObserver) {
            while (!data_source.isEmpty())
                responseObserver.onNext(data_source.pop());
            responseObserver.onCompleted();
        }
    }

    private final int port;
    private final Server server;

    public TrainingDataServer(int port, TrainingDataCollection data_source) {
        this(Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create()), port, data_source);
    }

    public TrainingDataServer(ServerBuilder<?> serverBuilder, int port, TrainingDataCollection data_source) {
        this.port = port;
        this.server = serverBuilder.addService(new TrainingDataService(data_source)).build();
    }

    public void start() {
        try {
            this.server.start();
            System.out.println("TrainingDataServer listening on port " + this.port);
        } catch (Exception e) {
            System.out.println("[EE] Failed to start TrainingDataServer on port " + this.port);
            e.printStackTrace();
        }
    }
}
