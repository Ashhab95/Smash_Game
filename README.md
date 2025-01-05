# Smash Game

## Introduction

This project was developed as part of **COMP 250 Winter 2023**. It involves implementing a game where players manipulate a recursively defined game board (quad-tree structure) by applying operations such as rotations, reflections, and smashing. The objective is to achieve a goal (either a "Blob Goal" or a "Perimeter Goal") while maximizing the score.

The game board resembles a Mondrian painting and is randomly generated using colors from a predefined set. Players can interact with the board via a graphical user interface (GUI).

## Objective

- Understand and implement quad-tree structures to model hierarchical data.
- Strengthen recursive programming skills.
- Develop a graphical application using Java Swing.
- Implement scoring logic based on different goals.

## Features

### 1. Game Board Manipulation
Players can perform the following actions on the game board:
- **Rotate**: Rotate a block clockwise or counterclockwise.
- **Reflect**: Reflect a block horizontally or vertically.
- **Smash**: Subdivide a block into four smaller blocks (if allowed).

### 2. Goals
Players are assigned a random goal at the beginning of the game:
- **Blob Goal**: Maximize the size of the largest connected group (blob) of a specific color.
- **Perimeter Goal**: Maximize the number of blocks of a specific color on the outer perimeter.

### 3. Graphical User Interface (GUI)
- The game features a **Java Swing-based GUI** where players can interact with the board.
- Buttons are provided to perform actions like **Rotate**, **Reflect**, **Smash**, and **Undo**.
- The GUI displays the current score, target color, and number of turns left.

### 4. Color Representation
- The game uses four distinct colors: **Red**, **Green**, **Blue**, and **Yellow**.
- Colors are managed using the `GameColors` class.

## Technologies Used

- **Programming Language**: Java
- **GUI Framework**: Java Swing

## How to Run the Application

### Prerequisites

- **Java Development Kit (JDK)**: Ensure that JDK 8 or higher is installed on your system.
- **IDE**: IntelliJ IDEA is recommended, but any IDE that supports Java can be used.

### Steps

1. **Clone the Repository**
2. **Open the Project in IntelliJ IDEA**
    Open the project folder and set the JDK as your project SDK.
    Ensure that the package structure is correctly maintained.
3. **Build and Run** 
   Build the project by selecting Build > Build Project.
   Run the BlockGame.java file to launch the game.

### Gameplay Instructions

1. Starting the Game
    The game begins with a randomly generated board of colored blocks.
    Players are assigned a goal (either Blob Goal or Perimeter Goal).
2. Actions
    Rotate: Rotate a selected block clockwise or counterclockwise.
    Reflect: Reflect a block horizontally or vertically.
    Smash: Subdivide a block into four smaller blocks.
    Undo: Revert the last action performed.
3. Scoring
    The score is calculated based on the assigned goal:
    Blob Goal: The score is determined by the size of the largest connected blob of the target color.
    Perimeter Goal: The score is determined by the number of blocks of the target color on the board’s perimeter.
    Class and Method Descriptions

### Classes
1. **Block.java**:
    Represents a block on the game board.
    Implements methods for rotating, reflecting, and smashing blocks.
    Contains helper methods for drawing the blocks and retrieving selected blocks.
2. **BlockGame.java**:
    Implements the GUI using Java Swing.
    Handles user interactions and game logic.
3. **Goal.java (Abstract Class)**
    Defines the structure for different goals.
    Subclasses include BlobGoal and PerimeterGoal.
4. **BlobGoal.java**:
    Implements scoring logic for the Blob Goal.
    Uses a breadth-first search approach to calculate the size of the largest blob.
5. **PerimeterGoal.java**:
    Implements scoring logic for the Perimeter Goal.
    Counts the number of blocks of the target color on the outer perimeter, with double points for corners.
6. **GameColors.java**:
    Manages the colors used in the game.
7. **BlockToDraw.java**:
    Represents a block to be drawn on the GUI, including its color and position.

### Future Enhancements

  - Add AI opponents to play against.
  - Introduce more complex goals for increased gameplay variety.
  - Add animations for block actions (e.g., rotate, reflect, smash).
  - Improve GUI responsiveness and design.

### Contact
For any inquiries or feedback, please contact Kazi Ashhab Rahman at kazi.a.rahman@mail.mcgill.ca.

### License
This project is licensed under the MIT License.

