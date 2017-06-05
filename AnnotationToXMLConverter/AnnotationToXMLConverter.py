import os
import time
from TextFileReader import *


def main():
    # get the base directory
    base_directory = os.getcwd()

    # counter for percentage print
    stage_counter = 0
    total_stage = len(os.listdir(base_directory + "/Annotation"))
    start_time = time.time()

    for filename in os.listdir(base_directory + "/Annotation"):
        # print the percentage.
        stage_counter += 1
        print(stage_counter, "/", total_stage, end=" ", sep=" ")
        print("(", int(stage_counter / total_stage * 100), "%)", sep="")

        # check the given file is a valid txt file.
        if not filename.endswith(".txt"):
            continue

        # get filename without .txt extension
        base_filename = truncate_extension(filename, 3)

        # read data from the file
        image_size = read_image_size(base_directory + "/Image/" + concatenate_extension(base_filename, "jpg"))
        annotation_data = read_annotation_data(base_directory + "/Annotation/"
                                               + concatenate_extension(base_filename, "txt"), image_size)

        # write the xml file
        write_xml_tag(base_directory + "/XML/" + concatenate_extension(base_filename, "xml"),
                      concatenate_extension(base_filename, "jpg"), image_size, annotation_data)

    end_time = time.time()
    print()
    print("%d annotations converted into xml. Total running time: %lf secs." % (total_stage, end_time - start_time))


# main function
if __name__ == "__main__":
    main()