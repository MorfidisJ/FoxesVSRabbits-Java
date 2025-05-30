# Animal Population Simulator

A Java-based simulation of a predator-prey ecosystem featuring foxes and rabbits. The simulation models the interactions between these animals in a grid-based environment, including movement, breeding, and population dynamics.

## Project Structure

```
├── Animal.java           # Abstract base class for all animals
├── Fox.java             # Fox implementation (predator)
├── Rabbit.java          # Rabbit implementation (prey)
├── Grid.java            # Grid management and cell organization
├── Cell.java            # Individual cell in the grid
├── AnimalSimulator.java # Main simulation logic
├── Simulation.java      # Simulation runner
└── SimulationGUI.java   # Graphical user interface
```

## Setup and Running

1. Ensure you have Java JDK installed on your system
2. Compile all Java files:
   ```bash
   javac *.java
   ```
3. Run the simulation:
   - For console mode:
     ```bash
     java Simulation
     ```
   - For GUI mode:
     ```bash
     java Simulation --gui
     ```

### GUI Features
- Visual representation of the grid with animal shapes:
  - Empty cells are shown in white with gray borders
  - Rabbits are shown as dark grey animal shapes
  - Foxes are shown as orange animal shapes
- Real-time population statistics display
- Interactive controls:
  - Step button: Advance simulation one step at a time
  - Auto Run button: Run simulation automatically
  - Population counters for foxes and rabbits
- Scrollable grid view for larger simulations
- Smooth animation with anti-aliased graphics

### GUI Technical Details

#### GUI Flowcharts

##### 1. Initialization and Setup Flow
```mermaid
flowchart TD
    Start([Start GUI]) --> CreateFrame[Create Main JFrame]
    CreateFrame --> SetupLayout[Setup BorderLayout]
    
    subgraph Control Panel Setup
        SetupLayout --> CreateControlPanel[Create Control Panel]
        CreateControlPanel --> AddStepButton[Add Step Button]
        AddStepButton --> AddAutoButton[Add Auto Run Button]
        AddAutoButton --> AddFoxLabel[Add Fox Counter]
        AddFoxLabel --> AddRabbitLabel[Add Rabbit Counter]
    end
    
    subgraph Grid Setup
        SetupLayout --> CreateGridPanel[Create Grid Panel]
        CreateGridPanel --> SetupGridLayout[Setup GridLayout]
        SetupGridLayout --> CreateCells[Create Cell Panels]
        CreateCells --> AddScrollPane[Add ScrollPane]
    end
    
    subgraph Timer Setup
        SetupLayout --> CreateTimer[Create Swing Timer]
        CreateTimer --> SetInterval[Set 500ms Interval]
        SetInterval --> SetupTimerAction[Setup Timer Action]
    end
    
    AddRabbitLabel --> FinalSetup[Final Setup]
    AddScrollPane --> FinalSetup
    SetupTimerAction --> FinalSetup
    
    FinalSetup --> PackFrame[Pack Frame]
    PackFrame --> CenterWindow[Center Window]
    CenterWindow --> ShowGUI[Show GUI]
    ShowGUI --> InitialUpdate[Initial Display Update]
    InitialUpdate --> End([GUI Ready])
```

##### 2. Update Process Flow
```mermaid
flowchart TD
    Start([Update Trigger]) --> TriggerType{Update Type?}
    
    TriggerType -->|Step Button| ManualStep[Manual Step]
    TriggerType -->|Timer Tick| AutoStep[Auto Step]
    
    subgraph Step Execution
        ManualStep --> ExecuteStep[Execute Simulation Step]
        AutoStep --> ExecuteStep
        ExecuteStep --> UpdateCounters[Update Population Counters]
        UpdateCounters --> RefreshGrid[Refresh Grid Display]
    end
    
    subgraph Grid Refresh Process
        RefreshGrid --> IterateCells[Iterate Through Cells]
        IterateCells --> CheckCell{Cell State?}
        
        CheckCell -->|Empty| SetEmpty[Set White Background]
        CheckCell -->|Rabbit| DrawRabbit[Draw Rabbit Shape]
        CheckCell -->|Fox| DrawFox[Draw Fox Shape]
        
        SetEmpty --> NextCell{More Cells?}
        DrawRabbit --> NextCell
        DrawFox --> NextCell
        
        NextCell -->|Yes| IterateCells
        NextCell -->|No| RepaintGrid[Repaint Grid]
    end
    
    RepaintGrid --> UpdateComplete([Update Complete])
```

