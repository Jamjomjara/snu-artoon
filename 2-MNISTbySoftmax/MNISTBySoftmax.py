import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data


# Constants:
TRAINING_NUMBER = 1_000


# 1. download MNIST data set.
# The downloaded data set has 3 types of data - training, test and validation.
mnist_data = input_data.read_data_sets("MNIST_data/", one_hot=True)


# 2. Design our model.
# Our Softmax model has y = Wx + b model where
#   y: th-e model output
#   x: the pixel inputs of every image.
#       each image is 28*28 so an image can be expressed as a tensor of [784]
#       and there are lots of images in a set, so x can be expressed as [None, 784]
#       where 'None' in TensorFlow means undefined number.
#   W: the evidence of each pixel to become each number.
#       each image has 784 pixels, and they can be an evidence of 10 numbers each.
#       therefore W is expressed in a tensor of [784, 10]
#       (each image pixel representing evidence-level of becoming each number)
#
#   b: there exists a 'biased' probability of becoming each number, therefore we add this 'b' to represent that.
#       therefore b is a tensor of [10].

# our model's form is Wx + b.
# but, to get "images' evidence to become each number" we should multiply matrix 'x * W'.
#   x[None, 784] * W[784, 10] = [None, 10] (meaning each images' evidence-level for becoming each number.)

# in this model, our goal is to compute W and b for given training set of (x, y) to make an model to identify number,
# so x becomes a placeholder while W and b becomes Variable.
x = tf.placeholder(tf.float32, [None, 784])
W = tf.Variable(tf.zeros([784, 10]))
b = tf.Variable(tf.zeros([10]))
y = tf.matmul(x, W) + b


# 3. Apply Softmax algorithm.
# the code above counts the 'evidence-level' of each image to become a specific number.
# 'evidence-level' itself means nothing, so we have to transform 'evidence-level' into 'probability.'
# Softmax algorithm first use exponential function to deal with negative evidence-level.
#   c.f., exp(negative) always results in positive number.
# And then softmax algorithm uses these resulting numbers to compute probability of a given image
# to become a specific number.
#       Remember 'Softmax' algorithm as softmax(x) = normalize(exp(x)) => which returns 'probability'
computed_number_probability = tf.nn.softmax(y)


# 4. design loss function.
# In this example let's use cross-entropy function as our loss function.
#   cross_entropy = sum(real(i) * -log(computed(i)))
# as we use one-hot encoding in this example, only one bit will be 1(and other will be 0) in real(i) case.
# therefore, as real(i) and computed(i) differs when real(i) = 1, the loss function will be so large.
#   e.g., imagine the number was 1.
#   the real distribution will be [0, 1, 0, 0, 0, 0, 0, 0, 0, 0] as it is one-hot encoded.
#   if the predicted distribution was absolutely correct,
#   then the cross-entropy will be 1 * -log(1) = 0.
#   however, if the distribution is totally wrong(worst case: probability to become 1 is 0)
#   the cross-entropy will be 1 * -log(0) = inf.
#   therefore, this cross-entropy can be loss function in our example.
real_number_probability = tf.placeholder(tf.float32, [None, 10])  # one-hot encoded

# we have to sum cross_entropy per image, not per number.
# therefore reduction_indices was set to [1] to sum the values per each image.
cross_entropy_each_numbers = tf.reduce_sum(real_number_probability * -tf.log(computed_number_probability),
                                           reduction_indices=[1])
total_cross_entropy = tf.reduce_mean(cross_entropy_each_numbers)


# 5. Get W and b by ML.
# Let's train our machine.
optimizer = tf.train.GradientDescentOptimizer(0.5)
train = optimizer.minimize(total_cross_entropy)
sess = tf.Session()
sess.run(tf.global_variables_initializer())
for i in range(1, TRAINING_NUMBER + 1):
    # for each step, let's use 100 arbitrary MNIST digit sets for x and real_number_probability.
    batch_x, batch_y = mnist_data.train.next_batch(100)
    sess.run(train, {x: batch_x, real_number_probability: batch_y})
    print("%.1lf%% Training..." % ((i / TRAINING_NUMBER) * 100))


# 6. Evaluate our y = Wx + b softmax model.

# (1) by given test data, check whether our model guessed right.
# to do that, we get the column of maximum probability (per image), and compare them.
is_prediction_correct = tf.equal(tf.argmax(computed_number_probability, 1), tf.argmax(real_number_probability, 1))

# (2) if our model guessed correct, let's consider them score 1 - and if wrong, they are 0.
prediction_score = tf.cast(is_prediction_correct, tf.float32)

# (3) get the average of score.
average_score = tf.reduce_mean(prediction_score)

# (4) print the score.
score = sess.run(average_score, {x: mnist_data.test.images, real_number_probability: mnist_data.test.labels})
print("\n-----RESULT-----")
print("%0.2lf%% Accurate!" % (score * 100))
