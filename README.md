# SNU AR Toon Intro
The project aims to develop the Android Webtoon Application with Webtoon mode and AR mode.
In the webtoon mode, you can select and see the webtoon. After scrolling down to the bottom of the webtoon, the app enters to the AR mode.
In the AR mode, the app recognize your face and the ghost graphic would track your face.

# Training
The goal of training is Face recognition.
For Training, we mainly used the darkflow project translating darknet to tensorflow.(https://github.com/thtrieu/darkflow/)
There are two weights that we used, tiny-yolo-1c and yolo-face.
We modified the tiny YOLO cfg from darknet(https://pjreddie.com/darknet/yolo/) for 1 class, person. (Previously, the model detected 20 classes).
