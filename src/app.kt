import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text
import javafx.stage.Stage
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.Timer
import kotlin.random.Random


fun main() {
    val x = Example()
    x.main()
}

class Example : Application(), ActionListener {
    override fun actionPerformed(p0: ActionEvent?) {
        checkWin()
        moveSnake()
        gameoverCheck()
        appleCheck()
        addToSnake()

    }

    private val group = Group()
    private val boardWidth = 800.0
    private val boardHeight = 600.0
    private val boardHeightwidthextra = 700.0
    private var currentPoint = 0
    private var actAppleX = 0.0
    private var actAppleY = 0.0
    private var nextSnakeItemX = 0.0
    private var nextSnakeItemY = 0.0
    private var addAble = false
    private val delay = 50
    private lateinit var text: Text
    private var lineList = mutableListOf<javafx.scene.shape.Line>()
    private var snakeHigh = 10
    private var snakeWith = 10
    private lateinit var rec: Rectangle
    private var apple: Rectangle = Rectangle(snakeHigh.toDouble(), snakeWith.toDouble())
    private var snakeList = mutableListOf<Rectangle>()
    private var leftDirection = false
    private var rightDirection = true
    private var upDirection = false
    private var downDirection = false
    private lateinit var timer: Timer

    private fun gameoverCheck() {
        for (x in 0..currentPoint) {
            if (rec.layoutX == snakeList[x].layoutX && rec.layoutY == snakeList[x].layoutY) {
                timer.stop()
                text.text="GAME OVER press 'r' for restart"
            }
        }
    }


    private fun checkWin() {
        if (currentPoint==1000){
            timer.stop()
            text.text="You won press 'r' for restart"
        }
    }

    private fun addToSnake() {
        if (addAble) {
            snakeList[currentPoint].fill = Color.GREY
            snakeList[currentPoint].layoutX = nextSnakeItemX
            snakeList[currentPoint].layoutY = nextSnakeItemY
            currentPoint++
            text.text = currentPoint.toString()
            addAble = false
        }
    }

    private fun appleCheck() {
        if (apple.layoutX == rec.layoutX && apple.layoutY == rec.layoutY) {
            nextSnakeItemX = snakeList[currentPoint].layoutX
            nextSnakeItemY = snakeList[currentPoint].layoutY
            addAble = true
            createApple()
        }
    }

    private fun createApple() {
        var good=false
        while(!good){
            actAppleX = Random.nextInt(0, boardWidth.toInt() / 10).toDouble()
            actAppleY = Random.nextInt(0, boardHeight.toInt() / 10).toDouble()
            actAppleX *= 10.0
            actAppleY *= 10.0
            good=true
               if(actAppleX == rec.layoutX && actAppleY == rec.layoutY)
                   good=false
               for (x in 0..currentPoint) {
                   if (actAppleX == snakeList[x].layoutX && actAppleY == snakeList[x].layoutY) {
                       good=false
                   }
               }
        }
        apple.layoutX = (actAppleX)
        apple.layoutY = (actAppleY)
        apple.fill = Color.RED
    }

    private fun moveSnake() {
        if (leftDirection) {
            if (currentPoint != 0 && currentPoint != 1) {
                for (i in 0 until currentPoint) {
                    snakeList[currentPoint - i].layoutX = snakeList[currentPoint - i - 1].layoutX
                    snakeList[currentPoint - i].layoutY = snakeList[currentPoint - i - 1].layoutY
                }
            }
            if (currentPoint > 0) {
                snakeList[0].layoutX = rec.layoutX
                snakeList[0].layoutY = rec.layoutY
            }
            rec.layoutX -= 10.0
        }

        if (rightDirection) {
            if (currentPoint != 0 && currentPoint != 1) {
                for (i in 0 until currentPoint) {
                    snakeList[currentPoint - i].layoutX = snakeList[currentPoint - i - 1].layoutX
                    snakeList[currentPoint - i].layoutY = snakeList[currentPoint - i - 1].layoutY
                }
            }
            if (currentPoint > 0) {
                snakeList[0].layoutX = rec.layoutX
                snakeList[0].layoutY = rec.layoutY
            }
            rec.layoutX += 10.0
        }

        if (upDirection) {
            if (currentPoint != 0 && currentPoint != 1) {
                for (i in 0 until currentPoint) {
                    snakeList[currentPoint - i].layoutX = snakeList[currentPoint - i - 1].layoutX
                    snakeList[currentPoint - i].layoutY = snakeList[currentPoint - i - 1].layoutY
                }
            }
            if (currentPoint > 0) {
                snakeList[0].layoutX = rec.layoutX
                snakeList[0].layoutY = rec.layoutY
            }
            rec.layoutY -= 10.0
        }

        if (downDirection) {
            if (currentPoint != 0 && currentPoint != 1) {
                for (i in 0 until currentPoint) {
                    snakeList[currentPoint - i].layoutX = snakeList[currentPoint - i - 1].layoutX
                    snakeList[currentPoint - i].layoutY = snakeList[currentPoint - i - 1].layoutY
                }
            }
            if (currentPoint > 0) {
                snakeList[0].layoutX = rec.layoutX
                snakeList[0].layoutY = rec.layoutY
            }
            rec.layoutY += 10.0
        }

        if (rec.layoutX == boardWidth)
            rec.layoutX = 0.0
        if (rec.layoutX == -10.0)
            rec.layoutX = boardWidth
        if (rec.layoutY == -10.0)
            rec.layoutY = boardHeight - 10
        if (rec.layoutY == boardHeight)
            rec.layoutY = 10.0
    }

