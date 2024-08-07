#!/usr/bin/env python3

import os
import sys
import glob
import yaml
import xml.etree.ElementTree as ET

findings = {
    'info': 0,
    'warning': 0,
    'error': 0
}

config = None
with open(".pipeline/config.yml", "r") as stream:
    try:
        config = yaml.safe_load(stream)
    except yaml.YAMLError as exc:
        print(exc)

threshold_low = int(config['stages']['codeCheck']['checkstyle']['low'])
threshold_normal = int(config['stages']['codeCheck']['checkstyle']['normal'])
threshold_high = int(config['stages']['codeCheck']['checkstyle']['high'])

all_checkstyle_report_files = glob.glob('**/checkstyle-result.xml', recursive=True)
for checkstyle_report_file in all_checkstyle_report_files:
    if os.path.isfile(checkstyle_report_file):
        parsed_checkstyle_report = ET.parse(checkstyle_report_file)
        checkstyle_report = parsed_checkstyle_report.getroot()
        for sdk_source_file in checkstyle_report:
            if sdk_source_file.find('error') is not None:
                print(f"File: .{sdk_source_file.attrib['name'].removeprefix(os.getcwd())}")
                for error in sdk_source_file:
                    findings[error.attrib['severity']] += 1
                    print(f"  - Rule: {error.attrib['source']}")
                    print(f"    Severity: {error.attrib['severity']}")
                    print(f"    Message: {error.attrib['message']}")
                    print(f"    Line: {error.attrib['line']}")
                print()

allowed_high = threshold_high if threshold_high >= 0 else 'unlimited'
allowed_normal = threshold_normal if threshold_normal >= 0 else 'unlimited'
allowed_low = threshold_low if threshold_low >= 0 else 'unlimited'

if 'GITHUB_STEP_SUMMARY' in os.environ:
    with open(os.environ["GITHUB_STEP_SUMMARY"], "a") as f:
        print('## Checkstyle Result', file=f)
        print('| Category | Actual Findings | Allowed Findings |', file=f)
        print('| -------- | --------------- | ---------------- |', file=f)
        print(f"| High   | {findings['error']} | {allowed_high} |", file=f)
        print(f"| Normal | {findings['warning']} | {allowed_normal} |", file=f)
        print(f"| Low    | {findings['info']} | {allowed_low} |", file=f)

print('Checkstyle result:')
print(f"warnings high:   {findings['error']}, allowed are {allowed_high}")
print(f"warnings normal: {findings['warning']}, allowed are {allowed_normal}")
print(f"warnings low:    {findings['info']}, allowed are {allowed_low}")

if threshold_high >= 0 and findings['error'] > threshold_high:
    sys.exit('Checkstyle exceeded threshold for high findings')
elif threshold_normal >= 0 and findings['warning'] > threshold_normal:
    sys.exit('Checkstyle exceeded threshold for normal findings')
elif threshold_low >= 0 and findings['info'] > threshold_low:
    sys.exit('Checkstyle exceeded threshold for low findings')