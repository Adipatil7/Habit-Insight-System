// src/components/DashboardClient.tsx
"use client";

import React from "react";
import { useMachine } from "@/hooks/useMachine";
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
  const [machineId, setMachineId] = React.useState<string>("MCH-1");
  const { data, error, isLoading } = useMachine(machineId, 6000);
  const MACHINES = ["MCH-1", "MCH-2", "MCH-3", "MCH-4", "MCH-5"];
  const [attr, setAttr] = React.useState<
    "temperature" | "vibration" | "pressure"
  >("temperature");

  const chartData = React.useMemo(() => {
    if (!data) {
      return Array.from({ length: 20 }).map((_, i) => ({
        ts: `T${i + 1}`,
        value: 0,
      }));
    }

    return Array.from({ length: 20 }).map((_, i) => ({
      ts: `${i + 1}`,
      value: Number((data[attr] + Math.sin(i / 3) * 1.2).toFixed(2)),
    }));
  }, [data, attr]);

  return (
    <div>
      <div className="flex items-center justify-end mb-4">
        <label className="text-sm text-gray-200 mr-2">Select machine:</label>

        <select
          value={machineId}
          onChange={(e) => setMachineId(e.target.value)}
          className="border border-gray-300 rounded-lg px-3 py-1 text-sm bg-black focus:outline-none focus:ring-2 focus:ring-gray-300"
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
            Machine: <span className="font-medium">{machineId}</span> · Live
            metrics
          </p>
        </div>

        <div className="flex items-center gap-3">
          <div className="text-sm text-gray-500 hidden sm:block">
            Environment: staging
          </div>
          <div className="text-sm text-gray-500">v0.1</div>
        </div>
      </header>

      {/* Stat cards */}
      <section className="grid grid-cols-1 sm:grid-cols-3 gap-4 mb-6">
        <StatCard
          title="Temperature"
          value={
            isLoading
              ? "—"
              : `${data ? data.temperature.toFixed(2) : "72.0"} °C`
          }
          delta={data ? "stable" : "demo"}
        />

        <StatCard
          title="Vibration"
          value={
            isLoading ? "—" : `${data ? data.vibration.toFixed(2) : "3.4"} m/s²`
          }
          delta={data ? "nominal" : "demo"}
        />

        <StatCard
          title="Pressure"
          value={
            isLoading ? "—" : `${data ? data.pressure.toFixed(2) : "8.5"} bar`
          }
          delta={data ? "normal" : "demo"}
        />
      </section>

      {/* Chart + Events */}
      <section className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Chart */}
        <div className="lg:col-span-2 bg-white rounded-2xl shadow-sm p-4">
          <div className="flex items-center justify-between mb-4">
            {/* Left: title */}
            <h2 className="text-lg font-medium text-gray-900 capitalize">
              {attr} (last samples)
            </h2>

            {/* Right: controls */}
            <div className="flex items-center gap-3">
              {/* Attribute selector */}
              <select
                value={attr}
                onChange={(e) =>
                  setAttr(
                    e.target.value as "temperature" | "vibration" | "pressure"
                  )
                }
                className="border border-gray-300 text-gray-900 rounded-lg px-3 py-1 text-sm bg-white focus:outline-none focus:ring-2 focus:ring-gray-300 capitalize"
              >
                <option value="temperature">Temperature</option>
                <option value="vibration">Vibration</option>
                <option value="pressure">Pressure</option>
              </select>

              {/* Timestamp */}
              <div className="text-sm text-gray-600">
                {data ? new Date(data.timeStamp).toLocaleTimeString() : "demo"}
              </div>
            </div>
          </div>

          {/* Chart */}
          <div className="w-full h-80">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart
                data={chartData}
                margin={{ top: 8, right: 16, left: 0, bottom: 0 }}
              >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="ts" tick={{ fontSize: 12 }} />
                <YAxis
                  tick={{ fontSize: 12 }}
                  tickFormatter={(v) => v.toFixed(1)}
                />
                <Tooltip />
                <Line
                  type="monotone"
                  dataKey="value"
                  dot={false}
                  stroke="#1f2937"
                  strokeWidth={2}
                />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Recent events */}
        <aside className="bg-white rounded-2xl shadow-sm p-4">
          <h3 className="text-base font-medium text-gray-900 mb-3">
            Recent events
          </h3>

          <div className="text-sm text-gray-700">
            {error ? (
              <div className="text-red-600">Error fetching data</div>
            ) : isLoading ? (
              <div>Loading latest readings…</div>
            ) : !data ? (
              <div className="text-gray-500">No data available</div>
            ) : (
              <ul className="space-y-3">
                <li>
                  <div className="font-medium text-gray-900">
                    Temperature: {data.temperature.toFixed(2)} °C
                  </div>
                  <div className="text-xs text-gray-600">
                    {new Date(data.timeStamp).toLocaleString()}
                  </div>
                </li>

                <li>
                  <div className="font-medium text-gray-900">
                    Vibration: {data.vibration.toFixed(2)} m/s²
                  </div>
                  <div className="text-xs text-gray-600">Sensor nominal</div>
                </li>

                <li>
                  <div className="font-medium text-gray-900">
                    Pressure: {data.pressure.toFixed(2)} bar
                  </div>
                  <div className="text-xs text-gray-600">Within safe range</div>
                </li>

                <li>
                  <div className="font-medium text-gray-900">
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
                  </div>
                </li>
              </ul>
            )}
          </div>
        </aside>
      </section>
    </div>
  );
}
