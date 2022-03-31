# JWordle

## A Desktop Java Wordle Clone

This application is a game mimicking the popular game *Wordle*. The rules are simple: you have to guess a word with the chosen number of letters. After each guess the program will respond with whether each letter is present in the word, and whether it is in the correct position. Anyone wanting to play more than one Wordle a game will use this.

I was interested in making this project because I was curious how such a simple game managed to captivate so many people, and what could be going on under the hood that made it run.

### User Stories

- As a user, I want to be able to add a guess to my game
- As a user, I want to be able to select to play again
- As a user, I want to be able to change the number of guesses I am allowed
- As a user, I want to view my statistics for all of my games played in the current session
- As a user, I want to be able to save my game to file
- As a user, I want to be able to load my game from file

### Phase 4: Task 2
Sun Mar 27 15:46:25 PDT 2022 \
Guess added. \
Sun Mar 27 15:46:26 PDT 2022 \
Viewed comparison with target word. \
Sun Mar 27 15:46:36 PDT 2022 \
Guess added. \
Sun Mar 27 15:46:37 PDT 2022 \
Viewed comparison with target word. \
Sun Mar 27 15:47:02 PDT 2022 \
Guess added. \
Sun Mar 27 15:47:03 PDT 2022 \
Viewed comparison with target word. \
Sun Mar 27 15:47:44 PDT 2022 \
Guess added. \
Sun Mar 27 15:47:58 PDT 2022 \
Guess added. \
Sun Mar 27 15:47:59 PDT 2022 \
Viewed comparison with target word. \
Sun Mar 27 15:48:04 PDT 2022 \
Guess added. \
Sun Mar 27 15:48:05 PDT 2022 \
Viewed comparison with target word. \
Sun Mar 27 15:48:07 PDT 2022 \
Viewed comparison with target word. \
Sun Mar 27 15:48:11 PDT 2022 \
Guess added.

### Phase 4: Task 3
Looking back at my UML diagram, I can see that the model itself seems to be well-structured, but there is a lot of coupling especially with the UI classes, GameControllerUI and GameMasterUI. The model is extremely simple and there is a lot of functionality that is specific to the ui package, so if I were to refactor the program I would try to put more of the functionality into the model and keep the UI simple and focused solely on user interaction and graphics. This is especially true with GameControllerUI—the ui class has to keep track of the game but also holds the target word, which is why it needs to be saved as a Json object. I would probably refactor this into multiple classes, putting one in the model package with the target word and actual game, and have the UI only be concerned with displaying information. I have the same problem with the GameMaster UI. I could probably implement the Observer pattern, since the GameController needs to be alerted every time a guess is added, and the GameMaster needs to be alerted every time a game begins or ends. The Game and Statistics classes would then be observables.