import random, datetime

def generate_data(machineId):

    mode = random.choices(
        ["NORMAL", "WARNING", "CRITICAL"],
        weights=[60, 25, 15],
        k=1
    )[0]

    if mode == "NORMAL":
        temperature = random.uniform(70, 85)
        vibration = random.uniform(0.5, 4.0)
        pressure = random.uniform(8.5, 11)

    elif mode == "WARNING":
        temperature = random.uniform(85, 100)
        vibration = random.uniform(4.0, 8.0)
        pressure = random.uniform(7.5, 9)

    else:  # CRITICAL
        temperature = random.uniform(100, 125)
        vibration = random.uniform(8.0, 14.0)
        pressure = random.uniform(6, 7.5)

    rpm = random.uniform(1000, 6000)
    motorVoltage = random.uniform(370, 460)
    motorCurrent = random.uniform(0, 100)
    oilLevel = random.uniform(0, 100)
    dutyCycle = random.uniform(0, 100)

    status = "NORMAL"
    if temperature > 110 or vibration > 10 or pressure < 7:
        status = "CRITICAL"
    elif temperature > 90 or vibration > 7 or pressure < 8:
        status = "WARNING"

    return {
        "machineId": f"MCH-{machineId}",
        "temperature": round(temperature, 2),
        "vibration": round(vibration, 2),
        "pressure": round(pressure, 2),
        "rpm": round(rpm, 2),
        "motorVoltage": round(motorVoltage, 2),
        "motorCurrent": round(motorCurrent, 2),
        "oilLevel": round(oilLevel, 2),
        "dutyCycle": round(dutyCycle, 2),
        "timestamp": datetime.datetime.now().isoformat(),
        "status": status
    }
