export type MachineReadings = {
  machine_id: string;
  temperature: number;
  vibration: number;
  pressure: number;
  rpm: number;
  motor_voltage: number;
  motor_current: number;
  oil_level: number;
  duty_cycle: number;
  timeStamp: string;
  status: string;
};
