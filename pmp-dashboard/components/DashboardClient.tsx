// src/components/DashboardClient.tsx
"use client";

import React from "react";
import { useMachine } from "@/hooks/useMachine";
import { useMachineCustomRange } from "@/hooks/useMachineCustomeRange";
import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
} from "recharts";

function StatCard({
  title,
  value,
  delta,
}: {
  title: string;
  value: React.ReactNode;
  delta?: string;
}) {
  return (
    <div className="bg-white rounded-2xl shadow-sm p-4 flex items-center justify-between">
      <div>
        <div className="text-xs text-gray-600">{title}</div>
        <div className="mt-1 text-2xl font-semibold tracking-tight text-gray-900">
          {value}
        </div>
        {delta && <div className="text-sm text-gray-500 mt-1">{delta}</div>}
      </div>
      <div className="w-12 h-12 rounded-lg bg-gray-200 flex items-center justify-center text-sm text-gray-700">
        {title.slice(0, 2)}
      </div>
    </div>
  );
}

export default function DashboardClient() {
  const [machineId, setMachineId] = React.useState("MCH-1");

  // Live snapshot (polling)
  const { data, error, isLoading } = useMachine(machineId, 3000);

  // Time range state
  const [range, setRange] = React.useState<"1h" | "6h" | "24h">("1h");
  const [from, setFrom] = React.useState<Date>(
    () => new Date(Date.now() - 60 * 60 * 1000)
  );
  const [to, setTo] = React.useState<Date>(() => new Date());

  // Custom range data (NO polling)
  const {
    data: custom,
    loading: customLoading,
    error: customError,
  } = useMachineCustomRange(machineId, from, to);

  const [attr, setAttr] = React.useState<
    "temperature" | "vibration" | "pressure"
  >("temperature");

  // ✅ FIX: Use useCallback to memoize the function properly
  const applyRange = React.useCallback(() => {
    const now = new Date();
    let hours = 1;
    if (range === "6h") hours = 6;
    if (range === "24h") hours = 24;

    setTo(now);
    setFrom(new Date(now.getTime() - hours * 60 * 60 * 1000));
  }, [range]); // ✅ Include range as dependency

  // ✅ FIX: Remove the initial useEffect, rely on default state values
  // The initial state already sets from/to correctly, no need to call applyRange on mount

  // ✅ FIXED chartData (uses custom data)
  const chartData = React.useMemo(() => {
    if (!custom || custom.length === 0) {
      console.log("No custom data available for chart");
      return [];
    }
    console.log("Custom data for chart:", custom);
    return custom.map((row) => ({
      ts: new Date(row.timeStamp).toLocaleTimeString(),
      value: row[attr],
    }));
  }, [custom, attr]);

  React.useEffect(() => {
    console.log("CUSTOM DATA ARRIVED:", custom?.length);
  }, [custom]);

  return (
    <div>
      {/* Machine selector */}
      <div className="flex items-center justify-end mb-4">
        <label className="text-sm text-gray-200 mr-2">Select machine:</label>
        <select
          value={machineId}
          onChange={(e) => setMachineId(e.target.value)}
          className="border border-gray-300 rounded-lg px-3 py-1 text-sm bg-black text-white"
        >
          {["MCH-1", "MCH-2", "MCH-3", "MCH-4", "MCH-5"].map((id) => (
            <option key={id} value={id}>
              {id}
            </option>
          ))}
        </select>
      </div>

      {/* Header */}
      <header className="flex items-center justify-between mb-6">
        <div>
          <h1 className="text-2xl font-semibold text-gray-200">
            Predictive Maintenance Platform
          </h1>
          <p className="text-sm text-gray-300">
            Machine: <span className="font-medium">{machineId}</span>
          </p>
        </div>
        <div className="text-sm text-gray-500">v0.1</div>
      </header>

      {/* Stat cards */}
      <section className="grid grid-cols-1 sm:grid-cols-3 gap-4 mb-6">
        <StatCard
          title="Temperature"
          value={
            isLoading ? "—" : `${data ? data.temperature.toFixed(2) : "—"} °C`
          }
        />
        <StatCard
          title="Vibration"
          value={
            isLoading ? "—" : `${data ? data.vibration.toFixed(2) : "—"} m/s²`
          }
        />
        <StatCard
          title="Pressure"
          value={
            isLoading ? "—" : `${data ? data.pressure.toFixed(2) : "—"} bar`
          }
        />
      </section>

      {/* Chart + Events */}
      <section className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Chart */}
        <div className="lg:col-span-2 bg-white rounded-2xl shadow-sm p-4">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-lg font-medium text-gray-900 capitalize">
              {attr} trend
            </h2>

            <div className="flex items-center gap-3">
              <select
                value={attr}
                onChange={(e) =>
                  setAttr(
                    e.target.value as "temperature" | "vibration" | "pressure"
                  )
                }
                className="border border-gray-600 rounded-lg px-3 py-1 text-sm bg-white text-black"
              >
                <option value="temperature">Temperature</option>
                <option value="vibration">Vibration</option>
                <option value="pressure">Pressure</option>
              </select>

              <select
                value={range}
                onChange={(e) =>
                  setRange(e.target.value as "1h" | "6h" | "24h")
                }
                className="border border-gray-600 text-black rounded-lg px-3 py-1 text-sm bg-white"
              >
                <option value="1h">Last 1 hour</option>
                <option value="6h">Last 6 hours</option>
                <option value="24h">Last 24 hours</option>
              </select>

              <button
                onClick={applyRange}
                className="px-4 py-1.5 text-sm rounded-lg bg-gray-900 text-white hover:bg-gray-800"
              >
                Apply
              </button>
            </div>
          </div>

          {customLoading ? (
            <div className="h-80 flex items-center justify-center text-gray-500">
              Loading chart data…
            </div>
          ) : customError ? (
            <div className="h-80 flex items-center justify-center text-red-500">
              Failed to load historical data
            </div>
          ) : chartData.length === 0 ? (
            <div className="h-80 flex items-center justify-center text-gray-400">
              No historical data available
            </div>
          ) : (
            <div className="w-full h-80">
              <ResponsiveContainer>
                <LineChart data={chartData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="ts" />
                  <YAxis tickFormatter={(v) => v.toFixed(1)} />
                  <Tooltip />
                  <Line
                    type="monotone"
                    dataKey="value"
                    stroke="#1f2937"
                    strokeWidth={2}
                    dot={false}
                  />
                </LineChart>
              </ResponsiveContainer>
            </div>
          )}
        </div>

        {/* Recent events */}
        <aside className="bg-white rounded-2xl shadow-sm p-4">
          <h3 className="text-base font-medium text-gray-900 mb-3">
            Recent events
          </h3>

          {error ? (
            <div className="text-red-600">Error fetching data</div>
          ) : isLoading ? (
            <div>Loading latest readings…</div>
          ) : !data ? (
            <div className="text-gray-500">No data available</div>
          ) : (
            <ul className="space-y-2 text-sm text-gray-800">
              <li>Temperature: {data.temperature.toFixed(2)} °C</li>
              <li>Vibration: {data.vibration.toFixed(2)} m/s²</li>
              <li>Pressure: {data.pressure.toFixed(2)} bar</li>
              
              <li>
                Status:{" "}
                <span
                  className={
                    data.status === "CRITICAL"
                      ? "text-red-600"
                      : data.status === "WARNING"
                      ? "text-yellow-600"
                      : "text-green-600"
                  }
                >
                  {data.status}
                </span>
              </li>
            </ul>
          )}
        </aside>
      </section>
    </div>
  );
}