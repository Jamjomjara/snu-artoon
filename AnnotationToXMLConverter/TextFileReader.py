from PIL import Image

# truncate extension from the filename.
# for example, to get 'hello' from 'hello.txt', ext_length becomes 3.
def truncate_extension(filename: str, extension_length: int):
    return filename[:-(extension_length + 1)]


# return the filename with extension.
def concatenate_extension(filename: str, extension: str):
    return filename + "." + extension


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


# write xml tag to the given file
def write_xml_tag(filepath: str, filename: str, image_size, bndbox):
    with open(filepath, "w") as file:
        file.write('<?xml version="1.0"?>')
        file.write('<annotation>')

        file.write('<folder>Image</folder>')
        file.write('<filename>' + filename + '</filename>')

        file.write('<source><database>FDDB</database></source>')

        file.write('<size><width>' + str(image_size[0]) + '</width><height>'
                   + str(image_size[1]) + '</height><depth>3</depth></size>')

        file.write('<segmented>0</segmented>')

        for data in bndbox:
            file.write('<object>')
            file.write('<name>person</name><pose>Unspecified</pose><truncated>0</truncated><difficult>0</difficult>')
            file.write('<bndbox>')
            file.write('<xmin>' + str(data[0]) + '</xmin>')
            file.write('<ymin>' + str(data[1]) + '</ymin>')
            file.write('<xmax>' + str(data[2]) + '</xmax>')
            file.write('<ymax>' + str(data[3]) + '</ymax>')
            file.write('</bndbox></object>')

        file.write('</annotation>')
