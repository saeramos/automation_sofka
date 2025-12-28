#!/bin/bash

JMETER_HOME=${JMETER_HOME:-"$HOME/apache-jmeter-5.6.2"}
TEST_PLAN="jmeter/performance-test.jmx"
RESULTS_DIR="jmeter-results"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
RESULTS_FILE="${RESULTS_DIR}/performance_test_${TIMESTAMP}.jtl"
REPORT_DIR="${RESULTS_DIR}/report_${TIMESTAMP}"

mkdir -p ${RESULTS_DIR}

echo "Starting JMeter Performance Test..."
echo "JMeter Home: ${JMETER_HOME}"
echo "Test Plan: ${TEST_PLAN}"
echo "Results File: ${RESULTS_FILE}"

${JMETER_HOME}/bin/jmeter.sh -n -t ${TEST_PLAN} -l ${RESULTS_FILE} -e -o ${REPORT_DIR}

echo "Performance test completed!"
echo "Results saved to: ${RESULTS_FILE}"
echo "HTML Report generated in: ${REPORT_DIR}"

