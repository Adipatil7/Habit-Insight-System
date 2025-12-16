import { useEffect, useState } from "react";

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

const API_BASE = "http://localhost:8080";

export function useMachineCustomRange(
  machineId: string,
  from: Date | null,
  to: Date | null
) {
  const [data, setData] = useState<MachineReading[] | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!machineId || !from || !to) return;

    const fetchData = async () => {
      setLoading(true);
      setError(null);

      const url = `${API_BASE}/api/v1/custom/${machineId}`;
      const body = {
        t1: from.toISOString(),
        t2: to.toISOString(),
      };

      console.log("🔵 FETCHING CUSTOM RANGE");
      console.log("URL:", url);
      console.log("BODY:", body);

      try {
        const res = await fetch(url, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(body),
        });

        if (!res.ok) {
          throw new Error(`HTTP ${res.status}`);
        }

        const json = await res.json();
        console.log("🟢 CUSTOM RANGE RESPONSE:", json.length);

        setData(json);
      } catch (err: any) {
        console.error("🔴 CUSTOM RANGE ERROR:", err.message);
        setError(err.message);
        setData(null);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [machineId, from, to]);

  return {
    data,
    loading,
    error,
  };
}
