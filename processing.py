import glob
import json
import re
from collections import OrderedDict
import os

def gen_dags(path):
    log_index = 0
    for log in glob.glob(os.path.join(path, "local-*")):
        app_name = ""
        # Build stage dictionary
        stage_dict = OrderedDict()
        file_open = open(log)
        with file_open as logfile:
            for line in logfile:
                data = json.loads(line)
                try:
                    if data["Event"] == "SparkListenerApplicationStart":
                        app_name = data["App Name"]
                        stage_dict["jobs"] = {}
                        id_symbols = []
                    elif data["Event"] == "SparkListenerStageSubmitted":
                        # print(data)
                        stage = data["Stage Info"]
                        stage_id = stage["Stage ID"]
                        if stage_id not in stage_dict.keys():
                            stage_dict[stage_id] = {}
                            if stage_id == 0:
                                stage_dict[0]["totalduration"] = 0
                            stage_dict[stage_id]["name"] = stage['Stage Name']
                            stage_dict[stage_id]["genstage"] = False
                            stage_dict[stage_id]["parentsIds"] = stage["Parent IDs"]
                            stage_dict[stage_id]["nominalrate"] = 0.0
                            stage_dict[stage_id]["weight"] = 0
                            stage_dict[stage_id]["RDDIds"] = {
                                x["RDD ID"]: {"name": x["Name"], "callsite": x["Callsite"]} for x in
                                stage["RDD Info"]}
                            stage_dict[stage_id]["skipped"] = False
                            stage_dict[stage_id]["cachedRDDs"] = []
                            stage_dict[stage_id]["numtask"] = 0
                            stage_dict[stage_id]["duration"] = 0.1
                            stage_dict[stage_id]["recordsread"] = 0.0
                            stage_dict[stage_id]["shufflerecordsread"] = 0.0
                            stage_dict[stage_id]["recordswrite"] = 0.0
                            stage_dict[stage_id]["shufflerecordswrite"] = 0.0
                            for rdd_info in stage["RDD Info"]:
                                storage_level = rdd_info["Storage Level"]
                                if storage_level["Use Disk"] or storage_level["Use Memory"] or \
                                        storage_level["Deserialized"]:
                                    stage_dict[stage_id]["cachedRDDs"].append(rdd_info["RDD ID"])
                    elif data["Event"] == "SparkListenerStageCompleted":
                        # print(data)
                        stage_id = data["Stage Info"]["Stage ID"]
                        stage_dict[stage_id]["numtask"] = data["Stage Info"]['Number of Tasks']
                        for acc in data["Stage Info"]["Accumulables"]:
                            if acc["Name"] == "internal.metrics.executorRunTime":
                                stage_dict[stage_id]["duration"] = int(acc["Value"])
                                stage_dict[0]["totalduration"] += int(acc["Value"])
                            if acc["Name"] == "internal.metrics.input.recordsRead":
                                stage_dict[stage_id]["recordsread"] = acc["Value"]
                            if acc["Name"] == "internal.metrics.shuffle.read.recordsRead":
                                stage_dict[stage_id]["shufflerecordsread"] = acc["Value"]
                            if acc["Name"] == "internal.metrics.output.recordsWrite":
                                stage_dict[stage_id]["recordswrite"] = acc["Value"]
                            if acc["Name"] == "internal.metrics.shuffle.write.recordsWritten":
                                stage_dict[stage_id]["shufflerecordswrite"] = acc["Value"]
                except KeyError:
                    print(data)

        skipped = []
        file_open = open(log)
        with file_open as logfile:
            for line in logfile:
                data = json.loads(line)
                try:
                    if data["Event"] == "SparkListenerJobStart":
                        # print(data)
                        job_id = data["Job ID"]
                        stage_dict["jobs"][job_id] = {}
                        # print(stage_dict["jobs"])
                        id_symb_root = sorted(data["Stage Infos"], 
                                              key = lambda k: k["Stage ID"])[-1]["Stage Name"]\
                                              .replace(" at ", "_") + "_"
                        seq = 0
                        while id_symb_root + str(seq) in id_symbols:
                            seq += 1
                        id_symb = id_symb_root + str(seq)
                        id_symbols.append(id_symb)
                        stage_dict["jobs"][job_id]["id-symb"] = id_symb
                        stage_dict["jobs"][job_id]["stages"] = sorted(data["Stage IDs"]) 
                        
                        for stage in data["Stage Infos"]:
                            stage_id = stage["Stage ID"]
                            # stage_dict["jobs"][job_id]["stages"].append(stage_id) 
                            if stage["Stage ID"] not in stage_dict.keys():
                                stage_dict[stage_id] = {}
                                stage_dict[stage_id]["name"] = stage['Stage Name']
                                stage_dict[stage_id]["genstage"] = False
                                stage_dict[stage_id]["parentsIds"] = stage["Parent IDs"]
                                stage_dict[stage_id]["nominalrate"] = 0.0
                                stage_dict[stage_id]["weight"] = 0
                                stage_dict[stage_id]["RDDIds"] = {
                                    x["RDD ID"]: {"name": x["Name"], "callsite": x["Callsite"]} for
                                    x in
                                    stage["RDD Info"]}
                                stage_dict[stage_id]["skipped"] = True
                                stage_dict[stage_id]["duration"] = 0
                                stage_dict[stage_id]["cachedRDDs"] = []
                                stage_dict[stage_id]["numtask"] = 0
                                stage_dict[stage_id]["recordsread"] = 0.0
                                stage_dict[stage_id]["shufflerecordsread"] = 0.0
                                stage_dict[stage_id]["recordswrite"] = 0.0
                                stage_dict[stage_id]["shufflerecordswrite"] = 0.0
                                for rdd_info in stage["RDD Info"]:
                                    storage_level = rdd_info["Storage Level"]
                                    if storage_level["Use Disk"] or storage_level["Use Memory"] or \
                                            storage_level["Deserialized"]:
                                        stage_dict[stage_id]["cachedRDDs"].append(
                                            rdd_info["RDD ID"])
                                skipped.append(stage_id)
                except KeyError:
                    None
        
        stage_dict_key_stages = [k for k in stage_dict.keys() if k != "jobs"]
        # Replace skipped stage id in parents ids based on RDD IDs
        for skipped_id in skipped:
            for stage_id1 in stage_dict_key_stages: #stage_dict.keys():
                if stage_id1 != skipped_id and stage_dict[skipped_id]["RDDIds"] == \
                        stage_dict[stage_id1]["RDDIds"]:
                    for stage_id2 in stage_dict_key_stages: #stage_dict.keys():
                        if skipped_id in stage_dict[stage_id2]["parentsIds"]:
                            stage_dict[stage_id2]["parentsIds"].remove(skipped_id)
                            stage_dict[stage_id2]["parentsIds"].append(stage_id1)
        
        # stage_dict_key_stages = [k for k in stage_dict.keys() if k != "jobs"]
        for stage in stage_dict_key_stages:
            if len(stage_dict[stage]["parentsIds"]) == 0:
                try:
                    cached = list(stage_dict[stage]["cachedRDDs"])
                except KeyError:
                    None
                for i in range(0, stage):
                    try:
                        for rdd in cached:
                            if rdd in stage_dict[i]["cachedRDDs"]:
                                stage_dict[stage]["parentsIds"].append(i)
                                cached.remove(rdd)
                    except KeyError:
                        None

        stage_to_do = len(list(stage_dict_key_stages)) - len(skipped)
        for stage_id in sorted(stage_dict_key_stages):
            parent_output = 0
            parent_input = 0
            if stage_id not in skipped:
                stage_dict[stage_id]["weight"] = stage_to_do
                stage_to_do -= 1
                for parent_id in stage_dict[stage_id]["parentsIds"]:
                    parent_output += stage_dict[parent_id]["recordswrite"]
                    parent_output += stage_dict[parent_id]["shufflerecordswrite"]
                    parent_input += stage_dict[parent_id]["recordsread"]
                    parent_input += stage_dict[parent_id]["shufflerecordsread"]
                if parent_output != 0:
                    stage_dict[stage_id]["nominalrate"] = parent_output / (
                        stage_dict[stage_id]["duration"] / 1000.0)
                elif parent_input != 0:
                    stage_dict[stage_id]["nominalrate"] = parent_input / (
                        stage_dict[stage_id]["duration"] / 1000.0)
                else:
                    stage_input = stage_dict[stage_id]["recordsread"] + stage_dict[stage_id][
                        "shufflerecordsread"]
                    if stage_input != 0 and stage_input != stage_dict[stage_id]["numtask"]:
                        stage_dict[stage_id]["nominalrate"] = stage_input / (
                            stage_dict[stage_id]["duration"] / 1000.0)
                    else:
                        stage_output = stage_dict[stage_id]["recordswrite"] + stage_dict[stage_id][
                            "shufflerecordswrite"]
                        stage_dict[stage_id]["nominalrate"] = stage_input / (
                            stage_dict[stage_id]["duration"] / 1000.0)
                if stage_dict[stage_id]["nominalrate"] == 0.0:
                    stage_dict[stage_id]["genstage"] = True

        totalduration = stage_dict[0]["totalduration"]
        for key in stage_dict_key_stages:
            if key not in skipped:
                old_weight = stage_dict[key]["weight"]
                stage_dict[key]["weight"] = (old_weight + (totalduration / stage_dict[key]["duration"]))/2
                totalduration -= stage_dict[key]["duration"]

        # Create json output
        stage_dict[0]["jobs"] = stage_dict["jobs"]
        stage_dict.pop("jobs")
        with open(os.path.join(path, re.sub("[^a-zA-Z0-9.-]", "_", app_name)+"-"+str(log_index)+".json"),
                  "w") as jsonoutput:
            json.dump(stage_dict, jsonoutput, indent=4, sort_keys=True)
        log_index += 1
