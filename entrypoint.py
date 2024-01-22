print("hello world")

import grpc
import training_data_pb2
import training_data_pb2_grpc

def run():
	with grpc.insecure_channel('localhost:5800') as channel:
		stub = training_data_pb2_grpc.TrainingDataStub(channel)
		for data in stub.GetTrainingData(training_data_pb2.TrainingDataRequest()):
			print(data)


if __name__ == "__main__":
	run()
