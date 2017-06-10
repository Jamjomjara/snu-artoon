from PIL import Image


# return the width and
def read_image_size(path: str):
    with Image.open(path) as image:
        return image.size


# read the bndbox annotation data from .txt annotation
def read_annotation_data(image_size):
    bndbox = []
    xmin = int(0.2 * image_size[0])
    ymin = int(0.2 * image_size[1])
    xmax = int(0.8 * image_size[0])
    ymax = int(0.8 * image_size[1])
    bndbox.append((xmin, ymin, xmax, ymax))

    return bndbox


# write xml tag to the given file
def write_xml_tag(filepath: str, filename: str, image_size, bndbox):
    with open(filepath, "w") as file:
        file.write('<?xml version="1.0"?>')
        file.write('<annotation>')

        file.write('<folder>Image</folder>')
        file.write('<filename>' + filename + '</filename>')

        file.write('<source><database>GoogleImage</database></source>')

        file.write('<size><width>' + str(image_size[0]) + '</width><height>'
                   + str(image_size[1]) + '</height><depth>3</depth></size>')

        file.write('<segmented>0</segmented>')

        for data in bndbox:
            file.write('<object>')
            file.write('<name>banana</name><pose>Unspecified</pose><truncated>0</truncated><difficult>0</difficult>')
            file.write('<bndbox>')
            file.write('<xmin>' + str(data[0]) + '</xmin>')
            file.write('<ymin>' + str(data[1]) + '</ymin>')
            file.write('<xmax>' + str(data[2]) + '</xmax>')
            file.write('<ymax>' + str(data[3]) + '</ymax>')
            file.write('</bndbox></object>')

        file.write('</annotation>')
