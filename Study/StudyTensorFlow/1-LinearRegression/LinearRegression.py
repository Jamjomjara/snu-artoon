import tensorflow as tf


# TensorFlow Example (1) - Linear Regression
#
# (model) y = ax + b
# By giving some pairs of (x, y) that satisfies the given model,
# TensorFlow can compute the value of 'a' and 'b'
# by using very simple Machine Learning(ML) algorithm.


# 1. implementing our model.
# TensorFlow has an element called 'node'.
# A 'node' can be formed from a Tensor(i.e. values),
# or by combining nodes with arithmetic operations.
# We should implement our model (y = ax + b) first.

# There are a few types of values.
# 1. constants: values which cannot change.
# 2. placeholders: values that we should give when computing.
# 3. variables: values that can be changed while computing.
# therefore, in our model y = ax + b
# 'x' is given by us so it should be 'placeholder',
# and 'a' and 'b' is computed by TensorFlow, which therefore
# should be variables.
x = tf.placeholder(tf.float32)
a = tf.Variable([1.0], tf.float32)
b = tf.Variable([1.0])  # data type inferred automatically

model_y = a * x + b  # same with 'y = tf.add(tf.multiply(a, x), b)'


# 2. let the computer know our goal.
# To compute 'a' and 'b' value using ML,
# we should let machine know what is their goal.
# in this case, the computation result of the model should be
# the same with real value(which is given by us.)
# to accomplish this goal, we design a function(which is called
# 'loss function'), and the goal of the machine is to minimize
# the value of loss function.
real_y = tf.placeholder(tf.float32)
error = model_y - real_y
squared_error = tf.square(error)  # make all errors positive to compute average
sum_error = tf.reduce_sum(squared_error)  # this is our loss function whose value should be minimized.


# 3. compute 'a' and 'b' value using ML.
# now we designed our model and the goal of the machine.
# therefore, now what we have to do is just command the machine
# to find the value 'a' and 'b' that minimizes our loss function(sum_error)
# to do that, we give our machine some data sets.
# (the exact (x, y) pairs to compute 'a' and 'b' values.
x_training_data = [1, 2, 3, 4]
y_training_data = [3, 5, 7, 9]  # y = 2x + 1 is the correct model

# to run a TensorFlow computation, we need something called 'Session'.
session = tf.Session()

# first, make all the Variables to be set to its initial value(which are wrong)
session.run(tf.global_variables_initializer())

# then, make machine to compute the right 'a' and 'b' value.
optimizer = tf.train.GradientDescentOptimizer(0.01)  # Machine's algorithm to find 'a' and 'b'
train = optimizer.minimize(sum_error)


for _ in range(10000):
    session.run(train, {x: x_training_data, real_y: y_training_data})


# 4. Machine finished computing 'a' and 'b' value.
# this code below will print out that values.
a, b = session.run([a, b])
print("a :", a)
print("b :", b)