    override fun start(p0: Stage?) {
        rec = initRecTangle()
        createSnakeLList()
        createApple()
        initLines()
        initScore()
        val scene=initKeyListener()
        group.children.add(apple)
        if (p0 != null) {
            p0.title = "Snake!"
            p0.isResizable = false
        }

        group.children.add(rec)
        lineList.forEach {
            group.children.add(it)
        }
        // create a scene
        p0?.scene = scene
        p0?.show()
        timer = Timer(delay, this)
        timer.start()
    }

    private fun initKeyListener() : Scene{
        val scene = Scene(group, boardWidth, boardHeightwidthextra)
        scene.setOnKeyPressed { keyEvent ->
            val key = keyEvent.code.code

            if (key == 37 && !rightDirection) {
                leftDirection = true
                upDirection = false
                downDirection = false
            }

            if (key == 39 && !leftDirection) {
                rightDirection = true
                upDirection = false
                downDirection = false
            }

            if (key == 38 && !downDirection) {
                upDirection = true
                rightDirection = false
                leftDirection = false
            }

            if (key == 40 && !upDirection) {
                downDirection = true
                rightDirection = false
                leftDirection = false
            }
            if (key == 82)
                restart()
        }
        return scene
    }

    private fun initScore() {
        text = Text("0")
        text.layoutX = 400.0
        text.layoutY = boardHeight + 30.0
        group.children.add(text)
    }

    private fun createSnakeLList() {
        for (x in 0..1000) {
            val r = Rectangle(snakeWith.toDouble(), snakeHigh.toDouble())
            r.layoutX = -100.0
            r.layoutY = -100.0
            r.fill = Color.WHITE
            snakeList.add(r)
            group.children.add(r)
        }
    }

    private fun restart() {
        for (x in 0..currentPoint)
            snakeList[x].fill = Color.WHITE
        snakeList.clear()
        currentPoint = 0
        text.text = currentPoint.toString()
        for (x in 0..1000) {
            val r = Rectangle(snakeWith.toDouble(), snakeHigh.toDouble())
            r.layoutX = 0.0
            r.layoutY = 0.0
            r.fill = Color.WHITE
            snakeList.add(r)
            group.children.add(r)
        }
        rec.layoutX = 50.0
        rec.layoutY = 50.0
        timer.start()
    }

    private fun initRecTangle(): Rectangle {

        val rec = Rectangle(snakeHigh.toDouble(), snakeWith.toDouble())
        rec.layoutX = boardHeight / 2
        rec.layoutY = boardWidth / 2
        rec.fill = Color.GREEN
        return rec
    }

    private fun initLines() {
        /*for (x in 0..boardHeight.toInt() step 10){
            val t=javafx.scene.shape.Line(0.0,x.toDouble(),boardWidth,x.toDouble())
            lineList.add(t)
        }
        for (x in 0..boardWidth.toInt() step 10){
            val t=javafx.scene.shape.Line(x.toDouble(),0.0,x.toDouble(),boardHeight)
            lineList.add(t)
        }*/
        val t = javafx.scene.shape.Line(0.0, boardHeight, boardWidth, boardHeight)
        lineList.add(t)
    }

    fun main() {
        launch()
    }
}