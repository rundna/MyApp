call runcrud.bat

if %ERRORLEVEL%==0 goto startbrowser
echo %ERRORLEVEL%
echo Error on run
goto fail

:startbrowser
start "C:\Program Files\internet explorer\iexplore.exe" http://localhost:8080/crud/v1/task/getTasks
echo.
echo Opened url in browser

:fail
echo %ERRORLEVEL%
echo There were errors

:end
echo.
echo Work finished