from matplotlib import pyplot as plt

# https://matplotlib.org/stable/api/_as_gen/matplotlib.pyplot.subplot.html
# https://matplotlib.org/stable/api/pyplot_summary.html


xs = [1,2,3,4,5,6,7,8,9]
ys = [8,6,7,5,3,0,9,9,9]

ax = plt.subplot(111, projection = 'rectilinear', polar = False, label = 'Sample scatter plot')
#plt.plot(xs, ys)
plt.scatter(xs, ys)
plt.show()
