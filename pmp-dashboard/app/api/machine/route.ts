// src/app/api/machine/route.ts
import { NextResponse } from "next/server";

export async function GET() {
  // mock a realistic payload
  const now = Date.now();
  const payload = {
    ts: new Date(now).toISOString(),
    temperature: +(65 + Math.sin(now / 5000) * 6 + (Math.random() * 2)).toFixed(2),
    vibration: +(2 + Math.abs(Math.sin(now / 2000)) * 2 + Math.random() * 0.2).toFixed(2),
    loadPercent: +(60 + Math.sin(now / 7000) * 12 + Math.random() * 3).toFixed(1),
  };

  return NextResponse.json(payload, { status: 200 });
}
