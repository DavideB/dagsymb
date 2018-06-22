import sys
import subprocess
import os
import shutil
from processing import gen_dags

def generate(jar_path, main_class_prefix):
    returncode = 0
    i = 0
    root = os.path.dirname(jar_path)
    output = os.path.join(root, "data")
    events = os.path.join(output, "tmp")

    if os.path.exists(output):
        shutil.rmtree(output)
    os.makedirs(output)


    while not returncode:
        command = ["docker", "run", "-v", jar_path+":/app.jar", "-v", events+":/tmp/spark-events", "gioquattrocchi/spark-dock", "/spark/bin/spark-submit", "--class", main_class_prefix+str(i), "--conf", "spark.eventLog.enabled=true", "/app.jar"]
        returncode = run_command(command)
        i += 1

    gen_dags(events)
    
    for filename in os.listdir(events):
        if "json" in filename:
            shutil.move(os.path.join(events, filename), os.path.join(output, filename))

    shutil.rmtree(events)

def run_command(command):
    print("Running", " ".join(command))
    process = subprocess.Popen(command, stdout=subprocess.PIPE)
    process.communicate()
    return process.returncode


if __name__ == "__main__":
    generate(sys.argv[1], sys.argv[2])
