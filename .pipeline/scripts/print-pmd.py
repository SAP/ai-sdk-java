#!/usr/bin/env python3

import os
import sys
import glob
import yaml
import xml.etree.ElementTree as ET

# 1 is highest, 5 is lowest, cf https://pmd.sourceforge.io/pmd-5.3.3/customizing/rule-guidelines.html
findings = {
    '1': 0,
    '2': 0,
    '3': 0,
    '4': 0,
    '5': 0,
}

config = None
with open(".pipeline/config.yml", "r") as stream:
    try:
        config = yaml.safe_load(stream)
    except yaml.YAMLError as exc:
        print(exc)

threshold_high = int(config['stages']['codeCheck']['pmd']['high'])
threshold_normal = int(config['stages']['codeCheck']['pmd']['normal'])
threshold_low = int(config['stages']['codeCheck']['pmd']['low'])

all_pmd_report_files = glob.glob('**/pmd.xml', recursive=True)
for pmd_report_file in all_pmd_report_files:
    if os.path.isfile(pmd_report_file):
        parsed_pmd_report = ET.parse(pmd_report_file)
        pmd_report = parsed_pmd_report.getroot().findall('{http://pmd.sourceforge.net/report/2.0.0}file')
        for sdk_source_file in pmd_report:
            print(f"File: .{sdk_source_file.attrib['name'].removeprefix(os.getcwd())}")
            for violation in sdk_source_file.findall('{http://pmd.sourceforge.net/report/2.0.0}violation'):
                ET.dump(violation)
                findings[str(violation.attrib['priority'])] += 1
                print(f"  - Rule: {violation.attrib['rule']}")
                print(f"    Priority: {violation.attrib['priority']}")
                print(f"    Message: {violation.text.strip()}")
                print(f"    Line: {violation.attrib['beginline']}")
            print()

high_findings = findings['1'] + findings['2']
normal_findings = findings['3']
low_findings = findings['4'] + findings['5']

allowed_high = threshold_high if threshold_high >= 0 else 'unlimited'
allowed_normal = threshold_normal if threshold_normal >= 0 else 'unlimited'
allowed_low = threshold_low if threshold_low >= 0 else 'unlimited'

if 'GITHUB_STEP_SUMMARY' in os.environ:
    with open(os.environ["GITHUB_STEP_SUMMARY"], "a") as f:
        print('## PMD Result', file=f)
        print('| Category | Actual Findings | Allowed Findings |', file=f)
        print('| -------- | --------------- | ---------------- |', file=f)
        print(f"| High   | {high_findings} | {allowed_high} |", file=f)
        print(f"| Normal | {normal_findings} | {allowed_normal} |", file=f)
        print(f"| Low    | {low_findings} | {allowed_low} |", file=f)

print('pmd result:')
print(f"warnings high:   {high_findings}, allowed are {allowed_high}")
print(f"warnings normal: {normal_findings}, allowed are {allowed_normal}")
print(f"warnings low:    {low_findings}, allowed are {allowed_low}")

if threshold_high >= 0 and high_findings > threshold_high:
    sys.exit('PMD exceeded threshold for high findings')
elif threshold_normal >= 0 and normal_findings > threshold_normal:
    sys.exit('PMD exceeded threshold for normal findings')
elif threshold_low >= 0 and low_findings > threshold_low:
    sys.exit('PMD exceeded threshold for low findings')
