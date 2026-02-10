import { useEffect, useState } from "react";
import api from "../api/axios";
import { BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid } from "recharts";
import { PieChart, Pie, Cell } from "recharts";
import Banner from "./Banner";

function Reports({ userId }) {
    const [reportData, setReportData] = useState([]);

    useEffect(() => {
        api.get(`/api/reports/skills/${userId}`)
            .then(res => setReportData(res.data));
    }, [userId]);

    const LevelCounts = reportData.reduce((acc, s) => {
        acc[s.level] = (acc[s.level] || 0) + 1;
        return acc;
    }, {});

    const pieData = Object.entries(LevelCounts).map(([name, value]) => ({ name, value }));
    const COLOURS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042"];

    function exportToCSV(data) {
        const headers = ["Skill", "Progress", "Level", "Created At"];

        const rows = data.map(d =>
            [d.skillName, d.progress, d.level, d.createdAt]
        );

        const csv = [
            headers.join(","),
            ...rows.map(r => r.join(","))
        ].join("\n");

        const blob = new Blob([csv], { type: "text/csv" });
        const url = URL.createObjectURL(blob);

        const a = document.createElement("a");
        a.href = url;
        a.download = "skills.csv";
        a.click();
    }

    return (
        <>
        <Banner/>
         <div className="min-h-screen bg-gray-800 px-6 py-10">
            <div className="max-w-6xl mx-auto space-y-8">
                <h2 className="text-2xl font-semibold text-gray-800">Skill Reports</h2>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                    <div className="bg-white rounded-lg shadow p-6">
                        <h3 className="text-lg font-medium text-gray-700 mb-4">Skill Progress</h3>
                        <BarChart width={500} height={300} data={reportData}>
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis dataKey="skillName" />
                            <YAxis />
                            <Tooltip />
                            <Bar dataKey="progress" />
                        </BarChart>
                    </div>

                    <div className="bg-white rounded-lg shadow p-6">
                        <h3 className="text-lg font-medium text-gray-700 mb-4">Skill Levels Distrbution</h3>
                        <PieChart width={400} height={300}>
                            <Pie
                                data={pieData}
                                dataKey="value"
                                nameKey="name"
                                cx="50%"
                                cy="50%"
                                outerRadius={100}
                                label
                            >
                                {pieData.map((entry, index) => (
                                    <Cell
                                        key={`cell-${index}`}
                                        fill={COLOURS[index % COLOURS.length]}
                                    />
                                ))}

                            </Pie>

                            <Tooltip />

                        </PieChart>
                    </div>

                    <div className="flex justify-start">
                        <button 
                        className="px-5 py-2 bg-green-600 text-white text-sm font-medium rounded-md hover:bg-green-700 transition"
                        onClick={() => exportToCSV(reportData)}
                        >
                            Export CSV
                        </button>
                    </div>

                </div>
            </div>
        </div>
        </>
       
    );
}

export default Reports;