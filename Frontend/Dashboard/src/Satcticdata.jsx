export const StaticData = {
  status: { noOfQueries: 10, inlineCompletions: 15, overallQuality: 85 },
  inlineSuggestion: [
    {
      lineNumber: 10,
      text: "Use a more descriptive variable name instead of 'x'",
    },
    {
      lineNumber: 25,
      text: "Consider extracting this logic into a separate function for better readability",
    },
  ],
  checkStyle: {
    checkboxes: [true, false, true, true, true, true, true, true, true, true],
    occuranceLineNumbers: [
      [10, 15, 20],
      [],
      [25, 30],
      [35, 40],
      [45, 50],
      [55, 60],
      [65, 70],
      [75, 80],
      [85, 90],
      [95, 100],
    ],
    Score: 8,
  },
  summary: {
    strength:
      "The code demonstrates strong adherence to naming conventions, code reusability, and cohesion. ",
    scopeForImprovement:
      "There are opportunities to enhance code structure and reduce complexity in certain sections.",
  },
};
