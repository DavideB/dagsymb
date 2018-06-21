import sys
import subprocess

def generate(jar_path, main_class_prefix):
    returncode = 0
    i = 0
    while not returncode:
        command = ["java", "-cp", jar_path, main_class_prefix+str(i)]
        print("Running", " ".join(command))
        process = subprocess.Popen(command, stdout=subprocess.PIPE)
        output, error = process.communicate()
        returncode = process.returncode
        i += 1


if __name__ == "__main__":
    generate(sys.argv[1], sys.argv[2])
