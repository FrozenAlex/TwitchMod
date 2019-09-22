import os
import re


with open('list.txt', 'r') as f:
    REPLACEMENTS = [(line.partition("=")[0], line.partition("=")[2]) for line in f.read().splitlines()]


def process(file_path):
    content = None

    with open(file_path, 'r') as f:
        content = f.read()

    new_content = content[::]

    if content is None:
        return

    for replacement in REPLACEMENTS:
        new_content = new_content.replace(replacement[0], replacement[1])

    if (new_content != content):
        print(file_path)

        with open(file_path, 'w') as f:
            f.write(new_content)


if __name__ == '__main__':
    tree = os.walk(r'{{path}}')
    for root, dirs, files in tree:
        for file in files:
            path = os.path.join(root, file)
            process(path)

    print('Done!')
    input()