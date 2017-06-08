# truncate extension from the filename.
# for example, to get 'hello' from 'hello.txt', ext_length becomes 3.
def truncate_extension(filename: str, extension_length: int):
    return filename[:-(extension_length + 1)]


# return the filename with extension.
def concatenate_extension(filename: str, extension: str):
    return filename + "." + extension