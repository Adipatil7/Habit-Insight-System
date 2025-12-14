// src/hooks/useMachine.ts
import useSWR from "swr";

export type MachineReading = {
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


const fetcher = (url: string) => fetch(url).then((r) => {
  if (!r.ok) throw new Error("Network response was not ok");
  return r.json();
});

const API_BASE = "http://localhost:8080";

export function useMachine(machineId:string,pollMs = 6000) {
  const { data, error, isLoading } = useSWR<MachineReading>(
    machineId ? `${API_BASE}/api/v1/latest/${machineId}` : null,
    fetcher,
    { refreshInterval: pollMs, revalidateOnFocus: false }
  );

  return {
    data,
    error,
    isLoading,
  };
}
