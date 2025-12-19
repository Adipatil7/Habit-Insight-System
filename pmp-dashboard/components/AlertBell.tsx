"use client";

import { Bell } from "lucide-react";
import { Alert } from "../types/alert";

type Props = {
  alerts: Alert[];
  onClick: () => void;
};

export const AlertBell = ({ alerts, onClick }: Props) => {
  const activeAlerts = alerts.filter(
    (alert) => alert.status === "ACTIVE"
  );

  if (activeAlerts.length === 0) {
    return (
      <div className="cursor-pointer" onClick={onClick}>
        <Bell />
      </div>
    );
  }

  const hasCritical = activeAlerts.some(
    (alert) => alert.severity === "CRITICAL"
  );

  const hasWarning = activeAlerts.some(
    (alert) => alert.severity === "WARNING"
  );

  const badgeColor = hasCritical
    ? "bg-red-600"
    : hasWarning
    ? "bg-yellow-500"
    : "bg-blue-500";

  return (
    <div className="relative cursor-pointer" onClick={onClick}>
      <Bell />
      <span
        className={`absolute -top-1 -right-1 ${badgeColor}
        text-white text-xs px-1.5 py-0.5 rounded-full`}
      >
        {activeAlerts.length}
      </span>
    </div>
  );
};
