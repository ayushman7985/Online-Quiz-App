import java.util.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * OnlineQuizApp - Main application class
 * Interactive console-based quiz application with scoring and results
 */
public class OnlineQuizApp {
    private QuizManager quizManager;
    private Scanner scanner;
    private List<QuizResult> quizHistory;

    public OnlineQuizApp() {
        this.quizManager = new QuizManager();
        this.scanner = new Scanner(System.in);
        this.quizHistory = new ArrayList<>();
    }

    public static void main(String[] args) {
        OnlineQuizApp app = new OnlineQuizApp();
        app.run();
    }

    /**
     * Main application loop
     */
    public void run() {
        displayWelcome();
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ", 1, 7);
            
            switch (choice) {
                case 1:
                    startQuiz();
                    break;
                case 2:
                    viewQuizHistory();
                    break;
                case 3:
                    displayStatistics();
                    break;
                case 4:
                    searchQuestions();
                    break;
                case 5:
                    practiceMode();
                    break;
                case 6:
                    displayHelp();
                    break;
                case 7:
                    running = false;
                    break;
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        displayGoodbye();
        scanner.close();
    }

    /**
     * Display welcome message
     */
    private void displayWelcome() {
        System.out.println("=".repeat(60));
        System.out.println("           🎯 WELCOME TO ONLINE QUIZ APP 🎯");
        System.out.println("=".repeat(60));
        System.out.println("Test your knowledge across multiple categories!");
        System.out.println("Available categories: " + String.join(", ", quizManager.getAvailableCategories()));
        System.out.println("Total questions available: " + quizManager.getTotalQuestions());
        System.out.println("=".repeat(60));
    }

    /**
     * Display main menu
     */
    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. 🎮 Start New Quiz");
        System.out.println("2. 📊 View Quiz History");
        System.out.println("3. 📈 View Statistics");
        System.out.println("4. 🔍 Search Questions");
        System.out.println("5. 💡 Practice Mode");
        System.out.println("6. ❓ Help");
        System.out.println("7. 🚪 Exit");
        System.out.println("=".repeat(50));
    }

    /**
     * Start a new quiz
     */
    private void startQuiz() {
        System.out.println("\n=== START NEW QUIZ ===");
        
        // Get player name
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) {
            playerName = "Anonymous";
        }
        
        // Select category
        String category = selectCategory();
        if (category == null) return;
        
        // Select number of questions
        int maxQuestions = category.equals("Mixed") ? quizManager.getTotalQuestions() : 
                          quizManager.getQuestionsByCategory(category).size();
        
        System.out.println("Available questions in " + category + ": " + maxQuestions);
        int numberOfQuestions = getIntInput("How many questions do you want? (1-" + maxQuestions + "): ", 1, maxQuestions);
        
        // Create quiz
        List<Question> quizQuestions = category.equals("Mixed") ? 
                                     quizManager.createMixedQuiz(numberOfQuestions) :
                                     quizManager.createQuiz(category, numberOfQuestions);
        
        if (quizQuestions.isEmpty()) {
            System.out.println("No questions available for the selected category!");
            return;
        }
        
