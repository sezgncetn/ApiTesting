package apiTest.runner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import apiTest.apiActions.Actions;

public class Runner {

	private Actions actions;
	String boardId, toDoListId, doneListId, cardId;

	@Before
	public void beforeTest() {
		
		this.actions = new Actions();
	}

	@Test
	public void apiTest() {

		
		boardId = actions.createBoard("myBoard");
		doneListId = actions.createList("doneList", boardId);
		toDoListId = actions.createList("toDoList", boardId);
		actions.createCard("firstItem", toDoListId);
		actions.createCard("secondItem", toDoListId);
		cardId = actions.getRandomCardId(toDoListId);
		actions.moveCard(cardId, doneListId);
		actions.deleteCards(doneListId);
		actions.deleteCards(toDoListId);
	}

	@After
	public void afterTest() {

		actions.deleteBoard(boardId);
	}
}
