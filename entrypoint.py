print("hello world")

import grpc
from google.protobuf import text_format
import training_data_pb2
import training_data_pb2_grpc

def run():
	with grpc.insecure_channel('localhost:5800') as channel, open('/tmp/nn-data', 'w') as file:
		stub = training_data_pb2_grpc.TrainingDataStub(channel)
		while True:
			for data in stub.GetTrainingData(training_data_pb2.TrainingDataRequest()):
				print(data)
				text_format.PrintMessage(data, file)


if __name__ == "__main__":
	run()
