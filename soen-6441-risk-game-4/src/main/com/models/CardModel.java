package com.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import com.config.CardType;
import com.config.Commands;
import com.controller.GameController;
import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;

/**
 * This class is handles the behavior of the card.
 *
 * @author Maryam
 * @author Mahmoudreza
 */
public class CardModel {


    private List<Card> cardForExchange;
	public void setNumberOfTimesCardExchanged() {
		cardExchanged += 5;
	}
	public int getCardExchanged() {
		return cardExchanged;
	}
	private static int cardExchanged = 5;
    /**
     * Gets the cards to be exchanged.
     *
     * @return the cardsToBeExchange
     */
    public List<Card> getCardsToBeExchange() {
        return cardForExchange;
    }

    /**
     * Sets the cards to be exchanged.
     *
     * @param cardsToBeExchange the cardsToBeExchange to set
     */
    public void setCardsToBeExchange(List<Card> cardsToBeExchange) {
        this.cardForExchange = cardsToBeExchange;
    }

    /**
     * Allocate cards to country 
     * @param map current Hashmap object
     * @param stackOfCards stack of cards
     * 
     */
    public void allocateCardsToCountry(Hmap map, Stack<Card> stackOfCards) {

        ArrayList<Country> countryList = map.getCountries();
        ArrayList<CardType> cardList = new ArrayList<>();
        int eachUniqueCards = countryList.size() / 3;
        
        cardList.addAll(Collections.nCopies(eachUniqueCards, CardType.valueOf("CAVALRY")));
        cardList.addAll(Collections.nCopies(eachUniqueCards, CardType.valueOf("ARTILLERY")));
        cardList.addAll(Collections.nCopies(eachUniqueCards, CardType.valueOf("INFANTRY")));

        int left = countryList.size() - cardList.size();

        if (left > 0) {
            for (int i = 0; i < left; i++) {
                cardList.add(CardType.values()[(int) (Math.random() * CardType.values().length)]);
            }
        }

        int i = 0;

        for (Country country : countryList) {
            Card card = new Card(cardList.get(i++));
            card.setCountryToWhichCardBelong(country);
            stackOfCards.push(card);
        }

        Collections.shuffle(stackOfCards);
    }

    /**
     * exchange of cards between players
     * @param cardlist list of cards
     * @param player current Player object 
     * @param idx index 
     * @param cardStack current stack of cards
     * 
     */    
    public void exchangeCards(Player player, List<Card> cardlist, Stack<Card> cardStack) {

    	Boolean isCardArmiesAssigned = false;
    	
        for (Country c : player.getAssignedCountry()) {
	    	for (Card cardChosen: cardlist) {
                if (c.getName().equalsIgnoreCase(cardChosen.getCountryToWhichCardBelong().getName())) {
            	   player.setArmies(player.getArmies() + 2);
                   isCardArmiesAssigned = true;
                   break;
                }
	    	}
	    	
	        if (isCardArmiesAssigned)
            	break;
        }

        player.setArmies(player.getArmies() + getCardExchanged());
        setNumberOfTimesCardExchanged();

        for (Card card : cardlist) {
            // Removing the exchanged cards from players hand
            player.getCardList().remove(card);
        	// Adding cards back to deck
            cardStack.push(card);
        }
    }

    /**
    * validates card for exchange
    * @param cardlist list of cards
    * @return true if cards are valid for exchange, false otherwise 
  	*
    */
    public boolean areCardsvalidForExchange(List<Card> cardlist) {

        if (cardlist.size() == 3) {
            int infantry = 0, cavalry = 0, artillery = 0;
            
            for (Card card : cardlist) {
                if (card.getCardKind().toString().equals(CardType.CAVALRY.toString())) {
                    infantry++;
                } else if (card.getCardKind().toString().equals(CardType.INFANTRY.toString())) {
                    cavalry++;
                } else if (card.getCardKind().toString().equals(CardType.ARTILLERY.toString())) {
                    artillery++;
                }
            }
            
            // if all are of different kind or all are of same 
            // kind then only, player can exchange cards for army.
            if ((infantry == 1 && cavalry == 1 && artillery == 1) 
            		|| infantry == 3 || cavalry == 3 || artillery == 3) {
                return true;
            }
        }
        
        return false;
    }   
}