        // Start the quiz
        conductQuiz(playerName, category, quizQuestions);
    }

    /**
     * Conduct the actual quiz
     */
    private void conductQuiz(String playerName, String category, List<Question> questions) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎯 QUIZ STARTED - " + category.toUpperCase());
        System.out.println("Player: " + playerName);
        System.out.println("Questions: " + questions.size());
        System.out.println("=".repeat(60));
        
        QuizResult result = new QuizResult(playerName, category);
        LocalDateTime startTime = LocalDateTime.now();
        
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            
            System.out.println("\n" + "-".repeat(50));
            System.out.println("Question " + (i + 1) + " of " + questions.size() + 
                             " (Points: " + question.getPoints() + ")");
            System.out.println("-".repeat(50));
            System.out.println(question.displayQuestion());
            
            // Get user answer
            int maxOptions = question.getOptions().size();
            char selectedChar = getCharInput("Your answer (A-" + (char)('A' + maxOptions - 1) + "): ", 'A', (char)('A' + maxOptions - 1));
            int selectedIndex = selectedChar - 'A';
            String selectedAnswerText = question.getOptions().get(selectedIndex);
            
            // Add result
            result.addQuestionResult(question, selectedIndex, selectedAnswerText);
            
            // Show immediate feedback
            if (question.isCorrect(selectedIndex)) {
                System.out.println("✅ Correct! +" + question.getPoints() + " points");
            } else {
                System.out.println("❌ Wrong! The correct answer was: " + question.getCorrectAnswerText());
            }
        }
        
        // Calculate time taken
        LocalDateTime endTime = LocalDateTime.now();
        long timeTaken = ChronoUnit.SECONDS.between(startTime, endTime);
        result.setTimeTakenSeconds(timeTaken);
        result.setCompletionTime(endTime);
        
        // Add to history
        quizHistory.add(result);
        
        // Display results
        displayQuizResults(result);
    }

    /**
     * Display quiz results
     */
    private void displayQuizResults(QuizResult result) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎉 QUIZ COMPLETED! 🎉");
        System.out.println("=".repeat(60));
        System.out.println("Player: " + result.getPlayerName());
        System.out.println("Category: " + result.getCategory());
        System.out.println("Score: " + result.getTotalScore() + "/" + result.getMaxPossibleScore());
        System.out.println("Percentage: " + String.format("%.1f", result.getPercentageScore()) + "%");
        System.out.println("Grade: " + result.getGrade());
        System.out.println("Correct Answers: " + result.getCorrectAnswers() + "/" + result.getTotalQuestions());
        System.out.println("Time Taken: " + formatTime(result.getTimeTakenSeconds()));
        System.out.println("\n" + result.getPerformanceMessage());
        System.out.println("=".repeat(60));
        
        // Ask if user wants detailed report
        System.out.print("\nWould you like to see a detailed report? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("y") || response.equals("yes")) {
            System.out.println("\n" + result.generateDetailedReport());
        }
    }

    /**
     * Select quiz category
     */
    private String selectCategory() {
        System.out.println("\nAvailable Categories:");
        List<String> categories = new ArrayList<>(quizManager.getAvailableCategories());
        categories.add("Mixed");
        
        for (int i = 0; i < categories.size(); i++) {
            String cat = categories.get(i);
            int questionCount = cat.equals("Mixed") ? quizManager.getTotalQuestions() : 
                              quizManager.getQuestionsByCategory(cat).size();
            System.out.println((i + 1) + ". " + cat + " (" + questionCount + " questions)");
        }
        
        int choice = getIntInput("Select category (1-" + categories.size() + "): ", 1, categories.size());
        return categories.get(choice - 1);
    }

    /**
     * View quiz history
     */
    private void viewQuizHistory() {
        System.out.println("\n=== QUIZ HISTORY ===");
        
        if (quizHistory.isEmpty()) {
            System.out.println("No quiz history available. Take a quiz first!");
            return;
        }
        
        System.out.println(String.format("%-15s %-15s %-10s %-12s %-8s %-20s", 
                          "Player", "Category", "Score", "Percentage", "Grade", "Date"));
        System.out.println("-".repeat(85));
        
        for (QuizResult result : quizHistory) {
            System.out.println(String.format("%-15s %-15s %-10s %-12s %-8s %-20s",
                              result.getPlayerName().length() > 14 ? result.getPlayerName().substring(0, 14) : result.getPlayerName(),
                              result.getCategory(),
                              result.getTotalScore() + "/" + result.getMaxPossibleScore(),
                              String.format("%.1f%%", result.getPercentageScore()),
                              result.getGrade(),
                              result.getCompletionTime().toString().substring(0, 19)));
        }
        
        System.out.println("\nTotal quizzes taken: " + quizHistory.size());
        
        // Calculate average performance
        if (!quizHistory.isEmpty()) {
            double avgPercentage = quizHistory.stream()
                .mapToDouble(QuizResult::getPercentageScore)
                .average()
                .orElse(0.0);
            System.out.println("Average Performance: " + String.format("%.1f%%", avgPercentage));
        }
    }

    /**
     * Display statistics
     */
    private void displayStatistics() {
        System.out.println("\n" + quizManager.getQuizStatistics());
        
        if (!quizHistory.isEmpty()) {
            System.out.println("PERFORMANCE STATISTICS:");
            System.out.println("Total Quizzes Taken: " + quizHistory.size());
            
            // Best performance
            QuizResult bestResult = quizHistory.stream()
                .max(Comparator.comparing(QuizResult::getPercentageScore))
                .orElse(null);
            
            if (bestResult != null) {
                System.out.println("Best Performance: " + String.format("%.1f%%", bestResult.getPercentageScore()) + 
                                 " by " + bestResult.getPlayerName() + " in " + bestResult.getCategory());
            }
            
            // Category performance
            Map<String, List<QuizResult>> resultsByCategory = new HashMap<>();
            for (QuizResult result : quizHistory) {
                resultsByCategory.computeIfAbsent(result.getCategory(), k -> new ArrayList<>()).add(result);
            }
            
            System.out.println("\nPerformance by Category:");
            for (Map.Entry<String, List<QuizResult>> entry : resultsByCategory.entrySet()) {
                double avgScore = entry.getValue().stream()
                    .mapToDouble(QuizResult::getPercentageScore)
                    .average()
                    .orElse(0.0);
                System.out.println("- " + entry.getKey() + ": " + String.format("%.1f%%", avgScore) + 
                                 " (from " + entry.getValue().size() + " attempts)");
            }
        }
    }

    /**
     * Search questions
     */
    private void searchQuestions() {
        System.out.println("\n=== SEARCH QUESTIONS ===");
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine().trim();
        
        if (keyword.isEmpty()) {
            System.out.println("Please enter a valid keyword!");
            return;
        }
        
        List<Question> results = quizManager.searchQuestions(keyword);
        
        if (results.isEmpty()) {
            System.out.println("No questions found matching: " + keyword);
            return;
        }
        
        System.out.println("\nFound " + results.size() + " question(s) matching '" + keyword + "':");
        System.out.println("-".repeat(60));
        
        for (int i = 0; i < results.size(); i++) {
            Question q = results.get(i);
            System.out.println((i + 1) + ". [" + q.getCategory() + "] " + q.getQuestionText());
            System.out.println("   Answer: " + q.getCorrectAnswerText() + " (Points: " + q.getPoints() + ")");
            System.out.println();
        }
    }

    /**
     * Practice mode - show questions with immediate answers
     */
    private void practiceMode() {
        System.out.println("\n=== PRACTICE MODE ===");
        String category = selectCategory();
        if (category == null) return;
        
        List<Question> questions = category.equals("Mixed") ? 
                                 quizManager.createMixedQuiz(5) :
                                 quizManager.createQuiz(category, 5);
        
        if (questions.isEmpty()) {
            System.out.println("No questions available for practice!");
            return;
        }
        
        System.out.println("\nPracticing with " + questions.size() + " questions from " + category);
        System.out.println("Press Enter after each question to see the answer...\n");
        
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("Question " + (i + 1) + ":");
            System.out.println(q.displayQuestion());
            System.out.print("Press Enter to see the answer...");
            scanner.nextLine();
            System.out.println("✅ Correct Answer: " + q.getCorrectAnswerText());
            System.out.println("Points: " + q.getPoints() + "\n");
        }
    }

    /**
     * Display help information
     */
    private void displayHelp() {
        System.out.println("\n=== HELP & INSTRUCTIONS ===");
        System.out.println("🎯 How to use the Online Quiz App:");
        System.out.println();
        System.out.println("1. START NEW QUIZ:");
        System.out.println("   - Enter your name");
        System.out.println("   - Choose a category or Mixed for random questions");
        System.out.println("   - Select number of questions");
        System.out.println("   - Answer each question by selecting A, B, C, or D");
        System.out.println();
        System.out.println("2. SCORING SYSTEM:");
        System.out.println("   - Each question has different point values");
        System.out.println("   - Easy questions: 5 points");
        System.out.println("   - Medium questions: 10 points");
        System.out.println("   - Hard questions: 15+ points");
        System.out.println();
        System.out.println("3. GRADING SCALE:");
        System.out.println("   - A+: 90-100%  - A: 80-89%  - B: 70-79%");
        System.out.println("   - C: 60-69%    - D: 50-59%  - F: Below 50%");
        System.out.println();
        System.out.println("4. FEATURES:");
        System.out.println("   - Quiz History: Track all your attempts");
        System.out.println("   - Statistics: View performance analytics");
        System.out.println("   - Search: Find questions by keyword");
        System.out.println("   - Practice Mode: Study without scoring");
        System.out.println();
        System.out.println("Available Categories: " + String.join(", ", quizManager.getAvailableCategories()));
    }

    /**
     * Display goodbye message
     */
    private void displayGoodbye() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           🎓 THANK YOU FOR USING QUIZ APP! 🎓");
        System.out.println("=".repeat(60));
        System.out.println("Keep learning and improving your knowledge!");
        if (!quizHistory.isEmpty()) {
            QuizResult lastResult = quizHistory.get(quizHistory.size() - 1);
            System.out.println("Your last score: " + String.format("%.1f%%", lastResult.getPercentageScore()) + 
                             " (" + lastResult.getGrade() + ")");
        }
        System.out.println("=".repeat(60));
    }

    /**
     * Get integer input with validation
     */
    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a number between " + min + " and " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    /**
     * Get character input with validation
     */
    private char getCharInput(String prompt, char min, char max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.length() == 1) {
                char ch = input.charAt(0);
                if (ch >= min && ch <= max) {
                    return ch;
                }
            }
            System.out.println("Please enter a letter between " + min + " and " + max + "!");
        }
    }

    /**
     * Format time in minutes and seconds
     */
    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%d:%02d", minutes, remainingSeconds);
    }
}
