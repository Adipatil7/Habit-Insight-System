// app/page.tsx
import DashboardClient from "../components/DashboardClient";

export const metadata = {
  title: "Machine Dashboard",
  description: "Overview and live metrics",
};

export default function Home() {
  return (
    <main className="max-w-7xl mx-auto p-6">
      <DashboardClient />
    </main>
  );
}
