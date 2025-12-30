"use client";

import { useState } from "react";
import { AlertBell } from "./AlertBell";
import { AlertDrawer } from "./AlertDrawer";
import { useAlerts } from "../hooks/useAlerts";
import { Toaster } from "react-hot-toast";

export default function Navbar() {
  const { alerts, acknowledge, resolve } = useAlerts();
  const [open, setOpen] = useState(false);

  return (
    <>
      <nav className="bg-white border-b">
        <div className="max-w-7xl mx-auto px-4 py-3 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="text-lg font-semibold text-gray-700">
              Mechas Mecha
            </div>
            <div className="text-sm text-gray-600">v0.1</div>
          </div>

          <div className="flex items-center gap-4">
            <div className="text-gray-600"><AlertBell alerts={alerts} onClick={() => setOpen(true)} />
</div>
            <div className="text-lg text-gray-600">Adi</div>
          </div>
        </div>
      </nav>

      <AlertDrawer
        open={open}
        alerts={alerts}
        onAcknowledge={acknowledge}
        onResolve={resolve}
        onClose={() => setOpen(false)}
      />
      <Toaster
        position="top-right"
        toastOptions={{
          duration: 3000,
        }}
      />
    </>
  );
}
