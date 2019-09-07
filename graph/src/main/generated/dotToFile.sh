DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

dot_file_name=$1

output_format=$2

[[ -z $output_format ]] && output_format="png"

# dot - вызов graphviz из командной строки

dot -T"${output_format}" "${DIR}"/dot/"${dot_file_name}".dot -o "${DIR}"/${output_format}/"${dot_file_name}".${output_format}