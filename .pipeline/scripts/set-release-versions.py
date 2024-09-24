import argparse
import os
import re
import sys
import xml.etree.ElementTree as xml

def _update_readme(sdk_version):
    _update_file("README.md", r'(maven_central-)(.+)(-blue\.svg)', r'\g<1>%s\g<3>' % sdk_version)
    _update_file("README.md", r'(a:sdk-core%20AND%20v:)(.+\))', r'\g<1>%s)' % sdk_version)

def _update_pom_file(sdk_version):
    namespaces = {'xmlns': 'http://maven.apache.org/POM/4.0.0'}
    # remove the prefix from all tags: ns0:version->version
    xml.register_namespace("", "http://maven.apache.org/POM/4.0.0")

    tree = xml.parse("pom.xml")
    root = tree.getroot()
    version_tag = root.find(".//xmlns:version", namespaces)
    version_tag.text = sdk_version
    tree.write("pom.xml", encoding="UTF-8")

def _update_file(file_name, search_str, replace_str):
    with open(file_name) as f:
        result = f.read()
        result = re.sub(search_str, replace_str, result)

    with open(file_name, "w") as f:
        f.write(result)

def set_sdk_version(sdk_version):
    _update_pom_file(sdk_version)
    if "SNAPSHOT" not in sdk_version:
        _update_readme(sdk_version)

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