##### 3. Event Handling Flow
```mermaid
flowchart TD
    Start([Event Trigger]) --> EventType{Event Type?}
    
    EventType -->|Step Button Click| StepClick[Step Button Clicked]
    EventType -->|Auto Button Click| AutoClick[Auto Button Clicked]
    EventType -->|Window Close| CloseClick[Close Button Clicked]
    EventType -->|Window Resize| ResizeEvent[Window Resized]
    
    subgraph Step Button Handler
        StepClick --> ExecuteStep[Execute Single Step]
        ExecuteStep --> UpdateDisplay[Update Display]
    end
    
    subgraph Auto Button Handler
        AutoClick --> CheckState{Auto Running?}
        CheckState -->|Yes| StopTimer[Stop Timer]
        CheckState -->|No| StartTimer[Start Timer]
        StopTimer --> UpdateButtonText[Update Button Text]
        StartTimer --> UpdateButtonText
        UpdateButtonText --> ToggleState[Toggle Auto State]
    end
    
    subgraph Window Event Handlers
        CloseClick --> Cleanup[Cleanup Resources]
        Cleanup --> ExitApp[Exit Application]
        
        ResizeEvent --> CheckScroll[Check Scroll Needs]
        CheckScroll --> UpdateScroll[Update Scrollbars]
        UpdateScroll --> MaintainProportions[Maintain Grid Proportions]
    end
    
    UpdateDisplay --> EventComplete([Event Handled])
    ToggleState --> EventComplete
    ExitApp --> EventComplete
    MaintainProportions --> EventComplete
```

##### 4. Rendering Flow
```mermaid
flowchart TD
    Start([Render Request]) --> GetCellState[Get Cell State]
    
    GetCellState --> StateType{Cell Type?}
    
    subgraph Empty Cell
        StateType -->|Empty| DrawEmpty[Draw Empty Cell]
        DrawEmpty --> SetWhite[Set White Background]
        SetWhite --> DrawBorder[Draw Gray Border]
    end
    
    subgraph Rabbit Cell
        StateType -->|Rabbit| DrawRabbit[Draw Rabbit]
        DrawRabbit --> SetColor[Set Dark Grey Color]
        SetColor --> DrawBody[Draw Rabbit Body]
        DrawBody --> DrawHead[Draw Rabbit Head]
        DrawHead --> DrawEars[Draw Rabbit Ears]
    end
    
    subgraph Fox Cell
        StateType -->|Fox| DrawFox[Draw Fox]
        DrawFox --> SetFoxColor[Set Orange Color]
        SetFoxColor --> DrawFoxBody[Draw Fox Body]
        DrawFoxBody --> DrawFoxHead[Draw Fox Head]
        DrawFoxHead --> DrawFoxEars[Draw Pointed Ears]
        DrawFoxEars --> DrawTail[Draw Fox Tail]
    end
    
    DrawBorder --> RenderComplete([Render Complete])
    DrawEars --> RenderComplete
    DrawTail --> RenderComplete
```

## Detailed System Architecture

### Complete Class Hierarchy
```mermaid
classDiagram
    %% Main Classes
    AnimalSimulator --> Grid
    AnimalSimulator --> Animal
    Grid --> Cell
    Cell --> Animal
    
    %% Animal Hierarchy
    Animal <|-- Fox : extends
    Animal <|-- Rabbit : extends
    
    %% Animal Class Details
    class Animal {
        <<abstract>>
        -Cell cell
        -int timeStep
        +Animal(Cell cell)
        +abstract boolean isRabbit()
        +abstract boolean breedingTime(int timeStep)
        +abstract Animal giveBirth()
        +Cell getCell()
        +void setCell(Cell cell)
        +void die()
        +void incrementTimeStep()
        +int getTimeStep()
        +Animal move()
        +Animal breed()
    }
    
    %% Fox Class Details
    class Fox {
        -int stepsWithoutFood
        -static final int BREEDING_AGE = 8
        -static final int STARVATION_THRESHOLD = 3
        +Fox(Cell cell)
        +boolean isRabbit()
        +boolean breedingTime(int timeStep)
        +Animal giveBirth()
        +Animal move()
    }
    
    %% Rabbit Class Details
    class Rabbit {
        -static final int BREEDING_AGE = 3
        -static final int MAX_AGE = 40
        +Rabbit(Cell cell)
        +boolean isRabbit()
        +boolean breedingTime(int timeStep)
        +Animal giveBirth()
    }
    
    %% Grid Class Details
    class Grid {
        -static final int SIZE = 20
        -Cell[][] cells
        -Random random
        +Grid()
        +void addAnimal(Animal animal)
        +String toString()
    }
    
    %% Cell Class Details
    class Cell {
        -ArrayList~Cell~ neighbors
        -Animal animal
        +Cell()
        +void addNeighbor(Cell cell)
        +Cell getRandomEmptyNeighbor()
        +Cell getRandomRabbitNeighbor()
        +Animal getAnimal()
        +void setAnimal(Animal animal)
        +void removeAnimal()
        +boolean isEmpty()
        +boolean containsRabbit()
        +String toString()
    }
```

