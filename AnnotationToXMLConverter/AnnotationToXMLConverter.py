import os
import time
import FileManager
import FDDBAnnotationReader as fddb
import BananaAnnotationReader as banana

def main():
    # get the base directory
    base_directory = os.getcwd()

    # counter for percentage print
    stage_counter = 0
    total_stage = len(os.listdir(base_directory + "/Annotation"))

    print("Select Datasource.")
    print("1 - FDDB")
    print("2 - Banana")
    datasource_selection = input("Datasource # : ")

    start_time = time.time()

    for filename in os.listdir(base_directory + "/Annotation"):
        # print the percentage.
        stage_counter += 1
        print(stage_counter, "/", total_stage, end=" ", sep=" ")
        print("(", int(stage_counter / total_stage * 100), "%)", sep="")

        # check the given file is a valid txt file.
        if not filename.endswith(".txt"):
            continue

        image_size = None
        annotation_data = None

        base_filename = FileManager.truncate_extension(filename, 3)

        # read data from the file
        if datasource_selection == 1:
            image_size = fddb.read_image_size(base_directory + "/Image/" + FileManager.concatenate_extension(base_filename, "jpg"))
            annotation_data = fddb.read_annotation_data(base_directory + "/Annotation/"
                                                   + FileManager.concatenate_extension(base_filename, "txt"), image_size)

        elif datasource_selection == 2:
            image_size = banana.read_image_size(
                base_directory + "/Image/" + FileManager.concatenate_extension(base_filename, "jpg"))
            annotation_data = banana.read_annotation_data(base_directory + "/Annotation/"
                                                        + FileManager.concatenate_extension(base_filename, "txt"), image_size)

        # write the xml file
        FileManager.write_xml_tag(base_directory + "/XML/" + FileManager.concatenate_extension(base_filename, "xml"),
                                  FileManager.concatenate_extension(base_filename, "jpg"), image_size, annotation_data)

    end_time = time.time()
    print()
    print("%d annotations converted into xml. Total running time: %lf secs." % (total_stage, end_time - start_time))


# main function
if __name__ == "__main__":
    main()