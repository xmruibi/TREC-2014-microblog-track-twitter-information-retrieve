@REM ----------------------------------------------------------------------------
@REM  Copyright 2001-2006 The Apache Software Foundation.
@REM
@REM  Licensed under the Apache License, Version 2.0 (the "License");
@REM  you may not use this file except in compliance with the License.
@REM  You may obtain a copy of the License at
@REM
@REM       http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM  Unless required by applicable law or agreed to in writing, software
@REM  distributed under the License is distributed on an "AS IS" BASIS,
@REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM  See the License for the specific language governing permissions and
@REM  limitations under the License.
@REM ----------------------------------------------------------------------------
@REM
@REM   Copyright (c) 2001-2006 The Apache Software Foundation.  All rights
@REM   reserved.

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\org\apache\lucene\lucene-core\4.3.1\lucene-core-4.3.1.jar;"%REPO%"\org\apache\lucene\lucene-queryparser\4.3.1\lucene-queryparser-4.3.1.jar;"%REPO%"\org\apache\lucene\lucene-queries\4.3.1\lucene-queries-4.3.1.jar;"%REPO%"\org\apache\lucene\lucene-sandbox\4.3.1\lucene-sandbox-4.3.1.jar;"%REPO%"\jakarta-regexp\jakarta-regexp\1.4\jakarta-regexp-1.4.jar;"%REPO%"\org\apache\lucene\lucene-analyzers-common\4.3.1\lucene-analyzers-common-4.3.1.jar;"%REPO%"\org\twitter4j\twitter4j-core\3.0.3\twitter4j-core-3.0.3.jar;"%REPO%"\org\twitter4j\twitter4j-stream\3.0.3\twitter4j-stream-3.0.3.jar;"%REPO%"\com\google\guava\guava\14.0.1\guava-14.0.1.jar;"%REPO%"\com\google\code\findbugs\jsr305\1.3.9\jsr305-1.3.9.jar;"%REPO%"\commons-cli\commons-cli\1.2\commons-cli-1.2.jar;"%REPO%"\commons-io\commons-io\2.4\commons-io-2.4.jar;"%REPO%"\com\ning\async-http-client\1.7.5\async-http-client-1.7.5.jar;"%REPO%"\io\netty\netty\3.4.4.Final\netty-3.4.4.Final.jar;"%REPO%"\org\slf4j\slf4j-api\1.6.2\slf4j-api-1.6.2.jar;"%REPO%"\com\google\code\gson\gson\1.6\gson-1.6.jar;"%REPO%"\org\slf4j\slf4j-log4j12\1.6.1\slf4j-log4j12-1.6.1.jar;"%REPO%"\junit\junit\4.8.2\junit-4.8.2.jar;"%REPO%"\log4j\log4j\1.2.17\log4j-1.2.17.jar;"%REPO%"\log4j\apache-log4j-extras\1.1\apache-log4j-extras-1.1.jar;"%REPO%"\org\apache\thrift\libthrift\0.9.0\libthrift-0.9.0.jar;"%REPO%"\commons-lang\commons-lang\2.5\commons-lang-2.5.jar;"%REPO%"\org\apache\httpcomponents\httpclient\4.1.3\httpclient-4.1.3.jar;"%REPO%"\commons-logging\commons-logging\1.1.1\commons-logging-1.1.1.jar;"%REPO%"\commons-codec\commons-codec\1.4\commons-codec-1.4.jar;"%REPO%"\org\apache\httpcomponents\httpcore\4.1.3\httpcore-4.1.3.jar;"%REPO%"\com\twitter\twitter-text\1.6.1\twitter-text-1.6.1.jar;"%REPO%"\org\yaml\snakeyaml\1.12\snakeyaml-1.12.jar;"%REPO%"\org\apache\ant\ant\1.9.1\ant-1.9.1.jar;"%REPO%"\org\apache\ant\ant-launcher\1.9.1\ant-launcher-1.9.1.jar;"%REPO%"\it\unimi\dsi\fastutil\6.5.6\fastutil-6.5.6.jar;"%REPO%"\org\jsoup\jsoup\1.7.3\jsoup-1.7.3.jar;"%REPO%"\cc\twittertools\twitter-tools-core\1.4.3-SNAPSHOT\twitter-tools-core-1.4.3-SNAPSHOT.jar
goto endInit

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS%  -classpath %CLASSPATH_PREFIX%;%CLASSPATH% -Dapp.name="GatherStatusStream" -Dapp.repo="%REPO%" -Dapp.home="%BASEDIR%" -Dbasedir="%BASEDIR%" cc.twittertools.stream.GatherStatusStream %CMD_LINE_ARGS%
if %ERRORLEVEL% NEQ 0 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=%ERRORLEVEL%

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@REM If error code is set to 1 then the endlocal was done already in :error.
if %ERROR_CODE% EQU 0 @endlocal


:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
