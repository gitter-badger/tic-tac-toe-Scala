import exception.IllegalMoveException

import scala.io.StdIn
import scala.util.{Success, Try, Failure}

/**
 * Created by pedrorijo on 11/12/14.
 */
class Game {

  private val board : Board = new Board
  final private val startingPlayer = 1

  def start = {
    println("Starting a new game")

    play(startingPlayer)
  }

  private def play(player : Int) : (Boolean, Int) = {

    println(board.prettyBoard)

    if(gameOver) {
      println("Game ended.")

      val winner = board.hasWinner
      println(if(winner._1) "Player " + winner._2 + " won!" else "Tie!")

      winner
    }
    else {
      println("Player " + player + " turn. Make your moves.")
      val pos = convertMove(StdIn.readLine())

      pos match {
        case Failure(e) => println("Invalid move: " + e.getMessage + ". Please choose a move between 'a' and 'i'"); play(player)
        case Success((line, col)) if (!allowedMove((line,col))) => println("Invalid move: " + pos + ". Please choose an empty position."); play(player)
        case Success((line, col)) => board.play(player, line, col); play(nextPlayer(player))
      }
    }
  }

  private def gameOver : Boolean = !board.hasAvailableMoves || board.hasWinner._1

  private def allowedMove(move : (Int,Int)) : Boolean =  board.isAvailable(move)

  private def nextPlayer(player: Int) = if(player == 1) 2 else 1

  private def convertMove(pos : String) : Try[(Int, Int)] = {
    Try(
      pos match {
        case "a" => (0,0)
        case "b" => (0,1)
        case "c" => (0,2)
        case "d" => (1,0)
        case "e" => (1,1)
        case "f" => (1,2)
        case "g" => (2,0)
        case "h" => (2,1)
        case "i" => (2,2)
        case  _ => throw new IllegalMoveException(pos)
      })
  }
}