import xml.etree.ElementTree as et


# return the width and
def read_image_size(path: str):
    file = et.parse(path)
    size = file.findall("size")[0]
    width = int(size[0].text)
    height = int(size[1].text)
    return width, height


# read the bndbox annotation data from .txt annotation
def read_annotation_data(path: str):
    bndbox = []
    file = et.parse(path)
    for object in file.findall("object"):
        box = object.findall("bndbox")[0]
        xmin = int(box[0].text)
        ymin = int(box[1].text)
        xmax = int(box[2].text)
        ymax = int(box[3].text)
        bndbox.append((xmin, ymin, xmax, ymax))

    return bndbox


# write xml tag to the given file
def write_xml_tag(filepath: str, filename: str, image_size, bndbox):
    with open(filepath, "w") as file:
        file.write('<?xml version="1.0"?>')
        file.write('<annotation>')

        file.write('<folder>Image</folder>')
        file.write('<filename>' + filename + '</filename>')

        file.write('<source><database>ImageNet</database></source>')

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
