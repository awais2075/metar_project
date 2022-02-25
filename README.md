
# Metar Project (Java 11)

## Project Branches with Tags
    1. first-task -  v1.0
    2. second-task - v1.0

### Configuration
    To run application you need to configure database (Postgres) by the following steps.
        1. Open File **application-dev.yml** (/src/main/resources)
        2. Update url: jdbc:postgresql://localhost:5432/**postgres**
        3. Update username: **postgres**
        4. Update password: **awais2075**
    
    Note: Application will run on **port: 8082** and **context-path: /metar/v1.x** 
    you can change by editing file **application.yml** (src/main/resources)

### Documentation
    To access documentation you can open 
    url http://localhost:8082/metar/v2.0/api-docs in your browser
    **port: 8082** 
    **context-path: /metar/v2.0**

### Automation-Script
    Project contains a file **automate-metar.sh** in directory **scripts**
    The following steps are required to run this script in crontab

        1. open crontab (crontab -e)
        2. add the following line in file
           */10 * * * * bash /file-path/automate-metar.sh
           means this script will run after every 10 mintues.
        3. change **BASE_URL** value according to your project path

        Note: jq and curl packages are required 
        to run this script on your linux machine
        jq: https://stedolan.github.io/jq/
        curl: https://curl.se/
