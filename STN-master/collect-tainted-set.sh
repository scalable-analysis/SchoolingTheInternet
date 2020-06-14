#!/bin/bash

INFER_OUT_DIR=$1

CONTEXT=()

for k in $(jq '. | keys | .[]' "${INFER_OUT_DIR}/report.json"); do
    BUG=$(jq -r ".[$k].bug_trace" "${INFER_OUT_DIR}/report.json");

    TRACE=()
    LOCALS=()

    LEN=$(jq '. | length' <<< ${BUG});

    for i in $(jq '. | keys | .[]' <<< ${BUG}); do
        DIRTY_CALL=$(jq -r ".[$i].description" <<< "$BUG");
        CALL=$(echo ${DIRTY_CALL} | sed 's/Call\sto\s//g' | sed 's/Return\sfrom\s//g')
        SINK_LOC=""

        if [[ ${CALL} == *"callLocation"* ]]; then
            SINK_LOC=$(echo ${CALL} | awk -F[{}] '{print $2}')
            CALL=$(echo ${CALL} | awk -F":::" '{print $1}')
            SEARCH=${SINK_LOC}
        else 
            SEARCH=${CALL}
        fi
 
        TRIMMED_SEARCH=$(echo $SEARCH | cut -d' ' -f2 )
        CONTEXT+=($TRIMMED_SEARCH)
    done

done

echo "" > tmp_tainted

grep -r 'TAINTED_CALL1' ${INFER_OUT_DIR} | while read -r line ; do
    CALLEE=$(echo ${line} | cut -d'"' -f 2)
    CALLER=$(echo ${line} | cut -d'"' -f 4)

    TRIMMED_CALLER=$(echo $CALLER | cut -d' ' -f2)
    TAINTED_CALLS=()

    for search in "${CONTEXT[@]}"
    do
        if [[ ${TRIMMED_CALLER} == ${search} ]]; then
            TRIMMED_CALLEE=$(echo $CALLEE | cut -d' ' -f2)
            echo "TAINTED: $TRIMMED_CALLEE" >> tmp_tainted
            TAINTED_CALLS+=($TRIMMED_CALLEE)
        fi
    done

done

grep -r 'TAINTED_CALL2' ${INFER_OUT_DIR} | while read -r line ; do
    CALLEE=$(echo ${line} | cut -d'"' -f 2)

    if ! [[ $CALLEE = "String Object"* ]] && ! [[ $CALLEE = StringBuilder* ]]; then 
        echo "SANITIZER: $CALLEE" >> tmp_tainted
    fi 
done

cat tmp_tainted | uniq

