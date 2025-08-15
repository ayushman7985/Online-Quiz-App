import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

/**
 * QuizResult class to store quiz attempt results
 * Tracks score, answers, and performance metrics
 */
public class QuizResult {
    private String playerName;
    private int totalQuestions;
    private int correctAnswers;
    private int totalScore;
    private int maxPossibleScore;
    private LocalDateTime completionTime;
    private long timeTakenSeconds;
    private String category;
    private List<QuestionResult> questionResults;

    // Constructor
    public QuizResult(String playerName, String category) {
        this.playerName = playerName;
        this.category = category;
        this.totalQuestions = 0;
        this.correctAnswers = 0;
        this.totalScore = 0;
        this.maxPossibleScore = 0;
        this.completionTime = LocalDateTime.now();
        this.timeTakenSeconds = 0;
        this.questionResults = new ArrayList<>();
    }

    // Inner class to store individual question results
    public static class QuestionResult {
        private String questionText;
        private String selectedAnswer;
        private String correctAnswer;
        private boolean isCorrect;
        private int pointsEarned;
        private int maxPoints;

        public QuestionResult(String questionText, String selectedAnswer, String correctAnswer, 
                            boolean isCorrect, int pointsEarned, int maxPoints) {
            this.questionText = questionText;
            this.selectedAnswer = selectedAnswer;
            this.correctAnswer = correctAnswer;
            this.isCorrect = isCorrect;
            this.pointsEarned = pointsEarned;
            this.maxPoints = maxPoints;
        }

        // Getters
        public String getQuestionText() { return questionText; }
        public String getSelectedAnswer() { return selectedAnswer; }
        public String getCorrectAnswer() { return correctAnswer; }
        public boolean isCorrect() { return isCorrect; }
        public int getPointsEarned() { return pointsEarned; }
        public int getMaxPoints() { return maxPoints; }
    }

    // Add a question result
    public void addQuestionResult(Question question, int selectedAnswerIndex, String selectedAnswerText) {
        boolean isCorrect = question.isCorrect(selectedAnswerIndex);
        int pointsEarned = isCorrect ? question.getPoints() : 0;
        
        QuestionResult result = new QuestionResult(
            question.getQuestionText(),
            selectedAnswerText,
            question.getCorrectAnswerText(),
            isCorrect,
            pointsEarned,
            question.getPoints()
        );
        
        questionResults.add(result);
        totalQuestions++;
        maxPossibleScore += question.getPoints();
        
        if (isCorrect) {
            correctAnswers++;
            totalScore += question.getPoints();
        }
    }

    // Calculate percentage score
    public double getPercentageScore() {
        if (maxPossibleScore == 0) return 0.0;
        return (double) totalScore / maxPossibleScore * 100.0;
    }

    // Get grade based on percentage
    public String getGrade() {
        double percentage = getPercentageScore();
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B";
        else if (percentage >= 60) return "C";
        else if (percentage >= 50) return "D";
        else return "F";
    }

    // Get performance message
    public String getPerformanceMessage() {
        double percentage = getPercentageScore();
        if (percentage >= 90) return "Excellent! Outstanding performance!";
        else if (percentage >= 80) return "Great job! Very good performance!";
        else if (percentage >= 70) return "Good work! Above average performance!";
        else if (percentage >= 60) return "Fair performance. Keep practicing!";
        else if (percentage >= 50) return "Below average. More study needed.";
        else return "Poor performance. Please review the material.";
    }

    // Generate detailed report
    public String generateDetailedReport() {
        StringBuilder report = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        report.append("=".repeat(60)).append("\n");
        report.append("                    QUIZ RESULT REPORT\n");
        report.append("=".repeat(60)).append("\n");
        report.append("Player Name: ").append(playerName).append("\n");
        report.append("Category: ").append(category).append("\n");
        report.append("Completion Time: ").append(completionTime.format(formatter)).append("\n");
        report.append("Time Taken: ").append(formatTime(timeTakenSeconds)).append("\n");
        report.append("-".repeat(60)).append("\n");
        
        report.append("SCORE SUMMARY:\n");
        report.append("Total Questions: ").append(totalQuestions).append("\n");
        report.append("Correct Answers: ").append(correctAnswers).append("\n");
        report.append("Wrong Answers: ").append(totalQuestions - correctAnswers).append("\n");
        report.append("Score: ").append(totalScore).append("/").append(maxPossibleScore).append("\n");
        report.append("Percentage: ").append(String.format("%.1f", getPercentageScore())).append("%\n");
        report.append("Grade: ").append(getGrade()).append("\n");
        report.append("Performance: ").append(getPerformanceMessage()).append("\n");
        report.append("-".repeat(60)).append("\n");
        
        report.append("QUESTION-BY-QUESTION BREAKDOWN:\n");
        for (int i = 0; i < questionResults.size(); i++) {
            QuestionResult qr = questionResults.get(i);
            report.append("Q").append(i + 1).append(": ");
            report.append(qr.isCorrect() ? "✓ CORRECT" : "✗ WRONG");
            report.append(" (").append(qr.getPointsEarned()).append("/").append(qr.getMaxPoints()).append(" points)\n");
            
            // Truncate long questions for summary
            String questionPreview = qr.getQuestionText();
            if (questionPreview.length() > 50) {
                questionPreview = questionPreview.substring(0, 47) + "...";
            }
            report.append("    Question: ").append(questionPreview).append("\n");
            report.append("    Your Answer: ").append(qr.getSelectedAnswer()).append("\n");
            if (!qr.isCorrect()) {
                report.append("    Correct Answer: ").append(qr.getCorrectAnswer()).append("\n");
            }
            report.append("\n");
        }
        
        report.append("=".repeat(60)).append("\n");
        return report.toString();
    }

    // Format time in minutes and seconds
    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%d:%02d", minutes, remainingSeconds);
    }

    // Getters
    public String getPlayerName() { return playerName; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCorrectAnswers() { return correctAnswers; }
    public int getTotalScore() { return totalScore; }
    public int getMaxPossibleScore() { return maxPossibleScore; }
    public LocalDateTime getCompletionTime() { return completionTime; }
    public long getTimeTakenSeconds() { return timeTakenSeconds; }
    public String getCategory() { return category; }
    public List<QuestionResult> getQuestionResults() { return new ArrayList<>(questionResults); }

    // Setters
    public void setTimeTakenSeconds(long timeTakenSeconds) { this.timeTakenSeconds = timeTakenSeconds; }
    public void setCompletionTime(LocalDateTime completionTime) { this.completionTime = completionTime; }

    @Override
    public String toString() {
        return String.format("QuizResult{player='%s', score=%d/%d (%.1f%%), grade='%s'}",
                playerName, totalScore, maxPossibleScore, getPercentageScore(), getGrade());
    }
}
