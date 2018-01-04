package com.boradgames.bastien.schotten_totten.core.controllers;

import java.util.List;

import com.boradgames.bastien.schotten_totten.core.exceptions.EmptyDeckException;
import com.boradgames.bastien.schotten_totten.core.exceptions.HandFullException;
import com.boradgames.bastien.schotten_totten.core.exceptions.MilestoneSideMaxReachedException;
import com.boradgames.bastien.schotten_totten.core.exceptions.NoPlayerException;
import com.boradgames.bastien.schotten_totten.core.exceptions.NotYourTurnException;
import com.boradgames.bastien.schotten_totten.core.model.Card;
import com.boradgames.bastien.schotten_totten.core.model.Milestone;
import com.boradgames.bastien.schotten_totten.core.model.Player;
import com.boradgames.bastien.schotten_totten.core.model.PlayingPlayerType;

/**
 * Created by Bastien on 07/11/2017.
 */

public interface GameManagerInterface {

    public boolean playerPlays(final PlayingPlayerType p, final int indexInPlayingPlayerHand, final int milestoneIndex)
            throws MilestoneSideMaxReachedException, NotYourTurnException, EmptyDeckException, HandFullException;

    public boolean reclaimMilestone(final PlayingPlayerType p, final int milestoneIndex) throws NotYourTurnException;

    public Player getPlayingPlayer();

    public Player getPlayer(final PlayingPlayerType p);

    public boolean swapPlayers();

    public Player getWinner() throws NoPlayerException;

    public List<Milestone> getMilestones();
    
    public Card getLastPlayedCard();
}
