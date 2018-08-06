import glob
import json
import re
from collections import OrderedDict
import os

def join_dags(path):
    json_index = 0
    app_name = ""
    joint_json = OrderedDict()
    for json_file in glob.glob(os.path.join(path, "*.json")):
        app_name = json_file.split(os.sep)[-1].split("-")[0]
        json_index = json_file.split(".")[0].split("-")[-1]
        file_open = open(json_file)
        print("json_file: ", json_file)
        with file_open as jsonfile:
            data = json.load(jsonfile)
            joint_json[json_index] = data

    print("app_name: ", app_name)
    with open(os.path.join(path, app_name + ".json"), "w") as jsonoutput:
        json.dump(joint_json, jsonoutput, indent=4, sort_keys=True)


if __name__ == "__main__":
     # join_dags(sys.argv[1], sys.argv[2])
     join_dags("target\data")