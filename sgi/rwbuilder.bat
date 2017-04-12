@REM
@REM Copyright (c) 1999, 2008, Oracle and/or its affiliates.
@REM All rights reserved. 
@REM

@echo off 
@echo Starting Reports 12c Builder...
setlocal

set DOMAIN_HOME=C:\app\oracle\config\domains\frs

call %DOMAIN_HOME%\reports\bin\reports.bat

set COMPONENT_NAME=reptools1
set COMPONENT_CONFIG_PATH=C:\app\oracle\config\domains\frs\config\fmwconfig\components\ReportsToolsComponent\%COMPONENT_NAME%

set REPORTS_INSTANCE=%COMPONENT_CONFIG_PATH%
set CA_GPREFS=%COMPONENT_CONFIG_PATH%\tools\admin
set CA_UPREFS=%COMPONENT_CONFIG_PATH%\tools\admin
set REPORTS_PATH=C:\app\oracle\product\12.2.1\reports\templates;C:\app\oracle\product\12.2.1\reports\printers;Y:\centralrepository\sgi\src\template;Y:\centralrepository\sgi\sql\lib;Y:\centralrepository\sgi\menu;Y:\centralrepository\sgi\imagen;


@echo on
start C:\app\oracle\product\12.2.1\bin\rwbuilder.exe %*

@echo off
endlocal
@echo on
