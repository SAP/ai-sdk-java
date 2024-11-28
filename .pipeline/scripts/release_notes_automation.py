import argparse
import os
import re
from datetime import datetime


def read_file(file_name):
    with open(file_name, 'r', encoding="utf8") as file:
        return file.read()


def write_file(file_name, data):
    with open(file_name, 'w', encoding="utf8") as file:
        file.write(data)

unchanged_sections = [
    """### 🚧 Known Issues
    
    -
    
    """,
    """### 🔧 Compatibility Notes
    
    -
    
    """,
    """### ✨ New Functionality
    
    -
    
    """,
    """### 📈 Improvements
    
    -
    
    """,
    """### 🐛 Fixed Issues
    
    - 
    """]

def remove_unchanged_sections(file, unchanged_sections):
    for unchanged_section in unchanged_sections:
        # if file contains unchanged_section, remove it
        file = re.sub(unchanged_section, "", file)
    return file

def set_header(file, version):
    date = datetime.today().strftime('%B %d, %Y')
    # Replace the first line with: ## 5.2.0 - January 17, 2024
    file = re.sub("^## .*", "## " + version + " - " + date, file)
    return file

def link_github_release(file, version):
    old_github_release_link = "\[All Release Changes\]\(https://github.com/SAP/ai-sdk-java/releases/\)"
    new_github_release_link = "[All Release Changes](https://github.com/SAP/cloud-sdk-java/releases/tag/rel%2F"+version+")"
    file = re.sub(old_github_release_link, new_github_release_link, file)
    return file



releases_pattern = re.compile(r"^## ")
def count_releases(filename):
    count = 0
    with open(filename, 'r', encoding="utf-8") as file:
        for line in file:
            if releases_pattern.match(line):
                count += 1
    return count

def find_target_file(version):
    # release-notes-X-to-Y.mdx with every 15 versions the index increases by 15 and stays the same for 15 versions
    minor_version = int(version.split(".")[1])
    index = minor_version // 15 * 15
    return "release-notes-" + str(index) + "-to-" + str(index + 14) + ".mdx"

def write_release_notes(folder, target_file):
    absolute_target_file = os.path.join(folder, target_file)

    # if target_file is a file, prepend the new release notes at the top
    if os.path.isfile(absolute_target_file):
        existing_file = read_file(absolute_target_file)
        write_file(absolute_target_file, file + "\n" + existing_file)
    # if target_file is not a file, create it
    else:
        write_file(absolute_target_file, file)


file_name = "docs/release-notes/release_notes.md"

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='SAP Cloud SDK for AI (for Java) - Release Notes formatting script.')

    parser.add_argument('--version', metavar='VERSION', help='The version to be released.', required=True)
    parser.add_argument('--folder', metavar='FOLDER', help='The ai-sdk-java/docs/release-notes folder.', required=True)
    args = parser.parse_args()

    file = read_file(file_name)
    file = remove_unchanged_sections(file, unchanged_sections)
    file = set_header(file, args.version)
    file = link_github_release(file, args.version)

    target_file = find_target_file(args.version)


    folder_path = args.folder
    write_release_notes(folder_path, target_file)

    # delete (temporary) release-notes file so it does not appear in the released version
    os.remove(file_name)
