"use client";

import { FileTerminal, Filter } from "lucide-react";
import { Alert, AlertSeverity, AlertStatus } from "../types/alert";
import React, { useEffect, useMemo, useRef, useState } from "react";

type Props = {
  open: boolean;
  alerts: Alert[];
  onAcknowledge: (id: number) => void;
  onResolve: (id: number) => void;
  onClose: () => void;
};

export const AlertDrawer = ({
  open,
  alerts,
  onAcknowledge,
  onResolve,
  onClose,
}: Props) => {
  const drawerRef = useRef<HTMLDivElement | null>(null);
  const [selectedStatus, setSelectedStatus] = useState<AlertStatus | "ALL">(
    "ALL"
  );

  const [selectedSeverity, setSelectedSeverity] = useState<
    AlertSeverity | "ALL"
  >("ALL");
  const [selectedMachine, setSelectedMachine] = useState<string>("ALL");
  const statusOptions: (AlertStatus | "ALL")[] = [
    "ALL",
    "ACTIVE",
    "ACKNOWLEDGED",
    "RESOLVED",
  ];

  const severityOptions: (AlertSeverity | "ALL")[] = [
    "ALL",
    "CRITICAL",
    "WARNING",
    "INFO",
  ];

  const machineOptions = [
    "ALL",
    ...Array.from(new Set(alerts.map((alert) => alert.machineId))),
  ];

  useEffect(() => {
    if (!open) return;

    const handleClickOutside = (event: MouseEvent) => {
      if (
        drawerRef.current &&
        !drawerRef.current.contains(event.target as Node)
      ) {
        onClose();
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, [open, onClose]);

  const filteredData = React.useMemo(() => {
    return alerts.filter((alert) => {
      const statusMatch =
        selectedStatus === "ALL" || alert.status === selectedStatus;
      const severityMatch =
        selectedSeverity === "ALL" || alert.severity === selectedSeverity;
      const machineMatch =
        selectedMachine === "ALL" || alert.machineId === selectedMachine;
      return statusMatch && severityMatch && machineMatch;
    });
  }, [alerts, selectedStatus, selectedSeverity, selectedMachine]);

  return (
    <div
      className={`fixed inset-0 z-40 ${
        open ? "pointer-events-auto" : "pointer-events-none"
      }`}
    >
      {/* Backdrop */}
      <div
        className={`absolute inset-0 bg-black/30 transition-opacity duration-300 ${
          open ? "opacity-100" : "opacity-0"
        }`}
      />

      {/* Drawer */}
      <div
        ref={drawerRef}
        className={`absolute right-0 top-0 h-full w-96 bg-white shadow-lg p-4 overflow-y-auto
        transform transition-transform duration-300 ease-in-out
        ${open ? "translate-x-0" : "translate-x-full"}`}
      >
        <div className="flex items-center justify-between gap-2 mb-3">
          <h2 className="text-lg font-semibold text-red-700 whitespace-nowrap">
            Alerts
          </h2>
          <div className="flex items-center gap-1 ">
            <select
              value={selectedStatus}
              onChange={(e) =>
                setSelectedStatus(e.target.value as AlertStatus | "ALL")
              }
              className="border border-gray-300 rounded-md px-1.5 py-0.5 text-xs text-black w-[90px]"
            >
              {statusOptions.map((status) => (
                <option key={status} value={status}>
                  {status}
                </option>
              ))}
            </select>

            <select
              value={selectedSeverity}
              onChange={(e) =>
                setSelectedSeverity(e.target.value as AlertSeverity | "ALL")
              }
              className="border border-gray-300 rounded-md px-1.5 py-0.5 text-xs text-black w-[95px]"
            >
              {severityOptions.map((severity) => (
                <option key={severity} value={severity}>
                  {severity}
                </option>
              ))}
            </select>

            <select
              value={selectedMachine}
              onChange={(e) => setSelectedMachine(e.target.value)}
              className="border border-gray-300 rounded-md px-1.5 py-0.5 text-xs text-black w-[110px]"
            >
              {machineOptions.map((machine) => (
                <option key={machine} value={machine}>
                  {machine}
                </option>
              ))}
            </select>
          </div>
        </div>

        {filteredData.length === 0 && (
          <p className="text-sm text-gray-500">No active alerts</p>
        )}

        {filteredData.map((alert) => (
          <div key={alert.id} className="border p-3 mb-2 rounded">
            <div className="flex justify-between">
              <span className="font-medium text-black ">{alert.machineId}</span>
              <span
                className={`text-sm font-semibold ${
                  alert.severity === "CRITICAL"
                    ? "text-red-600"
                    : "text-yellow-600"
                }`}
              >
                {alert.severity}
              </span>
            </div>

            <p className="text-sm mt-1 text-gray-600">{alert.message}</p>

            <div className="flex gap-2 mt-2">
              <button
                className="text-xs px-2 py-1 border rounded text-gray-600 bg-blue-400"
                onClick={() => onAcknowledge(alert.id)}
              >
                Ack
              </button>
              <button
                className="text-xs px-2 py-1 border rounded text-gray-600 bg-green-400"
                onClick={() => onResolve(alert.id)}
              >
                Resolve
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