### Detailed Grid Structure and Relationships
```mermaid
classDiagram
    %% Grid System
    Grid "1" *-- "n×m" Cell : contains
    Cell "1" -- "0..1" Animal : holds
    
    %% Grid Details
    class Grid {
        -Cell[][] cells
        -int width
        -int height
        +Grid(int width, int height)
        +void addAnimal(Animal animal)
        +Cell getRandomEmptyNeighbor(Cell cell)
        +Cell getCell(int x, int y)
        +int getWidth()
        +int getHeight()
        +boolean isValidPosition(int x, int y)
    }
    
    %% Cell Details
    class Cell {
        -Animal animal
        -int x
        -int y
        -Grid grid
        +Cell(Grid grid, int x, int y)
        +void setAnimal(Animal animal)
        +void removeAnimal()
        +Animal getAnimal()
        +Cell getRandomEmptyNeighbor()
        +boolean isEmpty()
        +int getX()
        +int getY()
        +List~Cell~ getNeighbors()
    }
    
    %% Animal Details
    class Animal {
        -Cell cell
        -int timeStep
        +void move()
        +void breed()
        +void die()
    }
```

## Detailed Simulation Logic

### Complete Simulation Flow
```mermaid
flowchart TD
    %% Main Simulation Flow
    Start([Start Simulation]) --> Init[Initialize Grid]
    Init --> Config[Configure Parameters]
    Config --> Populate[Populate with Animals]
    
    %% Time Step Loop
    Populate --> TimeStep[Time Step Loop]
    TimeStep --> Move[Move Animals]
    Move --> Breed[Handle Breeding]
    Breed --> Update[Update Populations]
    Update --> Display[Display Statistics]
    
    %% Decision Points
    Display --> Continue{Continue?}
    Continue -->|Yes| TimeStep
    Continue -->|No| End([End Simulation])
    
    %% Subprocesses
    subgraph "Grid Initialization"
        Init --> CreateGrid[Create Grid]
        CreateGrid --> SetSize[Set Grid Size]
        SetSize --> InitCells[Initialize Cells]
    end
    
    subgraph "Population Initialization"
        Populate --> AddFoxes[Add Foxes]
        AddFoxes --> AddRabbits[Add Rabbits]
        AddRabbits --> Verify[Verify Initial State]
    end
    
    subgraph "Time Step Processing"
        Move --> Randomize[Randomize Animal Order]
        Randomize --> ProcessMove[Process Each Animal]
        ProcessMove --> UpdateGrid[Update Grid State]
    end
    
    subgraph "Breeding Process"
        Breed --> CheckAge[Check Breeding Age]
        CheckAge --> FindSpace[Find Empty Space]
        FindSpace --> CreateBaby[Create New Animal]
    end
```

