package sims.actions;
/**
 * Defines the contract for progression-based systems such as skills, levels, or experience tracking.
 * <p>
 * Classes implementing this interface must provide mechanisms for:
 * </p>
 * <ul>
 *   <li>{@link #getLevel()} – Retrieving the current level of progression.</li>
 *   <li>{@link #earnXP()} – Awarding experience points (XP) that contribute to leveling up.</li>
 * </ul>
 *
 * <h3>Intended Use:</h3>
 * <p>
 * This interface is designed for game systems where entities (e.g., Sims, skills, or careers)
 * progress over time by earning experience. Implementations can define how XP is tracked,
 * how levels are calculated, and what thresholds are required for advancement.
 * </p>
 * */
public interface ProgressionInterface {

    /**
     * Retrieves the current progression level.
     * <p>
     * Implementations of this method should return the entity’s current level
     * based on its accumulated experience points (XP) or other progression criteria.
     * The level typically starts at 1 and increases as XP thresholds are met.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * ProgressionInterface skill = new SkillManager();
     * int currentLevel = skill.getLevel();
     * </pre>
     *
     * @return the current level of progression
     */
    int getLevel();

    /**
     * Awards experience points (XP) to the entity.
     * <p>
     * Implementations of this method should define how XP is accumulated
     * and how it contributes to progression. Typically, earning XP will
     * move the entity closer to reaching the next level, and once a
     * threshold is met, the level should increase.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * ProgressionInterface skill = new SkillManager();
     * skill.earnXP(); // Adds XP and may increase level if threshold is reached
     * </pre>
     *
     * <p>
     * The exact amount of XP awarded per call and the threshold for leveling
     * up should be determined by the implementing class, allowing flexibility
     * across different progression systems (e.g., skills, careers, achievements).
     * </p>
     */
    void earnXP();

}
