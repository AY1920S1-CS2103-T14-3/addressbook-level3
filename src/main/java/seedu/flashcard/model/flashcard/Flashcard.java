package seedu.flashcard.model.flashcard;

import static seedu.flashcard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.flashcard.model.tag.Tag;

/**
 * Represents a Flashcard in the flashcard list.
 * Guarantees: details are present and not null, field values are validated, immutable
 */
public class Flashcard {

    // Identity fields
    private final Word word;

    // Data fields
    private final Definition definition;
    private final Set<Tag> tags = new HashSet<>();

    public Flashcard(Word word, Definition definitions, Set<Tag> tags) {
        requireAllNonNull(word, definitions, tags);
        this.word = word;
        this.definition = definitions;
        this.tags.addAll(tags);
    }

    public Word getWord() {
        return word;
    }

    /**
     * Returns an immutable definition set, which throws {@code UnsupportedOperationException}
     * if modification is attempted
     */
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if this flashcard has the following tag.
     */
    public boolean hasTag(Tag tag) {
        return getTags().contains(tag);
    }

    /**
     * Returns true if this flashcard has any one of the tags in the given tag sets.
     */
    public boolean hasAnyTag(Set<Tag> tags) {
        for (Tag tag : tags) {
            if (getTags().contains(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the tag from this flashcard.
     */
    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    /**
     * Defines that if and only if two flashcards containing the same word can be considered as the same.
     */
    public boolean isSameFlashcard(Flashcard otherFlashcard) {
        if (otherFlashcard == this) {
            return true;
        }
        return otherFlashcard != null
                && otherFlashcard.getWord().equals(getWord());
    }

    /**
     * Returns true if both the word and the definitions and the tags are the same.
     * This is stronger than {@code isSameFlashCard}
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Flashcard)) {
            return false;
        }
        Flashcard otherFlashcard = (Flashcard) other;
        return otherFlashcard.getWord().equals(getWord())
                && otherFlashcard.getDefinition().equals(getDefinition())
                && otherFlashcard.getTags().equals(getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getWord())
                .append(" Definitions: ")
                .append(getDefinition())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, definition, tags);
    }
}
