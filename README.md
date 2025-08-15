# Online Quiz App - Mini Project

A comprehensive Java console-based quiz application with multiple categories, scoring system, and performance tracking.

## Features

### Core Functionality
- **Multiple Choice Questions**: Support for 2-4 options per question
- **Category-based Quizzes**: Java Programming, General Knowledge, Science, Mathematics
- **Mixed Quizzes**: Random questions from all categories
- **Scoring System**: Points-based scoring with different difficulty levels
- **Performance Tracking**: Grade calculation and performance analytics
- **Quiz History**: Track all quiz attempts with detailed statistics

### Advanced Features
- **Practice Mode**: Study questions without scoring pressure
- **Search Functionality**: Find questions by keywords
- **Detailed Reports**: Question-by-question breakdown
- **Time Tracking**: Monitor quiz completion time
- **Performance Analytics**: Category-wise performance analysis
- **Input Validation**: Robust error handling and user input validation

## Project Structure

```
Online Quiz App/
├── Question.java           # Question entity with validation
├── QuizResult.java        # Result tracking and reporting
├── QuizManager.java       # Question management and quiz creation
├── OnlineQuizApp.java     # Main application with console interface
└── OnlineQuizApp_README.md # This documentation
```

## Class Overview

### Question.java
- Represents individual quiz questions
- Supports multiple choice format (A, B, C, D)
- Includes category, points, and validation
- Methods for answer checking and display formatting

### QuizResult.java
- Tracks quiz performance and results
- Calculates scores, percentages, and grades
- Generates detailed performance reports
- Stores individual question results

### QuizManager.java
- Manages question database and categories
- Creates quizzes based on category and difficulty
- Provides search and filtering capabilities
- Loads default question set across multiple subjects

### OnlineQuizApp.java
- Main application with interactive console interface
- Menu-driven navigation system
- Quiz conductor and result display
- History tracking and statistics

## Question Categories

### 1. Java Programming (5 questions)
- Variable declaration and syntax
- Class creation and main method
- Data types and JVM concepts
- **Difficulty**: Medium to Hard (10-15 points)

### 2. General Knowledge (5 questions)
- Geography, history, and culture
- Famous personalities and landmarks
- World events and capitals
- **Difficulty**: Easy to Medium (5-10 points)

### 3. Science (5 questions)
- Chemistry, physics, and biology
- Scientific facts and discoveries
- Natural phenomena and human body
- **Difficulty**: Medium to Hard (10-15 points)

### 4. Mathematics (5 questions)
- Basic arithmetic and geometry
- Mathematical constants and formulas
- Problem-solving and calculations
- **Difficulty**: Easy to Hard (5-15 points)

## How to Run

### Compilation and Execution
```bash
# Compile all Java files
javac *.java

# Run the application
java OnlineQuizApp
```

### System Requirements
- Java 8 or higher
- Console/Terminal access
- No external dependencies required

## Usage Guide

### Main Menu Options
1. **🎮 Start New Quiz**
   - Enter player name
   - Select category or Mixed mode
   - Choose number of questions
   - Take the quiz with immediate feedback

2. **📊 View Quiz History**
   - See all previous quiz attempts
   - Performance summary with grades
   - Average performance calculation

3. **📈 View Statistics**
   - Question database statistics
   - Category-wise performance analysis
   - Best performance tracking

4. **🔍 Search Questions**
   - Find questions by keyword
   - Browse questions by topic
   - Preview questions and answers

5. **💡 Practice Mode**
   - Study mode without scoring
   - Immediate answer revelation
   - Perfect for learning and review

6. **❓ Help**
   - Complete usage instructions
   - Scoring system explanation
   - Available categories overview

### Scoring System
- **Points per Question**: 5-15 points based on difficulty
- **Grade Scale**:
  - A+: 90-100% (Excellent)
  - A: 80-89% (Great)
  - B: 70-79% (Good)
  - C: 60-69% (Fair)
  - D: 50-59% (Below Average)
  - F: Below 50% (Poor)

### Sample Quiz Flow
```
1. Enter name: "John Doe"
2. Select category: "Java Programming"
3. Choose questions: 5
4. Answer each question (A/B/C/D)
5. View immediate feedback
6. See final results and grade
7. Optional detailed report
```

## Sample Questions

### Java Programming
- "What is the correct way to declare a variable in Java?"
- "Which keyword is used to create a class in Java?"
- "What is the main method signature in Java?"

### General Knowledge
- "What is the capital of France?"
- "Which planet is known as the Red Planet?"
- "Who painted the Mona Lisa?"

### Science
- "What is the chemical symbol for gold?"
- "How many bones are there in an adult human body?"
- "Which gas makes up about 78% of Earth's atmosphere?"

### Mathematics
- "What is the value of π (pi) approximately?"
- "What is 15% of 200?"
- "What is the square root of 144?"

## Key Features Demonstrated

### Object-Oriented Programming
- **Encapsulation**: Private fields with public methods
- **Data Validation**: Input validation and error handling
- **Collections**: Lists, Maps, and Sets for data management
- **Exception Handling**: Try-catch blocks for robust operation

### User Experience
- **Interactive Interface**: Menu-driven console application
- **Immediate Feedback**: Show correct answers after each question
- **Progress Tracking**: Question counter and time tracking
- **Performance Analytics**: Detailed statistics and history

### Data Management
- **Question Database**: In-memory storage with categorization
- **Result Tracking**: Comprehensive quiz result storage
- **Search Functionality**: Keyword-based question search
- **Statistics**: Performance analysis and reporting

## Extensibility

The application can be extended with:
- **GUI Interface**: Swing or JavaFX implementation
- **Database Integration**: MySQL/PostgreSQL for persistent storage
- **User Authentication**: Login system with user profiles
- **Online Multiplayer**: Network-based multiplayer quizzes
- **Question Import**: Load questions from external files
- **Timer Functionality**: Time-limited questions
- **Difficulty Levels**: Adaptive difficulty based on performance
- **Leaderboards**: Global and category-wise rankings

## Learning Objectives

### Core Java Concepts
- Object-oriented programming principles
- Collections framework (List, Map, Set)
- Exception handling and input validation
- String manipulation and formatting
- Date/time handling with LocalDateTime

### Application Design
- Menu-driven console applications
- Data modeling and entity relationships
- Result processing and analytics
- User interface design patterns
- Code organization and modularity

## Testing Scenarios

1. **Basic Quiz Flow**: Complete a full quiz in each category
2. **Input Validation**: Test invalid inputs and edge cases
3. **Mixed Quiz**: Take quizzes with random questions
4. **Practice Mode**: Use study mode for learning
5. **Search Function**: Search for specific topics
6. **History Tracking**: Verify result storage and display
7. **Statistics**: Check performance calculations
8. **Edge Cases**: Empty inputs, boundary values

## Performance Considerations

- **Memory Usage**: Efficient question storage and retrieval
- **Response Time**: Fast quiz loading and result calculation
- **User Experience**: Smooth navigation and clear feedback
- **Data Integrity**: Proper validation and error handling

---

**Author**: Java Mini Project  
**Version**: 1.0  
**Last Updated**: August 2025

This Online Quiz App demonstrates comprehensive Java programming skills including OOP principles, collections framework, user interface design, and application architecture. Perfect for learning and assessment purposes!
