import java.util.*;

/**
 * QuizManager class to manage quiz questions and operations
 * Handles question loading, quiz creation, and category management
 */
public class QuizManager {
    private List<Question> allQuestions;
    private Map<String, List<Question>> questionsByCategory;
    private Random random;

    public QuizManager() {
        this.allQuestions = new ArrayList<>();
        this.questionsByCategory = new HashMap<>();
        this.random = new Random();
        loadDefaultQuestions();
    }

    /**
     * Load default questions for the quiz
     */
    private void loadDefaultQuestions() {
        // Java Programming Questions
        addQuestion("What is the correct way to declare a variable in Java?",
                Arrays.asList("int x = 5;", "variable int x = 5;", "declare int x = 5;", "x = 5 int;"),
                0, "Java Programming", 10);

        addQuestion("Which keyword is used to create a class in Java?",
                Arrays.asList("class", "Class", "create", "new"),
                0, "Java Programming", 10);

        addQuestion("What is the main method signature in Java?",
                Arrays.asList("public static void main(String[] args)", "public void main(String args)", "static void main(String[] args)", "public main(String[] args)"),
                0, "Java Programming", 15);

        addQuestion("Which of these is NOT a primitive data type in Java?",
                Arrays.asList("int", "String", "boolean", "char"),
                1, "Java Programming", 10);

        addQuestion("What does JVM stand for?",
                Arrays.asList("Java Virtual Machine", "Java Variable Method", "Java Visual Manager", "Java Version Manager"),
                0, "Java Programming", 10);

        // General Knowledge Questions
        addQuestion("What is the capital of France?",
                Arrays.asList("London", "Berlin", "Paris", "Madrid"),
                2, "General Knowledge", 5);

        addQuestion("Which planet is known as the Red Planet?",
                Arrays.asList("Venus", "Mars", "Jupiter", "Saturn"),
                1, "General Knowledge", 5);

        addQuestion("Who painted the Mona Lisa?",
                Arrays.asList("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"),
                2, "General Knowledge", 10);

        addQuestion("What is the largest ocean on Earth?",
                Arrays.asList("Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"),
                3, "General Knowledge", 5);

        addQuestion("In which year did World War II end?",
                Arrays.asList("1944", "1945", "1946", "1947"),
                1, "General Knowledge", 10);

        // Science Questions
        addQuestion("What is the chemical symbol for gold?",
                Arrays.asList("Go", "Gd", "Au", "Ag"),
                2, "Science", 10);

        addQuestion("How many bones are there in an adult human body?",
                Arrays.asList("206", "208", "210", "212"),
                0, "Science", 15);

        addQuestion("What is the speed of light in vacuum?",
                Arrays.asList("300,000 km/s", "299,792,458 m/s", "186,000 miles/s", "All of the above"),
                3, "Science", 15);

        addQuestion("Which gas makes up about 78% of Earth's atmosphere?",
                Arrays.asList("Oxygen", "Carbon Dioxide", "Nitrogen", "Argon"),
                2, "Science", 10);

        addQuestion("What is the smallest unit of matter?",
                Arrays.asList("Molecule", "Atom", "Electron", "Proton"),
                1, "Science", 10);

        // Mathematics Questions
        addQuestion("What is the value of π (pi) approximately?",
                Arrays.asList("3.14", "3.141", "3.1416", "All are correct approximations"),
                3, "Mathematics", 10);

        addQuestion("What is 15% of 200?",
                Arrays.asList("25", "30", "35", "40"),
                1, "Mathematics", 10);

        addQuestion("What is the square root of 144?",
                Arrays.asList("11", "12", "13", "14"),
                1, "Mathematics", 5);

        addQuestion("In a right triangle, what is the relationship between the sides?",
                Arrays.asList("a + b = c", "a² + b² = c²", "a × b = c", "a - b = c"),
                1, "Mathematics", 15);

        addQuestion("What is the result of 2³ × 3²?",
                Arrays.asList("36", "54", "72", "108"),
                2, "Mathematics", 15);

        System.out.println("Loaded " + allQuestions.size() + " questions across " + questionsByCategory.size() + " categories.");
    }

