#!/bin/bash

GIT_ROOT="$(git rev-parse --show-toplevel)/STN-master"
DATA_ROOT="${GIT_ROOT}/raw-data"
SOURCE_ROOT="${DATA_ROOT}/sources"
CONFIG_ROOT="${DATA_ROOT}/infer-configs"
RESULT_ROOT="${DATA_ROOT}/results"
LOG_ROOT="${DATA_ROOT}/logs"
CALL_TRACE_JAR="${GIT_ROOT}/trace-extraction/production/0.0.1/trace-extractor-0.0.1.jar"
DATA_TRACE_JAR="${GIT_ROOT}/dataflow-extraction/target/data-tracer-0.0.1-jar-with-dependencies.jar"

cd "${DATA_ROOT}"

show_info () {
  echo "INFO: $1"
}

mkdir -p "${RESULT_ROOT}"
mkdir -p "${LOG_ROOT}"

FILES="${SOURCE_ROOT}/*"
for file in $FILES
do
    source ${file}
    EXAMPLE_DIR="${RESULT_ROOT}/${EXAMPLE_NAME}"
    EXAMPLE_LOG_DIR="${LOG_ROOT}/${EXAMPLE_NAME}";

    rm -fr "${EXAMPLE_DIR}"
    mkdir -p "${EXAMPLE_LOG_DIR}"

    show_info "Analyzing example: ${EXAMPLE_NAME}"
    cd "${RESULT_ROOT}"

    show_info "Cloning from ${EXAMPLE_GIT_ROOT}"
    git clone "${EXAMPLE_GIT_ROOT}"


    ##### Run Infer #####
    show_info "Moving Infer config to ${EXAMPLE_DIR}"
    cp "${CONFIG_ROOT}/${EXAMPLE_INFER_CONFIG}" "${EXAMPLE_DIR}/${EXAMPLE_MONOREPO_FOLDER}/.inferconfig"

    show_info "Running quandary"
    cd "${EXAMPLE_DIR}/${EXAMPLE_MONOREPO_FOLDER}"
    infer run --debug-level 2 --debug --quandary-only -j 1 -- ${EXAMPLE_BUILD_CMD} 2>&1 | tee "${EXAMPLE_LOG_DIR}/${EXAMPLE_MONOREPO_FOLDER}-quandary.log"
    cp "${EXAMPLE_DIR}/${EXAMPLE_MONOREPO_FOLDER}/infer-out/report.json" "${EXAMPLE_LOG_DIR}/${EXAMPLE_MONOREPO_FOLDER}-quandary-report.json"

    show_info "Generating the quandary tainted method set"
    ${GIT_ROOT}/scripts/collect-tainted-set.sh "${EXAMPLE_DIR}/${EXAMPLE_MONOREPO_FOLDER}/infer-out" > "${EXAMPLE_LOG_DIR}/${EXAMPLE_MONOREPO_FOLDER}-quandary-set.json"

    if [ ${EXAMPLE_RUN_INFER_TRACE} = true ] ; then

    show_info "Generating the quandary trace"
    ${GIT_ROOT}/scripts/collect-tainted.sh "${EXAMPLE_DIR}/${EXAMPLE_MONOREPO_FOLDER}/infer-out" > "${EXAMPLE_LOG_DIR}/${EXAMPLE_MONOREPO_FOLDER}-quandary-trace.json"

    fi

    ##### Clean build without infer #####

    show_info "Rebuilding..."
    $($EXAMPLE_BUILD_CMD)

    ##### Run callgraph analysis #####

    if [ ${EXAMPLE_RUN_CALLGRAPH} = true ] ; then

    show_info "Extracting callgraph traces"

    # The source/sink info can also be pulled from infer
    #source=$(cat "${CONFIG_ROOT}/${EXAMPLE_INFER_CONFIG}" | jq '."quandary-sources"' | jq -r '.[0]."procedure"')
    #sink=$(cat "${CONFIG_ROOT}/${EXAMPLE_INFER_CONFIG}" | jq '."quandary-sinks"' | jq -r '.[0]."procedure"')
    classpath="${EXAMPLE_DIR}/${EXAMPLE_MONOREPO_FOLDER}/${EXAMPLE_ARTIFACTS}"

    show_info "  parameters:"
    show_info "    source = ${EXAMPLE_CG_SOURCE}"
    show_info "    sink = ${EXAMPLE_CG_SINK}"
    show_info "    namespace = ${EXAMPLE_CG_NAMESPACE}"
    show_info "    classpath = ${classpath}"

    java -jar "${CALL_TRACE_JAR}" "-s" "${EXAMPLE_CG_SOURCE}" "-e" ${EXAMPLE_CG_SINK} -n ${EXAMPLE_CG_NAMESPACE} "${classpath}"  2>&1 | tee "${EXAMPLE_LOG_DIR}/${EXAMPLE_NAME}-callgraph-traces.log"

    fi

    ##### Run dataflow analysis #####

    if [ ${EXAMPLE_RUN_DATAFLOW} = true ] ; then

    show_info "Extracting dataflow traces"

    show_info "  parameters:"
    show_info "    source = ${EXAMPLE_DF_SOURCE}"
    show_info "    sink = ${EXAMPLE_DF_SINK}"
    show_info "    main class = ${EXAMPLE_DF_MAIN_CLASS}"
    show_info "    entrypoint = ${EXAMPLE_DF_ENTRYPOINT}"
    show_info "    classpath = ${EXAMPLE_DIR}/${EXAMPLE_MONOREPO_FOLDER}/${EXAMPLE_ARTIFACTS}"

    java -jar "${DATA_TRACE_JAR}" "-s" "${EXAMPLE_DF_SOURCE}" "-k" "${EXAMPLE_DF_SINK}" "-m" "${EXAMPLE_DF_MAIN_CLASS}" "-e" "${EXAMPLE_DF_ENTRYPOINT}" ${EXAMPLE_DIR}/${EXAMPLE_MONOREPO_FOLDER}/${EXAMPLE_ARTIFACTS} 2>&1 | tee "${EXAMPLE_LOG_DIR}/${EXAMPLE_NAME}-dataflow-traces.log"

    fi

done
