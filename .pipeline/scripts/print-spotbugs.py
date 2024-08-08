#!/usr/bin/env python3

import os
import sys
import glob
import yaml
import xml.etree.ElementTree as ET

findings = {
    '1': 0,
    '2': 0,
    '3': 0,
}

config = None
with open(".pipeline/config.yml", "r") as stream:
    try:
        config = yaml.safe_load(stream)
    except yaml.YAMLError as exc:
        print(exc)

threshold_low = int(config['stages']['codeCheck']['spotbugs']['low'])
threshold_normal = int(config['stages']['codeCheck']['spotbugs']['normal'])
threshold_high = int(config['stages']['codeCheck']['spotbugs']['high'])


all_spotbugs_report_files = glob.glob('**/spotbugsXml.xml', recursive=True)
for spotbugs_report_file in all_spotbugs_report_files:
    if os.path.isfile(spotbugs_report_file):

        parsed_spotbugs_report = ET.parse(spotbugs_report_file)
        spotbugs_report = parsed_spotbugs_report.getroot()

        for bugInstance in spotbugs_report.findall('.//BugInstance'):
            findings[str(bugInstance.get('priority'))] += 1
            if bugInstance.get('priority') == '1':
                print('  - Bug Type:', bugInstance.get('type'))
                print('    Bug Category:', bugInstance.get('category'))
                print('    Bug Priority:', bugInstance.get('priority'))
                print('    Bug Message:', bugInstance.find('LongMessage').text)
                print('    Source File:', bugInstance.find('SourceLine').get('sourcefile'))
                print('    Source Line Number:', bugInstance.find('SourceLine').get('start'))
                print()

allowed_high = threshold_high if threshold_high >= 0 else 'unlimited'
allowed_normal = threshold_normal if threshold_normal >= 0 else 'unlimited'
allowed_low = threshold_low if threshold_low >= 0 else 'unlimited'

if 'GITHUB_STEP_SUMMARY' in os.environ:
    with open(os.environ["GITHUB_STEP_SUMMARY"], "a") as f:
        print('## Spotbugs Result', file=f)
        print('| Category | Actual Findings | Allowed Findings |', file=f)
        print('| -------- | --------------- | ---------------- |', file=f)
        print(f"| High   | {findings['1']} | {allowed_high} |", file=f)
        print(f"| Normal | {findings['2']} | {allowed_normal} |", file=f)
        print(f"| Low    | {findings['3']} | {allowed_low} |", file=f)

print('Spotbugs result:')
print(f"warnings high:   {findings['1']}, allowed are {allowed_high}")
print(f"warnings normal: {findings['2']}, allowed are {allowed_normal}")
print(f"warnings low:    {findings['3']}, allowed are {allowed_low}")

if threshold_high >= 0 and findings['1'] > threshold_high:
    sys.exit('Spotbugs exceeded threshold for high findings')
elif threshold_normal >= 0 and findings['2'] > threshold_normal:
    sys.exit('Spotbugs exceeded threshold for normal findings')
elif threshold_low >= 0 and findings['3'] > threshold_low:
    sys.exit('Spotbugs exceeded threshold for low findings')
