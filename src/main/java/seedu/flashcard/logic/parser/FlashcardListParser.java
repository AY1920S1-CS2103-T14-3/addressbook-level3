package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.commons.core.Messages.MESSAGE_UNKOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.flashcard.logic.commands.AddCommand;
import seedu.flashcard.logic.commands.Command;
import seedu.flashcard.logic.commands.DeleteCommand;
import seedu.flashcard.logic.commands.EditCommand;
import seedu.flashcard.logic.commands.ExitCommand;
import seedu.flashcard.logic.commands.FindCommand;
import seedu.flashcard.logic.commands.HelpCommand;
import seedu.flashcard.logic.commands.ListCardByTagCommand;
import seedu.flashcard.logic.commands.ListCommand;

import seedu.flashcard.logic.parser.exceptions.ParseException;

/**
 * The major controlling panel of command parsers.
 */
public class FlashcardListParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses the original user input and calls corresponding commands or sub-parsers.
     * @param userInput the original user input.
     * @return the command being extracted out from the user input.
     * @throws ParseException when the user input format does not fit the pattern.
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCardByTagCommand.COMMAND_WORD:
            return new ListCardByTagCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKOWN_COMMAND);
        }
    }

}
