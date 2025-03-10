# Spaceflight Simulator - Tomasz Kaczmarek 155903 L9

# Description
<p>The game allows you to build your own rocket and then test it in flight. To build a rocket, click on the item on the left side of the screen and drag and drop it onto the grid in the center of the screen. When you feel your rocket is ready to launch, press the TEST button. To make your rocket leave the ground, press the up arrow. In the lower left corner you see the amount of fuel in the rocket, and on the right side of the screen you see the altitude indicator above the ground. To return to the building screen, press the mouse anywhere on the screen. If you want to remove an element from the racket, press it and drop it outside the grid.</p>

# Implementation
</header>

In <b>Main</b> class, are initialized two main game views represented by classes <b>ConstructorView</b> and <b>PlaygroundView</b>. Both this classes contains game loops which are started right in the begining inside Main class. Game loop contains two functions update() and repaint(). update() is responsible for our logic in game and repaint() is responsible for graphics. In my project only in PlaygroundView class update() is realy used because most of my game logic is executed in MouseEvents.

<b>RocketElement</b> class</br>
Class contains getters and setters (data encapsulation):</br>
- setX()
- getX()
- setY()
- getY()
- getHeight()
- getWidth()

<b>Engine</b> class and <b>FuelTank</b> class</br>
Classes inherits from RocketElement class and overrides some of the methods.
Inherited methods are mentioned earlier (setter and getters) and overrided methods are:
- draw()
- SetOn()
- SetOff()
- cloneObj()
- drawGui()

<b>Rocket</b> class</br>
Stores RocketElement objects and its responsible for drawing them on screen. Objects are stored in an array, which can be accessed using the following methods:</br>
- AddElement()
- RemoveElement()
- GetElement() - returns pointer
