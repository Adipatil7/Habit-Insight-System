// src/app/layout.tsx
import "./globals.css";
import React from "react";

export const metadata = {
  title: "Mechas Mecha",
  description: "Predictive maintenance platform",
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body className="min-h-screen bg-gray-50 text-gray-800">
        <div className="min-h-screen flex flex-col">
          <nav className="bg-white border-b">
            <div className="max-w-7xl mx-auto px-4 py-3 flex items-center justify-between">
              <div className="flex items-center gap-3">
                <div className="text-lg font-semibold text-gray-700">Mechas Mecha</div>
                <div className="text-sm text-gray-600">v0.1</div>
              </div>
              <div className="text-sm text-gray-600">Adi</div>
            </div>
          </nav>

          <main className="flex-1">
            {children}
          </main>

          <footer className="border-t bg-white text-xs text-gray-500">
            <div className="max-w-7xl mx-auto px-4 py-3">Built with Next.js · Iterative UI</div>
          </footer>
        </div>
      </body>
    </html>
  );
}
