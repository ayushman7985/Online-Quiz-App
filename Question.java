import java.util.List;
import java.util.ArrayList;

/**
 * Question class representing a quiz question
 * Supports multiple choice questions with validation
 */
public class Question {
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;
    private String category;
    private int points;

    // Constructor
    public Question(String questionText, List<String> options, int correctAnswerIndex, String category, int points) {
        this.questionText = questionText;
        this.options = new ArrayList<>(options);
        this.correctAnswerIndex = correctAnswerIndex;
        this.category = category;
        this.points = points;
    }

    // Constructor with default points
    public Question(String questionText, List<String> options, int correctAnswerIndex, String category) {
        this(questionText, options, correctAnswerIndex, category, 10);
    }

    // Getters
    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return new ArrayList<>(options); }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
    public String getCategory() { return category; }
    public int getPoints() { return points; }

    // Setters
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public void setOptions(List<String> options) { this.options = new ArrayList<>(options); }
    public void setCorrectAnswerIndex(int correctAnswerIndex) { this.correctAnswerIndex = correctAnswerIndex; }
    public void setCategory(String category) { this.category = category; }
    public void setPoints(int points) { this.points = points; }

    /**
     * Check if the given answer is correct
     * @param answerIndex The index of the selected answer (0-based)
     * @return true if correct, false otherwise
     */
    public boolean isCorrect(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }

    /**
     * Get the correct answer text
     * @return The text of the correct answer
     */
    public String getCorrectAnswerText() {
        if (correctAnswerIndex >= 0 && correctAnswerIndex < options.size()) {
            return options.get(correctAnswerIndex);
        }
        return "Invalid answer index";
    }

    /**
     * Display the question with options
     * @return Formatted question string
     */
    public String displayQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append(questionText).append("\n");
        
        for (int i = 0; i < options.size(); i++) {
            sb.append((char)('A' + i)).append(") ").append(options.get(i)).append("\n");
        }
        
        return sb.toString();
    }

    /**
     * Validate question data
     * @return true if question is valid, false otherwise
     */
    public boolean isValid() {
        return questionText != null && !questionText.trim().isEmpty() &&
               options != null && options.size() >= 2 &&
               correctAnswerIndex >= 0 && correctAnswerIndex < options.size() &&
               category != null && !category.trim().isEmpty() &&
               points > 0;
    }

    @Override
    public String toString() {
        return String.format("Question{category='%s', points=%d, text='%s', options=%d}",
                category, points, questionText.substring(0, Math.min(50, questionText.length())), options.size());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Question question = (Question) obj;
        return correctAnswerIndex == question.correctAnswerIndex &&
               points == question.points &&
               questionText.equals(question.questionText) &&
               options.equals(question.options) &&
               category.equals(question.category);
    }

    @Override
    public int hashCode() {
        return questionText.hashCode() + correctAnswerIndex + points;
    }
}
