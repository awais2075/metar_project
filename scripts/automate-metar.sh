#!/bin/bash 
TEMP_FILE_PATH="/tmp/response.txt"
BASE_URL="http://localhost:8082/metar"
httpStatusCode=404
fileHttpUrl=""
isAvailable=1
PAGE_SIZE=10
declare -i pageNo=40

getHttpData() {    
    httpStatusCode=$(curl -s -o $1 -w "%{http_code}" $2)

    case $3 in
            "file")
                # fileData=$(cat $TEMP_FILE_PATH)
                fileData="METAR "$(awk NR==2 $TEMP_FILE_PATH)
                ;;
            "json")
                jsonData=$(cat $TEMP_FILE_PATH)
    esac
}

getMetarUrl() {
    fileHttpUrl="https://tgftp.nws.noaa.gov/data/observations/metar/stations/$1.TXT"
}

getSubscriptionData() {
    if [ $httpStatusCode == "200" ]; then

        subscriptions=$(jq -r ".subscriptions" <<< $jsonData)

        totalIcaos=$(jq -r length <<< $subscriptions)

        if [ $totalIcaos -eq 0 ]; then
            isAvailable=0
        else    

            for (( index=0; index<$totalIcaos; index++ ))
            do
                code=$(jq -r ".[$index].icaoCode" <<< $subscriptions)
                echo "code is $code"
                getMetarUrl $code
                echo $fileHttpUrl

                getHttpData $TEMP_FILE_PATH $fileHttpUrl "file"
                echo $httpStatusCode
                if [ $httpStatusCode == "200" ]; then

                    echo $fileData
                    # formatFileData $code $fileData

                    echo $fileData
                    postMetaData $code '{"data": "'"$fileData"'"}'

                else
                    echo "$code : invalid file data"
                fi;

                # postMetaData
            done
        fi;
    else 
        echo $(jq -r ".message" <<< $jsonData)
    fi;
}

postMetaData() {
    echo $1
    echo $2
    httpStatusCode=$(curl -X 'POST' \
    ''$BASE_URL'/airport/'$1'/METAR' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d "$2"\
    )

    echo $httpStatusCode
}

formatFileData() {
    fileData="METAR "$(awk NR==2 $TEMP_FILE_PATH)
}


init() {
    while [ $isAvailable -eq 1 ]
    do
        getHttpData $TEMP_FILE_PATH "$BASE_URL/subscriptions?pageNo=$pageNo&pageSize=$PAGE_SIZE" "json"
        getSubscriptionData
        pageNo+=1
    done
}

init
# dat="METAR LDZA 121200Z 0902MPS 090V150 2000 R04/P2000N R22/P2000N OVC0500/M01 Q1020="
# postMetaData "LDZA" '{"data": "'"$dat"'"}'
# use jq for json-iteration

# jq length /tmp/response.txt -- to find json-nodes length
# cat /tmp/response.txt | jq '.[0].icaoCode' -- iterate over json nodes