### Detailed Animal Behavior Flow
```mermaid
flowchart TD
    %% Animal Turn Start
    Start([Animal Turn]) --> HasCell{Has Cell?}
    
    %% Movement Logic
    HasCell -->|No| EndTurn([End Turn])
    HasCell -->|Yes| CheckAge{Check Age}
    CheckAge -->|Too Old| Die[Die]
    CheckAge -->|Valid Age| FindNeighbor[Find Empty Neighbor]
    
    %% Movement Decision
    FindNeighbor --> FoundCell{Found Empty Cell?}
    FoundCell -->|No| Stay[Stay in Place]
    FoundCell -->|Yes| Move[Move to New Cell]
    
    %% Breeding Logic
    Move --> CheckBreed{Can Breed?}
    CheckBreed -->|No| EndTurn
    CheckBreed -->|Yes| FindBreedSpace[Find Breeding Space]
    
    %% Breeding Decision
    FindBreedSpace --> HasSpace{Has Empty Space?}
    HasSpace -->|No| EndTurn
    HasSpace -->|Yes| CreateBaby[Create Baby]
    
    %% Final States
    Die --> UpdateGrid[Update Grid]
    Stay --> CheckBreed
    CreateBaby --> UpdateGrid
    UpdateGrid --> EndTurn
    
    %% Subprocesses
    subgraph "Movement Process"
        FindNeighbor --> CheckAll[Check All Directions]
        CheckAll --> RandomSelect[Random Selection]
        RandomSelect --> Validate[Validate Move]
    end
    
    subgraph "Breeding Process"
        CheckBreed --> VerifyAge[Verify Breeding Age]
        VerifyAge --> CheckSpace[Check Available Space]
        CheckSpace --> CreateNew[Create New Animal]
    end
    
    subgraph "Grid Updates"
        UpdateGrid --> RemoveOld[Remove Old Position]
        RemoveOld --> SetNew[Set New Position]
        SetNew --> UpdateStats[Update Statistics]
    end
```

## Detailed System Components

### 1. Grid-Based Environment
- **Grid Structure**
  - Two-dimensional array of cells
  - Each cell can hold exactly one animal
  - Grid maintains boundaries and valid positions
  - Cells track their position and neighbors

- **Cell Management**
  - Cells maintain references to their grid
  - Each cell knows its x,y coordinates
  - Cells can identify empty neighboring cells
  - Cells handle animal placement and removal

### 2. Animal Behavior System
- **Fox Behavior (Predator)**
  - Breeding age: 8 time steps
  - Starvation threshold: 3 steps without food
  - Movement: Prioritizes moving to cells with rabbits
  - If no rabbit nearby: Moves randomly to empty cell
  - Dies if no food for 3 consecutive steps
  - Breeding: Creates new fox in empty neighbor cell

- **Rabbit Behavior (Prey)**
  - Breeding age: 3 time steps
  - Movement: Random to empty neighboring cells
  - Breeding: Creates new rabbit in empty neighbor cell
  - No maximum age limit (removed incorrect MAX_AGE reference)

### 3. Population Management
- **Tracking System**
  - Maintains separate counts for foxes and rabbits
  - Updates counts after each time step
  - Handles animal creation and removal
  - Provides population statistics

- **Simulation Control**
  - Manages time step progression
  - Controls animal movement order
  - Handles breeding conditions
  - Updates grid state

### 4. Grid System
- **Grid Structure**
  - Fixed size: 20x20 cells
  - Cells maintain neighbor relationships
  - Grid is bounded (not toroidal)
  - Random placement of new animals
  - Visual representation:
    - "_" for empty cells
    - "o" for rabbits
    - "X" for foxes

### 5. Cell Management
- **Cell Properties**
  - Maintains list of neighboring cells
  - Can hold one animal or be empty
  - Provides methods for:
    - Finding empty neighbors
    - Finding rabbit neighbors (for foxes)
    - Animal placement and removal
  - Bidirectional neighbor relationships

## Simulation Rules and Parameters

### 1. Time Step Operations
- **Movement Phase**
  - Foxes move first, prioritizing rabbit neighbors
  - If no rabbit nearby, foxes move randomly
  - Rabbits move randomly to empty neighbors
  - Movement is restricted to grid boundaries
  - Animals cannot share cells

- **Breeding Phase**
  - Foxes breed every 8 time steps
  - Rabbits breed every 3 time steps
  - Breeding requires empty neighboring cell
  - New animals inherit parent's type
  - Breeding resets parent's breeding timer

### 2. Environmental Rules
- **Grid Constraints**
  - Fixed 20x20 grid size
  - Bounded grid (not toroidal)
  - Cells maintain neighbor relationships
  - Grid enforces movement boundaries

- **Population Limits**
  - Foxes die after 3 steps without food
  - Breeding limited by available space
  - Population density affects breeding success
  - Grid size (400 cells) limits total population

### 3. Statistical Tracking
- **Population Metrics**
  - Current fox and rabbit counts
  - Breeding success rates
  - Population growth trends
  - Survival rates by species

## Author

John Morfidis 