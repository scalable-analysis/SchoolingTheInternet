#!/bin/bash

INFER_OUT_DIR=$1

grep -r 'TAINTED_CALL' ${INFER_OUT_DIR} | while read -r line ; do
    CALLEE=$(echo ${line} | cut -d'"' -f 2)
    CALLER=$(echo ${line} | cut -d'"' -f 4)
done

FINDING=()

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
 
        TRIMMED_CALL=$(echo $CALL | cut -d' ' -f2 | cut -d'(' -f1)
        TRACE+=($TRIMMED_CALL)

        while IFS= read -r line
        do
            CALLEE=$(echo ${line} | cut -d'"' -f 2)
            CALLER=$(echo ${line} | cut -d'"' -f 4)

            if [[ ${CALLER} == ${SEARCH} ]]; then

                TRIMMED_CALLEE=$(echo $CALLEE | cut -d' ' -f2 | cut -d'(' -f1)
                SINK_DEPTH=$[LEN - i - 1]

                LOCALS+=(`jq -n --arg c $TRIMMED_CALLEE --argjson t $i --argjson b $SINK_DEPTH '{"method": $c, "src-distance": $t, "sink-distance": $b }'`)

            fi
        done < <(grep -r 'TAINTED_CALL' ${INFER_OUT_DIR})

    done


    TRACE_LIST=`printf '%s\n' "${TRACE[@]}" | jq -R . | jq -s .`
    LOCALS_LIST=`printf '%s\n' "${LOCALS[@]}" | jq -s .`

    FINDING+=(`jq -n --argjson t "$TRACE_LIST" --argjson l "$LOCALS_LIST" '{"trace": $t, "passthroughs": $l}'`)

done

echo "${FINDING[@]}" | jq -s .
