# KjelliGameEngine
So you want to make a game?
## Introduction

Do you have a crazy idea for a game, or a remake of one? Tired of writing hours of code just to make something appear on the screen?

<b>This game-engine lets you focus on writing only the logic that makes your game</b> fast and easy, yet flexible. 
KjelliGameEngine extends lwjgl in functionality and lets you draw textures, handle collisions, and implement your own gamelogic
with less boiler-plate code.

## Getting started with Eclipse
This step-by-step guide will provide you with a functional skeleton that you can use to make your game.
This will absolutely work in other IDEs, though this step-by-step guide will only cover Eclipse.
### 1. Start a new project from scratch
  Start a fresh project in eclipse.
### 2. Download the library
  Download the library from this repository and put it in your projects library folder.
### 3. Download lwjgl and the other dependencies
  Download lwjgl and every other .jar file in the same folder as the kjelli-game-engine.jar and put them in your projects library folder.
### 4. Download the natives
  Download the natives for your target OS and link them to your lwjgl.jar
### 5. Create your class
Everything should now be good to go. If by any chance there are some issues along the way, or this guide is unfulfilled, please let me know.
Start off by implementing the Game interface
```java
import no.kjelli.generic.Game;

public class YourClass implements Game {...};
  
```

### 6. Customize variables
Some variables <b>must</b> be set before you can run your skeleton game.

```java
  // Code above omitted ...
  
	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 480;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Hello KGE";
	}

	@Override
	public double getWidth() {
		return 640;
	}
	// Code below omitted ...
```
### 7. Run application
Run your application and you will notice that you have an empty black screen ready for whatever you want to put in it!

NOTE: When closing your application (as of commit 5f6aee8bcc35df7d971cf93e5375c394d509eb20)
you will get an exception claiming the resource <i>res\fontsmall.png</i> was not found. This is a minor issue that will
be fixed in a later commit.

### 8. ??????
Create your game using the given library, your own imagination and time.

### 9. Profit
Export the game and publish it (!TODO guide for that). If you want to post your game as an example game for this engine, I would love getting a pull-request! :smile:

## Examples:

### Pong
Pong game combined with a shooting feature. Makes for an even more competitive bat-and-ball game!


![Example game: Pong](http://i.imgur.com/AydO4Xb.png "Example game: Pong")
![Example game: Pong](http://i.imgur.com/0GWb6Pa.png "Example game: Pong")



### Bomberman
Classic bomberman remake with powerups, levels and also a LAN/Internet feature. Play with your friends and bombs away!


![Example game: Bomberman](http://i.imgur.com/jXQBCOR.png "Example game: Bomberman")
![Example game: Bomberman](http://i.imgur.com/GJHmhob.png "Example game: Bomberman")

### MathMania
Eight-directional game where you traverse the level by solving simple arithmetic problems. The faster you solve them, and the more you solve in a row increases the score you get!

![Example game: MathMania](http://i.imgur.com/BOBqcf4.png "Example game: MathMania")



## DISCLAIMER
The content in this repository is severely unfinished. The game-engine is only meant for two-dimensional games.
This project is nowhere near finished and may or may not contain minor and/or major issues.

