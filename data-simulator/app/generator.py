import random, datetime

def generate_data(machineId):

    # Decide operating mode (controls rarity)
    mode = random.choices(
        ["NORMAL", "WARNING", "CRITICAL"],
        weights=[50, 25, 25],  # CRITICAL is now RARE
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

    # Other parameters (kept random, non-critical)
    rpm = random.uniform(1000, 6000)
    motor_voltage = random.uniform(370, 460)
    motor_current = random.uniform(0, 100)
    oil_level = random.uniform(0, 100)
    duty_cycle = random.uniform(0, 100)

    # SAME status logic as before (unchanged)
    status = "NORMAL"
    if temperature > 110 or vibration > 10 or pressure < 7:
        status = "CRITICAL"
    elif temperature > 90 or vibration > 7 or pressure < 8:
        status = "WARNING"

    return {
        "machine_id": f"MCH-{machineId}",
        "temperature": round(temperature, 2),
        "vibration": round(vibration, 2),
        "pressure": round(pressure, 2),
        "rpm": round(rpm, 2),
        "motor_voltage": round(motor_voltage, 2),
        "motor_current": round(motor_current, 2),
        "oil_level": round(oil_level, 2),
        "duty_cycle": round(duty_cycle, 2),
        "timeStamp": datetime.datetime.now().isoformat(),
        "status": status
    }
