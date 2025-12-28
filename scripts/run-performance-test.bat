@echo off
setlocal

set JMETER_HOME=%JMETER_HOME%
if "%JMETER_HOME%"=="" set JMETER_HOME=C:\apache-jmeter-5.6.2

set TEST_PLAN=jmeter\performance-test.jmx
set RESULTS_DIR=jmeter-results
set TIMESTAMP=%date:~-4,4%%date:~-10,2%%date:~-7,2%_%time:~0,2%%time:~3,2%%time:~6,2%
set TIMESTAMP=%TIMESTAMP: =0%
set RESULTS_FILE=%RESULTS_DIR%\performance_test_%TIMESTAMP%.jtl
set REPORT_DIR=%RESULTS_DIR%\report_%TIMESTAMP%

if not exist %RESULTS_DIR% mkdir %RESULTS_DIR%

echo Starting JMeter Performance Test...
echo JMeter Home: %JMETER_HOME%
echo Test Plan: %TEST_PLAN%
echo Results File: %RESULTS_FILE%

"%JMETER_HOME%\bin\jmeter.bat" -n -t %TEST_PLAN% -l %RESULTS_FILE% -e -o %REPORT_DIR%

echo Performance test completed!
echo Results saved to: %RESULTS_FILE%
echo HTML Report generated in: %REPORT_DIR%

endlocal

