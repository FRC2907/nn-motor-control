from random import random

# Currently only models steady state behavior.
# TODO model acceleration as a response to voltage
class SteadyStateMotor:
	class MotorCharacteristics:
		def __init__(self, V_stiction, Vpmps_ramp):
			self.stiction = V_stiction
			self.ramp = Vpmps_ramp
	
	def __init__(self, V_fwd_stiction, V_rev_stiction, Vpmps_fwd_ramp, Vpmps_rev_ramp, noise_cap):
		self.fwd = SteadyStateMotor.MotorCharacteristics(V_fwd_stiction, Vpmps_fwd_ramp)
		self.rev = SteadyStateMotor.MotorCharacteristics(V_rev_stiction, Vpmps_rev_ramp)
		self.noise_cap = noise_cap
	
	def sim(self, V_input):
		out = 0
		if V_input <= self.rev.stiction:
			out = (V_input - self.rev.stiction) * self.rev.ramp
		elif self.fwd.stiction <= V_input:
			out = (V_input - self.fwd.stiction) * self.fwd.ramp
		out *=  1 + (self.noise_cap * random())
		return out



if __name__ == '__main__':
	from matplotlib import pyplot as plt
	from mpl_toolkits.mplot3d import axes3d
	import numpy as np
	m = SteadyStateMotor(2, -2, 1, 1, 0.05)
	inputs_x = [[]]
	inputs_y = []
	outputs = [[]]
	for V in [x/4 for x in range(-12*4, 12*4)]:
		inputs_x[0].append(V)
		inputs_y.append([0])
		outputs[0].append(m.sim(V))
	fig = plt.figure()
	ax = fig.add_subplot(projection='3d')
	#inputs_x, inputs_y, outputs = axes3d.get_test_data(0.05)
	ax.plot_wireframe(np.array(inputs_x), np.array(inputs_y), np.array(outputs))
	#plt.scatter(inputs, outputs)
	plt.show()


# System inputs: voltage
# System outputs: position, velocity, acceleration

# Model inputs: position, velocity, acceleration, desired pos, des vel, des acc, prev voltage
# Model outputs: voltage
