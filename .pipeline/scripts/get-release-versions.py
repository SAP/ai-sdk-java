import os
import re
import xml.etree.ElementTree as xml
import subprocess
import unittest

def determine_versions(current_snapshot, input_version):

    milestone_version_regex = r'^\d+\.\d+\.\d+-M\d+$'

    if not input_version:
        input_version = re.sub(r'-SNAPSHOT', '', current_snapshot)
    elif re.match(milestone_version_regex, input_version):
        return {'input_version': input_version, 'current_snapshot': current_snapshot, 'new_snapshot_version': current_snapshot}

    version_parts = [int(x) for x in input_version.split('.')]
    new_snapshot_version = f"{version_parts[0]}.{version_parts[1] + 1}.0-SNAPSHOT"

    return {'input_version': input_version, 'current_snapshot': current_snapshot, 'new_snapshot_version': new_snapshot_version}

class TestDetermineVersions(unittest.TestCase):

    def test_no_input_provided(self):
        self.assertEqual(determine_versions('1.2.3-SNAPSHOT', None),
                         {'input_version': '1.2.3', 'current_snapshot': '1.2.3-SNAPSHOT','new_snapshot_version': '1.3.0-SNAPSHOT'})

    def test_input_provided(self):
        self.assertEqual(determine_versions('1.2.3-SNAPSHOT', '2.1.0'),
                         {'input_version': '2.1.0', 'current_snapshot': '1.2.3-SNAPSHOT','new_snapshot_version': '2.2.0-SNAPSHOT'})

    def test_input_milestone_provided(self):
        self.assertEqual(determine_versions('1.2.3-SNAPSHOT', '1.2.3-M2'),
                         {'input_version': '1.2.3-M2', 'current_snapshot': '1.2.3-SNAPSHOT','new_snapshot_version': '1.2.3-SNAPSHOT'})

def write_versions_github_output():
    tree = xml.parse("pom.xml")
    # get the value of version, which is inside of project
    current_snapshot = tree.find(".//{http://maven.apache.org/POM/4.0.0}version").text
    print(f"Current snapshot: {current_snapshot}")

    input_version = os.environ.get("INPUT_VERSION")

    calculated_versions = determine_versions(current_snapshot, input_version)

    github_output = os.environ.get("GITHUB_OUTPUT")
    with open(github_output, "a") as f:
        f.write(f"RELEASE_VERSION={calculated_versions['input_version']}\n")
        f.write(f"CURRENT_SNAPSHOT={calculated_versions['current_snapshot']}\n")
        f.write(f"NEW_SNAPSHOT={calculated_versions['new_snapshot_version']}\n")

if __name__ == '__main__':
    write_versions_github_output()
