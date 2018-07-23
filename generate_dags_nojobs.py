import sys
import subprocess
import os
import shutil
from processing_nojobs import gen_dags

def generate(jar_path, main_class_prefix):
    returncode = 0
    i = 0
    root = os.path.dirname(jar_path)
    output = os.path.join(root, "data")
    events = os.path.join(output, "tmp")
               
    if os.path.exists(output):
        shutil.rmtree(output)
    os.makedirs(output)
    os.makedirs(events)

    # If client is Windows, convert drive part of paths to format needed by docker run -v <path> command
    events_for_docker = "/" + events.replace("\\", "/").replace(":", "") if ":" in events else events
    jar_path_for_docker = "/" + jar_path.replace(":", "").replace("\\", "/") if ":" in jar_path else jar_path
    stop = False
    while not returncode:# and not stop:
        # command = ["docker", "run", "-v", jar_path+":/app.jar", "-v", events+":/tmp/spark-events", "gioquattrocchi/spark-dock", "/spark/bin/spark-submit", "--class", main_class_prefix+str(i), "--conf", "spark.eventLog.enabled=true", "/app.jar"]
        # command = ["docker", "run", "-v", jar_path_for_docker+":/app.jar", "-v", events_for_docker+":/tmp/spark-events", "epahomov/docker-spark:lightweighted", "/spark/bin/spark-submit", "--class", main_class_prefix+str(i), "--conf", "spark.eventLog.enabled=true", "/app.jar"]
        # command = ["docker", "run", "-v", jar_path_for_docker+":/app.jar", "-v", events_for_docker+":/tmp/spark-events", "epahomov/docker-spark", "/spark/bin/spark-submit", "--class", main_class_prefix+str(i), "--conf", "spark.eventLog.enabled=true", "/app.jar"]
        # command = ["C:\\Users\\Fabio\\git\\xSpark\\bin\\spark-submit.cmd", "--class", "it.polimi.deepse.dagsymb.launchers.Launcher"+str(i), "--conf", "spark.eventLog.enabled=true", "/Users/Davide/git/dagsymb/target/dagsymb-1.0-jar-with-dependencies.jar"]
        # C:\Users\Davide\git\spark\bin\spark-submit.cmd --class it.polimi.deepse.dagsymb.launchers.Launcher0 --conf spark.eventLog.enabled=true /Users/Davide/git/dagsymb/target/dagsymb-1.0-jar-with-dependencies.jar
        command = ["C:\\Users\\Davide\\git\\spark\\bin\\spark-submit.cmd", "--class", "it.polimi.deepse.dagsymb.launchers.Launcher"+str(i), "--conf", "spark.eventLog.enabled=true", "/Users/Davide/git/dagsymb/target/dagsymb-1.0-jar-with-dependencies.jar"]
        returncode = run_command(command)
        i += 1
        stop = True
    
    gen_dags(events)
    
    for filename in os.listdir(events):
        if "json" in filename:
            shutil.move(os.path.join(events, filename), os.path.join(output, filename))

    #shutil.rmtree(events)

def run_command(command):
    print("Running", " ".join(command))
    # env = os.environ
    # print("Env: ", env)
    # env["PATH"] = "C:\\Hadoop\\hadoop-2.7.3\\bin;" + env["PATH"]
    # process = subprocess.Popen(command, shell = True, env=env, stdout=subprocess.PIPE)
    process = subprocess.Popen(command, stdout=subprocess.PIPE)
    process.communicate()
    return process.returncode


if __name__ == "__main__":
     # generate(sys.argv[1], sys.argv[2])
     gen_dags("target/data/tmp")
