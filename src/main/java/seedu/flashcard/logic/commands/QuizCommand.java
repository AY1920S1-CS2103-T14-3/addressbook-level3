package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * Command to view the MCQ choices in a flashcard. The answer will not be shown.
 */
public class QuizCommand extends Command {

    public static final String COMMAND_WORD = "quiz";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Quiz the flashcard identified by the index number used in the displayed flashcard list.\n"
        + "Only the choices with the flashcard will be shown but the answer will not.\n"
        + "The quiz command should be preceded by a flip command to answer the flashcard. \n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_FLASHCARD_INDEX = "The index you entered is invalid!";
    private final Index targetIndex;

    public QuizCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Showing a whole flashcard in the command bot, the answer will not be shown.
     * @param model list of flashcards
     * @return the execution result containing the flashcard without the answer.
     * @throws CommandException error encountered during execution of command.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_FLASHCARD_INDEX);
        }
        Flashcard cardToView = lastShownList.get(targetIndex.getZeroBased());
        List<Flashcard> quizList = new ArrayList<>();
        quizList.add(cardToView);
        model.setQuiz(quizList);
        return new CommandResult(cardToView.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof QuizCommand
            && targetIndex.equals(((QuizCommand) other).targetIndex));
    }
}
