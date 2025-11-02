import random , datetime

def generate_data(machineId):
    temperature = random.uniform(65 , 125)
    vibration = random.uniform(0.0,14.0)
    rpm = random.uniform(1000 , 6000)
    pressure = random.uniform(6 , 15)
    motor_voltage = random.uniform(370 , 460) #relative to rated value
    motor_current = random.uniform(0 , 100) # % full load current
    oil_level = random.uniform(0,100)
    duty_cycle = random.uniform(0,100)
    status = "NORMAL"
    if(temperature > 110 or vibration > 10 or pressure < 7):
        status = "CRITICAL"
    elif (temperature > 90 or vibration > 7 or pressure < 8):
        status = "WARNING";


    return {
        "machine_id" : f"MCH-{machineId}",
        "temperature" : round(temperature , 2),
        "vibration" : round(vibration, 2),
        "pressure" : round(pressure , 2),
        "rpm" : rpm,
        "motor_voltage": motor_voltage,
        "motor_current" : motor_current,
        "oil_level" : oil_level,
        "duty_cycle" : duty_cycle,
        "timeStamp" : datetime.datetime.now().isoformat(),
        "status":status
    }


