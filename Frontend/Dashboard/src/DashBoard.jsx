import { StaticData } from "./Satcticdata";
const DashBoard = () => {
  // useEffect(() => {
  //   const fetchData = async () => {
  //     try {
  //       const response = await axios.get(
  //         "https://localhost:8080/fetchDashboard"
  //       );
  //       const data = response.data; 

  //       setDashboardData({
  //         summary: {
  //           strength: data.summary.strength,
  //           scopeForImprovement: data.summary.scopeForImprovement,
  //         },
  //         status: {
  //           noOfQueries: data.status.noOfQueries,
  //           inlineCompletions: data.status.inlineCompletions,
  //           overallQuality: data.status.overallQuality,
  //         },
  //         inlineSuggestions: data.inlineSuggestion,
  //         checkStyle: {
  //           checkboxes: data.checkStyle.checkboxes,
  //           occuranceLineNumbers: data.checkStyle.occuranceLineNumbers,
  //           score: data.checkStyle.Score,
  //         },
  //       });
  //     } catch (error) {
  //       console.error("Error fetching dashboard data:", error);
  //     }
  //   };

  //   fetchData();
  // }, []);

  return (
    <div className="p-8 bg-gray-100 min-h-screen">
      <div className="container mx-auto space-y-8">
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-bold mb-4">Summary</h2>
          <p className="mb-2">
            <strong>Strength:</strong> {StaticData.summary.strength}
          </p>
          <p>
            <strong>Scope for Improvement:</strong>{" "}
            {StaticData.summary.scopeForImprovement}
          </p>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-bold mb-4">Status</h2>
          <p className="mb-2">
            <strong>Number of Queries:</strong> {StaticData.status.noOfQueries}
          </p>
          <p className="mb-2">
            <strong>Inline Completions:</strong>{" "}
            {StaticData.status.inlineCompletions}
          </p>
          <p>
            <strong>Overall Quality:</strong> {StaticData.status.overallQuality}
          </p>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-bold mb-4">Inline Suggestions</h2>
          <ul>
            {StaticData.inlineSuggestion.map((suggestion, index) => (
              <li key={index} className="mb-2">
                <strong>Line {suggestion.lineNumber}:</strong> {suggestion.text}
              </li>
            ))}
          </ul>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-bold mb-4">Check Style</h2>
          <div className="mb-4">
            <strong>Checkboxes:</strong>
            <div className="flex space-x-2 mt-2">
              {StaticData.checkStyle.checkboxes.map((checked, index) => (
                <div
                  key={index}
                  className={`p-2 rounded ${
                    checked ? "bg-green-500" : "bg-red-500"
                  }`}>
                  {checked ? "✔️" : "❌"}
                </div>
              ))}
            </div>
          </div>
          <div>
            <strong>Occurrence Line Numbers:</strong>
            <ul className="list-disc pl-5 mt-2">
              {StaticData.checkStyle.occuranceLineNumbers.map(
                (lines, index) => (
                  <li key={index} className="mb-2">
                    Check {index + 1}:{" "}
                    {lines.length > 0 ? lines.join(", ") : "None"}
                  </li>
                )
              )}
            </ul>
          </div>
          <p className="mt-4">
            <strong>Overall Score:</strong> {StaticData.checkStyle.Score}
          </p>
        </div>
      </div>
    </div>
  );
};

export default DashBoard;
