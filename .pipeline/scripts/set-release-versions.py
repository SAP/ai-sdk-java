import argparse
import os
import re
import sys
from xml.etree import ElementTree

class _CommentedTreeBuilder(ElementTree.TreeBuilder):
    def __init__(self, *args, **kwargs):
        super(_CommentedTreeBuilder, self).__init__(*args, **kwargs)

    def comment(self, data):
        self.start(ElementTree.Comment, {})
        self.data(data)
        self.end(ElementTree.Comment)

def _update_readme(sdk_version):
    _update_file("README.md", r'(maven_central-)(.+)(-blue\.svg)', r'\g<1>%s\g<3>' % sdk_version)
    _update_file("README.md", r'(a:sdk-core%20AND%20v:)(.+\))', r'\g<1>%s)' % sdk_version)

def _update_pom_files(sdk_version):
    _update_version_tags(sdk_version)

    with open("latest.json", "w") as f:
        f.write('{\n    "version": "%s"\n}\n' % sdk_version)


def _update_version_tags(sdk_version):
    for root, dirs, files in os.walk(os.getcwd()):
        for file in files:
            if file == "pom.xml":
                _update_file(os.path.join(root, file), r'(<sdk\.version>)(.*?)(</sdk\.version>)', r'\g<1>%s\g<3>' % sdk_version)


def _update_version_tag(pom_file, sdk_version):
    ElementTree.register_namespace("", "http://maven.apache.org/POM/4.0.0")

    parser = ElementTree.XMLParser(target=_CommentedTreeBuilder())

    try:
        tree = ElementTree.parse(pom_file, parser=parser)
        root = tree.getroot()
        child_to_parent = {c: p for p in root.iter() for c in p}
    except:
        print(f"ERROR: Failed to parse POM file {pom_file}.")
        raise

    namespaces = {'xmlns': 'http://maven.apache.org/POM/4.0.0'}

    version_tags = root.findall(".//xmlns:version", namespaces=namespaces)
    project_version_tags = list(filter(lambda x: child_to_parent[x].tag.endswith("project"), version_tags))

    if len(project_version_tags) == 1:
        project_version_tags[0].text = sdk_version

    parent_tags = root.findall(".//xmlns:parent", namespaces=namespaces)

    if len(parent_tags) == 1:
        parent_tags[0].find("xmlns:version", namespaces=namespaces).text = sdk_version

    # not sure why, but tree.write() (as used in other functions) produces a differently formatted XML declaration...
    # that's why we the file is written in this kind of hacky manner
    with open(pom_file, "w") as f:
        f.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + ElementTree.tostring(root).decode() + "\n")

def _update_file(file_name, search_str, replace_str):
    with open(file_name) as f:
        result = f.read()
        result = re.sub(search_str, replace_str, result)

    with open(file_name, "w") as f:
        f.write(result)

def set_sdk_version(full_version):
    _update_pom_files(full_version)
    if "SNAPSHOT" not in full_version:
        _update_readme(full_version)

if __name__ == '__main__':
    try:
        parser = argparse.ArgumentParser(description='SAP Cloud SDK - Versioning script.')

        parser.add_argument('--version',
                            metavar='VERSION',
                            help='The version to be set.', required=True)
        args = parser.parse_args()

        set_sdk_version(args.version)

    except KeyboardInterrupt:
        sys.exit(1)
