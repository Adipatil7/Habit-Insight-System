import { useCallback, useEffect, useRef, useState } from "react";
import { Alert } from "../types/alert";
import { fetchActiveAlerts, acknowledgeAlert, resolveAlert } from "../api/alertApi";
import toast from "react-hot-toast";

export const useAlerts = () => {
  const [alerts, setAlerts] = useState<Alert[]>([]);
  const [loading, setLoading] = useState(false);
  const seenCriticalAlertIds = useRef<Set<number>>(new Set());
  const initializedRef = useRef(false);
  const loadActiveAlerts = useCallback(async () => {
  setLoading(true);
  try {
    const data = await fetchActiveAlerts();

    data.forEach((alert) => {
      if (
        alert.status === "ACTIVE" &&
        alert.severity === "CRITICAL"
      ) {
        // 🚫 First load: just remember
        if (!initializedRef.current) {
          seenCriticalAlertIds.current.add(alert.id);
        }
        // 🔔 Subsequent loads: toast if new
        else if (!seenCriticalAlertIds.current.has(alert.id)) {
          toast.error(
            `CRITICAL: ${alert.machineId} — ${alert.message}`
          );
          seenCriticalAlertIds.current.add(alert.id);
        }
      }
    });

    setAlerts(data);

    // ✅ Mark initialization complete AFTER first fetch
    if (!initializedRef.current) {
      initializedRef.current = true;
    }
  } finally {
    setLoading(false);
  }
}, []);


  useEffect(() => {
    loadActiveAlerts();

    const interval = setInterval(loadActiveAlerts, 10000);
    return () => clearInterval(interval);
  }, [loadActiveAlerts]);


const acknowledge = async (id: number) => {
    try {
        await acknowledgeAlert(id);
        toast.success("Alert acknowledged");
        loadActiveAlerts();
    } catch (err: any) {
        toast.error(err.message || "Unable to acknowledge alert");
    }
};



const resolve = async (id: number) => {
    try {
        await resolveAlert(id);
        toast.success("Alert resolved");
        loadActiveAlerts();
    } catch (err: any) {
        toast.error(err.message || "Unable to resolve alert");
    }
};


  return {
    alerts,
    loading,
    acknowledge,
    resolve
  };
};
