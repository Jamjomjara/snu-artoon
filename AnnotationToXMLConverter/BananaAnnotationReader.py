import xml.etree.ElementTree as et


# return the width and
def read_image_size(path: str):
    with et.parse(path) as file:
        width = int(file.find("width").text)
        height = int(file.find("height").text)
        return width, height


# read the bndbox annotation data from .txt annotation
def read_annotation_data(path: str, image_size):
    bndbox = []
    with et.parse(path) as file:
        for box in file.findall(bndbox):
            xmin = int(box[0].text)
            ymin = int(box[1].text)
            xmax = int(box[2].text)
            ymax = int(box[3].text)
            bndbox.append((xmin, ymin, xmax, ymax))

    return bndbox
