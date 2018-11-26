package impl;

import abstracts.Minion;
import identifiers.IdGame;
import java.util.ArrayList;

public class Game {

    private IdGame id;
    private ArrayList<Minion> minionsInPlay;
    private Player player1;
    private Player player2;

    public Game() {
      this.id = new IdGame(this);
      this.minionsInPlay = new ArrayList<Minion>();
    }

  	/**
  	* Returns value of id
  	* @return
  	*/
  	public IdGame getId() {
  		return id;
  	}

  	/**
  	* Sets new value of id
  	* @param
  	*/
  	public void setId(IdGame id) {
  		this.id = id;
  	}

  	/**
  	* Returns value of minionsInPlay
  	* @return
  	*/
  	public ArrayList<Minion> getMinionsInPlay() {
  		return minionsInPlay;
  	}

  	/**
  	* Sets new value of minionsInPlay
  	* @param
  	*/
  	public void setMinionsInPlay(ArrayList<Minion> _newMinion) {
  		this.minionsInPlay = _newMinion;
  	}

  	/**
  	* Returns value of player1
  	* @return
  	*/
  	public Player getPlayer1() {
  		return player1;
  	}

  	/**
  	* Sets new value of player1
  	* @param
  	*/
  	public void setPlayer1(Player player1) {
  		this.player1 = player1;
  	}

  	/**
  	* Returns value of player2
  	* @return
  	*/
  	public Player getPlayer2() {
  		return player2;
  	}

  	/**
  	* Sets new value of player2
  	* @param
  	*/
  	public void setPlayer2(Player player2) {
  		this.player2 = player2;
  	}
}
