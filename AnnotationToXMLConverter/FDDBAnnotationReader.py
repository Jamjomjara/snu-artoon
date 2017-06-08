from PIL import Image


# return the width and
def read_image_size(path: str):
    with Image.open(path) as image:
        return image.size


# read the bndbox annotation data from .txt annotation
def read_annotation_data(path: str, image_size):
    bndbox = []
    with open(path, "r") as file:
        lines = file.readlines()
        for line in lines:
            data = line.split(" ")
            for i in range(len(data)):
                data[i] = float(data[i])
            xmin = int((data[1] - data[3] / 2) * image_size[0])
            ymin = int((data[2] - data[4] / 2) * image_size[1])
            xmax = int((data[1] + data[3] / 2) * image_size[0])
            ymax = int((data[2] + data[4] / 2) * image_size[1])
            bndbox.append((xmin, ymin, xmax, ymax))

    return bndbox
