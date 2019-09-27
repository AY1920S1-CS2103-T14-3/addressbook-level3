package seedu.address.flashcard;

import java.util.ArrayList;

import seedu.address.flashcard.exceptions.CardNotFoundException;
import seedu.address.flashcard.exceptions.DuplicateTagException;

/**
 * The list of all flashcard list, meanwhile, holding the {@Code TagManager}
 */
public class FlashcardList {

    private ArrayList<Flashcard> flashcards;
    private TagManager tagManager;

    public FlashcardList() {
        flashcards = new ArrayList<Flashcard>();
        tagManager = new TagManager();
    }

    public ArrayList<Flashcard> getAllFlashcards() {
        return flashcards;
    }

    /**
     * Fetch the particular flashcard based on its id number
     * @param flashcardId the id number of the flashcard we are looking for
     * @return the flashcard with this id number
     * @throws CardNotFoundException if flashcard with this number was not found
     */
    public Flashcard getFlashcard(int flashcardId) throws CardNotFoundException {
        for (Flashcard flashcard : flashcards) {
            if (flashcard.getId().getIdentityNumber() == flashcardId) {
                return flashcard;
            }
        }
        throw new CardNotFoundException();
    }

    /**
     * Edit the question on a particular flashcard
     * @param flashcardId the id number of the flashcard we want to edit
     * @param newQuestion the updated question for the target flashcard
     */
    public void setFlashcard(int flashcardId, String newQuestion) {
        Flashcard editFlashcard = getFlashcard(flashcardId);
        editFlashcard.setQuestion(newQuestion);
    }

    /**
     * Edit the answer on a particular flashcard
     * @param flashcardId the id number of the flashcard we want to edit
     * @param newAnswer the updated answer for the target flashcard
     */
    public void setFlashcardAnswer(int flashcardId, String newAnswer) {
        Flashcard editFlashcard = getFlashcard(flashcardId);
        editFlashcard.setAnswer(newAnswer);
    }

    /**
     * Edit the options on a particular MCQ flash card
     * @param flashcardId the id number of the flashcard we want to edit
     * @param newOptions the updated options for the target flashcard
     * @throws RuntimeException if the card with this id is not found or the corresponding card is not an MCQ card.
     */
    public void setFlashcardOptions(int flashcardId, ArrayList<String> newOptions) throws RuntimeException {
        Flashcard editFlashcard = getFlashcard(flashcardId);
        if (!(editFlashcard instanceof McqFlashcard)) {
            throw new RuntimeException();
        }
        McqFlashcard castedEditFlashcard = (McqFlashcard) editFlashcard;
        castedEditFlashcard.setOptions(newOptions);
    }


    /**
     * Look up for a flashcard whose id number, question or answer contains this specific keyword
     * @param search the keyword we want to look up for
     * @return list of the flashcards that matches the keyword
     * @throws CardNotFoundException when no cards of this keyword was found
     */
    public ArrayList<Flashcard> findFlashcard(String search) throws CardNotFoundException {
        ArrayList<Flashcard> matchingFlashcards = new ArrayList<Flashcard>();
        for (Flashcard flashcard : flashcards) {
            if (flashcard.contains(search)) {
                matchingFlashcards.add(flashcard);
            }
        }
        if (matchingFlashcards.isEmpty()) {
            throw new CardNotFoundException();
        }
        return matchingFlashcards;
    }

    /**
     * delete the flashcard based on its id
     * @param flashcardId the id of the flashcard we want to delete
     */
    public void deleteFlashcard (int flashcardId) throws CardNotFoundException {
        Flashcard flashcardDelete = getFlashcard(flashcardId);
        for (Tag tag : flashcardDelete.getTags()) {
            tag.deleteFlashcard(flashcardId);
        }
        flashcards.remove(flashcardDelete);
    }

    /**
     * add an MCQ flash card into the list
     * @param question the question of the flashcard
     * @param options the options of the flashcard
     * @param answer the answer of this MCQ, simply "A", "B", "C", "D".
     */
    public void addFlashcard (String question, ArrayList<String> options, String answer) {
        flashcards.add(new McqFlashcard(new McqQuestion(question, options), new Answer(answer)));
    }

    /**
     * add a shortAnswer flash card into the list
     * @param question the question of the flashcard
     * @param answer the options of the flashcard
     */
    public void addFlashcard(String question, String answer) {
        flashcards.add(new ShortAnswerFlashcard(new ShortAnswerQuestion(question), new Answer(answer)));
    }

    /**
     * Give the target flashcard a tag. If this tag currently does not exist, create a new one in the TagManager
     * @param flashcardId the flashcard to be tagged
     * @param tagName the tag to be added to the flashcard
     */
    public void tagFlashcard(int flashcardId, String tagName) throws DuplicateTagException {
        if (!tagManager.hasTag(tagName)) {
            tagManager.addTag(tagName);
        }
        Flashcard targetCard = getFlashcard(flashcardId);
        Tag targetTag = tagManager.getTag(tagName);
        if (targetTag.hasCard(targetCard)) {
            throw new DuplicateTagException();
        }
        targetCard.addTag(targetTag);
        targetTag.addFlashcard(targetCard);
    }
}
