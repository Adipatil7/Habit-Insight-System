import time
from generator import generate_data
from sender import data_sender
from config import NUM_MACHINES, INTERVAL

if __name__ == "__main__":
    while (True):
        for machine_id in range(1, NUM_MACHINES+1):
            data = generate_data(machineId=machine_id)
            data_sender(data)
            print(data)
        time.sleep(INTERVAL)