    /**
     * Add a question to the quiz manager
     */
    public void addQuestion(String questionText, List<String> options, int correctAnswerIndex, String category, int points) {
        Question question = new Question(questionText, options, correctAnswerIndex, category, points);
        if (question.isValid()) {
            allQuestions.add(question);
            questionsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(question);
        }
    }

    /**
     * Get all available categories
     */
    public Set<String> getAvailableCategories() {
        return new HashSet<>(questionsByCategory.keySet());
    }

    /**
     * Get questions by category
     */
    public List<Question> getQuestionsByCategory(String category) {
        return new ArrayList<>(questionsByCategory.getOrDefault(category, new ArrayList<>()));
    }

    /**
     * Create a quiz with specified number of questions from a category
     */
    public List<Question> createQuiz(String category, int numberOfQuestions) {
        List<Question> categoryQuestions = getQuestionsByCategory(category);
        
        if (categoryQuestions.isEmpty()) {
            return new ArrayList<>();
        }

        // Shuffle questions and select the requested number
        Collections.shuffle(categoryQuestions, random);
        int questionsToSelect = Math.min(numberOfQuestions, categoryQuestions.size());
        
        return new ArrayList<>(categoryQuestions.subList(0, questionsToSelect));
    }

    /**
     * Create a mixed quiz from all categories
     */
    public List<Question> createMixedQuiz(int numberOfQuestions) {
        List<Question> shuffledQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(shuffledQuestions, random);
        
        int questionsToSelect = Math.min(numberOfQuestions, shuffledQuestions.size());
        return new ArrayList<>(shuffledQuestions.subList(0, questionsToSelect));
    }

    /**
     * Get quiz statistics
     */
    public String getQuizStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== QUIZ STATISTICS ===\n");
        stats.append("Total Questions: ").append(allQuestions.size()).append("\n");
        stats.append("Categories: ").append(questionsByCategory.size()).append("\n\n");
        
        stats.append("Questions by Category:\n");
        for (Map.Entry<String, List<Question>> entry : questionsByCategory.entrySet()) {
            stats.append("- ").append(entry.getKey()).append(": ").append(entry.getValue().size()).append(" questions\n");
        }
        
        return stats.toString();
    }

    /**
     * Search questions by keyword
     */
    public List<Question> searchQuestions(String keyword) {
        List<Question> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        
        for (Question question : allQuestions) {
            if (question.getQuestionText().toLowerCase().contains(lowerKeyword) ||
                question.getCategory().toLowerCase().contains(lowerKeyword)) {
                results.add(question);
            }
        }
        
        return results;
    }

    /**
     * Get difficulty-based questions (based on points)
     */
    public List<Question> getQuestionsByDifficulty(String difficulty) {
        List<Question> results = new ArrayList<>();
        int minPoints, maxPoints;
        
        switch (difficulty.toLowerCase()) {
            case "easy":
                minPoints = 1; maxPoints = 5;
                break;
            case "medium":
                minPoints = 6; maxPoints = 10;
                break;
            case "hard":
                minPoints = 11; maxPoints = 20;
                break;
            default:
                return results;
        }
        
        for (Question question : allQuestions) {
            if (question.getPoints() >= minPoints && question.getPoints() <= maxPoints) {
                results.add(question);
            }
        }
        
        return results;
    }

    /**
     * Validate quiz configuration
     */
    public boolean isValidQuizConfig(String category, int numberOfQuestions) {
        if (category.equals("Mixed")) {
            return numberOfQuestions > 0 && numberOfQuestions <= allQuestions.size();
        }
        
        List<Question> categoryQuestions = questionsByCategory.get(category);
        return categoryQuestions != null && numberOfQuestions > 0 && numberOfQuestions <= categoryQuestions.size();
    }

    // Getters
    public int getTotalQuestions() { return allQuestions.size(); }
    public int getCategoryCount() { return questionsByCategory.size(); }
    
    public List<Question> getAllQuestions() { 
        return new ArrayList<>(allQuestions); 
    }
}
