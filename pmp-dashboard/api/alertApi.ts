import { Alert } from "@/types/alert";
import axios from "axios";

const BASE_URL = "http://localhost:8080/api/v1/alerts";

export const fetchActiveAlerts = async (): Promise<Alert[]> => {
  try {
    const res = await fetch(`${BASE_URL}/active`, {
      cache: "no-store",
      mode: "cors"
    });

    if (!res.ok) {
      console.warn("Alerts fetch failed:", res.status);
      return [];
    }

    return res.json();
  } catch (err) {
    console.warn("Alerts fetch error:", err);
    return [];
  }
};



export const acknowledgeAlert = async (alertId: number): Promise<Alert> => {
    try {
        const res = await axios.patch(`${BASE_URL}/acknowledge/${alertId}`);
        return res.data;
    } catch (error) {
        if (axios.isAxiosError(error)) {
            const message =
                error.response?.data?.message ||
                error.response?.data ||
                "Failed to acknowledge alert";
            throw new Error(message);
        }
        throw new Error("Unexpected error occurred");
    }
};


export const resolveAlert = async (alertId: number): Promise<Alert> => {
    try {
        const res = await axios.patch(`${BASE_URL}/resolve/${alertId}`);
        return res.data;
    } catch (error) {
        if (axios.isAxiosError(error)) {
            const message =
                error.response?.data?.message ||
                error.response?.data ||
                "Failed to resolve alert";
            throw new Error(message);
        }
        throw new Error("Unexpected error occurred");
    }
};


export const fetchAlertsForMachine = async (machineId: string): Promise<Alert[]> => {
  const res = await axios.get(`${BASE_URL}/machine/${machineId}`);
  return res.data;
}; 