# SNU AR Toon Intro
This project aims to develop an Android Webtoon Application consisting of Webtoon mode and AR mode. <br />
WEBTOON MODE <br />
In Webtoon mode, you can select and read the Webtoon (Famous Korean Cartoons.) While scrolling down to the bottom of the Webtoon, the app enters the AR mode. <br />
AR MODE <br />
In AR mode, the app will recognize your face and the ghost graphic would appear and track your face. <br /><br />

# Training
The goal of this training is Face recognition. <br />
For Training, we mainly used the darkflow project translating darknet to Tensorflow.
(For more information, please visit https://github.com/thtrieu/darkflow/) <br />
Two weights were utilized, tiny-yolo-1c and yolo-face. <br />
We modified the tiny YOLO cfg from darknet (refer to https://pjreddie.com/darknet/yolo/) for a 1-class person. (Previously, the model detected 20 classes).
