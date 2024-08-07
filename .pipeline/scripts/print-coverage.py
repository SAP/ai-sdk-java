#!/usr/bin/env python3

import argparse
import csv
import os
import os.path
import sys
import yaml
from glob import glob

def get_jacoco_thresholds():
    with open(".pipeline/config.yml", "r") as stream:
        config = yaml.safe_load(stream)

        return (config['stages']['test']['coverageThresholds']['instruction'],
                config['stages']['test']['coverageThresholds']['branch'],
                config['stages']['test']['coverageThresholds']['line'],
                config['stages']['test']['coverageThresholds']['complexity'],
                config['stages']['test']['coverageThresholds']['method'],
                config['stages']['test']['coverageThresholds']['class'])

def get_threshold_for_summary(threshold):
    return str(threshold) + "%" if threshold >= 0 else 'unlimited'

def write_jacoco_github_output(jacoco_report_pattern):
    jacoco_report_files = glob(jacoco_report_pattern, recursive=True)

    instruction_coverage, branch_coverage, line_coverage, complexity_coverage, method_coverage, class_coverage, classes_covered, number_of_classes = compute_coverage(jacoco_report_files)
    instruction_threshold, branch_threshold, line_threshold, complexity_threshold, method_threshold, class_threshold = get_jacoco_thresholds()

    summary_instruction_threshold = get_threshold_for_summary(instruction_threshold)
    summary_branch_threshold = get_threshold_for_summary(branch_threshold)
    summary_line_threshold = get_threshold_for_summary(line_threshold)
    summary_complexity_threshold = get_threshold_for_summary(complexity_threshold)
    summary_method_threshold = get_threshold_for_summary(method_threshold)
    summary_class_threshold = get_threshold_for_summary(class_threshold)

    if 'GITHUB_STEP_SUMMARY' in os.environ:
        with open(os.environ["GITHUB_STEP_SUMMARY"], "a") as f:
            print('## JaCoCo Result', file=f)
            print('| Category | Actual Coverage | Coverage Threshold |', file=f)
            print('| --------------- | --------------- | ------------------ |', file=f)
            print(f"| Class Coverage | {class_coverage}% ({classes_covered}/{number_of_classes} covered) | {summary_class_threshold} |", file=f)
            print(f"| Method Coverage | {method_coverage}% | {summary_method_threshold} |", file=f)
            print(f"| Line Coverage | {line_coverage}% | {summary_line_threshold} |", file=f)
            print(f"| Complexity Coverage | {complexity_coverage}% | {summary_complexity_threshold} |", file=f)
            print(f"| Branch Coverage | {branch_coverage}% | {summary_branch_threshold} |", file=f)
            print(f"| Instruction Coverage | {instruction_coverage}% | {summary_instruction_threshold} |", file=f)

    print(f"Class Coverage: {class_coverage}% ({classes_covered}/{number_of_classes}), Class Threshold: {summary_class_threshold}%")
    print(f"Method Coverage: {method_coverage}%, Method Threshold: {summary_method_threshold}%")
    print(f"Line Coverage: {line_coverage}%, Line Threshold: {summary_line_threshold}%")
    print(f"Complexity Coverage: {complexity_coverage}%, Complexity Threshold: {summary_complexity_threshold}%")
    print(f"Branch Coverage: {branch_coverage}%, Branch Threshold: {summary_branch_threshold}%")
    print(f"Instruction Coverage: {instruction_coverage}%, Instruction Threshold: {summary_instruction_threshold}%")

    if instruction_threshold >= 0 and instruction_coverage < instruction_threshold:
        sys.exit('Instruction coverage below threshold')
    elif branch_threshold >= 0 and branch_coverage < branch_threshold:
        sys.exit('Branch coverage below threshold')
    elif line_threshold >= 0 and line_coverage < line_threshold:
        sys.exit('Line coverage below threshold')
    elif complexity_threshold >= 0 and complexity_coverage < complexity_threshold:
        sys.exit('Complexity coverage below threshold')
    elif method_threshold >= 0 and method_coverage < method_threshold:
        sys.exit('Method coverage below threshold')
    elif class_threshold >= 0 and class_coverage < class_threshold:
        sys.exit('Class coverage below threshold')

def compute_coverage(file_list) :
    missed_instructions = 0
    covered_instructions = 0
    missed_branches = 0
    covered_branches = 0
    missed_lines = 0
    covered_lines = 0
    complexity_missed = 0
    complexity_covered = 0
    method_missed = 0
    method_covered = 0
    classes_missed = 0
    classes_covered = 0
    for filename in file_list :
        with open(filename, newline='') as csv_file :
            jacoco_reader = csv.reader(csv_file)
            local_classes_missed = 0
            local_classes_covered = 0
            for i, row in enumerate(jacoco_reader) :
                if i > 0 :
                    missed_instructions += int(row[3])
                    covered_instructions += int(row[4])
                    missed_branches += int(row[5])
                    covered_branches += int(row[6])
                    missed_lines += int(row[7])
                    covered_lines += int(row[8])
                    complexity_missed += int(row[9])
                    complexity_covered += int(row[10])
                    method_missed += int(row[11])
                    method_covered += int(row[12])
                    if int(row[8]) == 0:
                        local_classes_missed += 1
                    else:
                        local_classes_covered += 1
            classes_missed += local_classes_missed
            classes_covered += local_classes_covered
            print(f"{filename} {local_classes_missed + local_classes_covered} classes")

    return (calculate_percentage(covered_instructions, missed_instructions),
            calculate_percentage(covered_branches, missed_branches),
            calculate_percentage(covered_lines, missed_lines),
            calculate_percentage(complexity_covered, complexity_missed),
            calculate_percentage(method_covered, method_missed),
            calculate_percentage(classes_covered, classes_missed),
            classes_covered,
            classes_covered + classes_missed)

def calculate_percentage(covered, missed) :
    if covered == 0 and missed == 0:
        sys.exit('No coverage data found')
    return int(covered * 100 / (covered + missed))

def main():
    parser: argparse.ArgumentParser = argparse.ArgumentParser(
        description="Parses code coverage reports and generates a Github Actions step summary.")
    parser.add_argument("--jacoco-report-pattern",
                        help="Glob pattern for JaCoCo reports in CSV format.",
                        required=True)
    args = parser.parse_args()

    write_jacoco_github_output(args.jacoco_report_pattern)

if __name__ == '__main__':
    main()
