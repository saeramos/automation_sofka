param(
    [string]$JtlFile = "",
    [string]$OutputDir = "jmeter-results/reports"
)

if ([string]::IsNullOrEmpty($JtlFile)) {
    Write-Host "Usage: .\generate-performance-report.ps1 -JtlFile <path_to_jtl> -OutputDir <output_directory>"
    exit 1
}

$JMETER_HOME = $env:JMETER_HOME
if ([string]::IsNullOrEmpty($JMETER_HOME)) {
    $JMETER_HOME = "C:\apache-jmeter-5.6.2"
}

if (-not (Test-Path $JtlFile)) {
    Write-Host "Error: JTL file not found: $JtlFile"
    exit 1
}

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null

Write-Host "Generating performance report from: $JtlFile"
Write-Host "Output directory: $OutputDir"

& "$JMETER_HOME\bin\jmeter.bat" -g $JtlFile -o $OutputDir

Write-Host "Report generated successfully in: $OutputDir"

