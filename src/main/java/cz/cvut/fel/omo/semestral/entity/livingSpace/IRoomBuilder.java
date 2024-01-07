package cz.cvut.fel.omo.semestral.entity.livingSpace;

/**
 * Represents a builder interface for rooms. Builds either a garage or a habitable room.
 */
public interface IRoomBuilder<T> {
    T build();
}
