# SNU AR Toon Intro
This project aims to develop an Android Webtoon Application consisting of Webtoon mode and AR mode.
WEBTOON MODE
In Webtoon mode, you can select and read the Webtoon (Famous Korean Cartoons.) While scrolling down to the bottom of the Webtoon, the app enters the AR mode.
AR MODE
In AR mode, the app will recognize your face and the ghost graphic would appear and track your face.

# Training
The goal of this training is Face recognition.
For Training, we mainly used the darkflow project translating darknet to Tensorflow. 
(For more information, please visit https://github.com/thtrieu/darkflow/)
Two weights were utilized, tiny-yolo-1c and yolo-face.
We modified the tiny YOLO cfg from darknet (refer to https://pjreddie.com/darknet/yolo/) for a 1-class person. (Previously, the model detected 20 classes).
