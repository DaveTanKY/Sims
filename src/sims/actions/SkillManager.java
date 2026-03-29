package sims.actions;


/**
 * Manages skill progression for a Sim by tracking levels and experience points (XP).
 * <p>
 * The {@code SkillManager} class implements {@link ProgressionInterface} to provide
 * a consistent progression system across the game. Each skill starts at level 1 and
 * gains XP through actions. Once enough XP is accumulated, the skill levels up.
 * </p>
 *
 * <h3>Core Features:</h3>
 * <ul>
 *   <li>Tracks the current skill {@code level}.</li>
 *   <li>Accumulates {@code exp} (experience points) through {@link #earnXP()}.</li>
 *   <li>Levels up when XP reaches or exceeds 100, resetting XP while incrementing the level.</li>
 * </ul>
 *
 * <h3>Progression Rules:</h3>
 * <ul>
 *   <li>Initial level is set to 1.</li>
 *   <li>Each call to {@link #earnXP()} adds 25 XP.</li>
 *   <li>When XP ≥ 100, the skill levels up and XP is reduced modulo 100.</li>
 * </ul>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 * SkillManager cookingSkill = new SkillManager();
 * System.out.println(cookingSkill.getLevel()); // 1
 *
 * cookingSkill.earnXP(); // +25 XP
 * cookingSkill.earnXP(); // +25 XP (total 50 XP)
 *
 * // After 4 calls to earnXP(), XP = 100 → level increases to 2
 * </pre>
 *
 * <p>
 * This class provides a simple yet extensible framework for skill progression,
 * allowing different skills (e.g., Hunger, Social, Fun) to share a consistent
 * leveling system while remaining flexible for future enhancements.
 * </p>
 */
public class SkillManager implements ProgressionInterface {


    /**
     * Represents the current progression level of the skill.
     * <p>
     * The {@code level} field tracks how advanced the skill is, starting
     * at 1 by default. As experience points (XP) are earned through
     * {@link #earnXP()}, the level increases once XP thresholds are met.
     * </p>
     *
     * <h3>Progression Rules:</h3>
     * <ul>
     *   <li>Initial value is set to 1 when a new {@link SkillManager} is created.</li>
     *   <li>Each level-up occurs when XP reaches or exceeds 100.</li>
     *   <li>Levels increment by 1 each time the threshold is crossed.</li>
     * </ul>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * SkillManager cookingSkill = new SkillManager();
     * System.out.println(cookingSkill.level); // 1
     * cookingSkill.earnXP(); // XP increases, level remains 1 until threshold is met
     * </pre>
     */
    int level;


    /**
     * Tracks the current experience points (XP) accumulated for this skill.
     * <p>
     * The {@code exp} field represents how close the skill is to reaching
     * the next level. Each call to {@link #earnXP()} increases this value
     * by a fixed amount (25 XP). When {@code exp} reaches or exceeds 100,
     * the skill levels up and {@code exp} is reduced modulo 100.
     * </p>
     *
     * <h3>Progression Rules:</h3>
     * <ul>
     *   <li>Initial value is 0 when a new {@link SkillManager} is created.</li>
     *   <li>Each call to {@link #earnXP()} adds 25 XP.</li>
     *   <li>When XP ≥ 100, {@code level} increments by 1 and {@code exp} resets to the remainder.</li>
     * </ul>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * SkillManager cookingSkill = new SkillManager();
     * cookingSkill.earnXP(); // exp = 25
     * cookingSkill.earnXP(); // exp = 50
     * cookingSkill.earnXP(); // exp = 75
     * cookingSkill.earnXP(); // exp = 100 → level = 2, exp = 0
     * </pre>
     */
    int exp;

    /**
     * Constructs a new {@code SkillManager} with default values.
     * <p>
     * This constructor initializes the skill progression system by
     * setting the starting {@code level} to 1. The {@code exp}
     * (experience points) field is implicitly initialized to 0,
     * meaning the skill begins at the base level with no XP earned.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * SkillManager cookingSkill = new SkillManager();
     * System.out.println(cookingSkill.getLevel()); // Outputs: 1
     * </pre>
     *
     * <p>
     * By default, all skills begin at level 1 and progress upward
     * as XP is accumulated through {@link #earnXP()}.
     * </p>
     */
    public SkillManager()
    {
        this.level = 1;
    }

    /**
     * Retrieves the current skill level.
     * <p>
     * This method returns the value of the {@code level} field,
     * representing how advanced the skill is. Levels start at 1
     * and increase as experience points (XP) are earned through
     * {@link #earnXP()}.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * SkillManager cookingSkill = new SkillManager();
     * int currentLevel = cookingSkill.getLevel(); // Returns 1 initially
     * </pre>
     *
     * @return the current progression level of the skill
     */
    public int getLevel() {
        return level;
    }


    /**
     * Awards experience points (XP) to the skill and handles level progression.
     * <p>
     * Each call to this method increases the {@code exp} field by 25 points.
     * When {@code exp} reaches or exceeds 100, the skill levels up:
     * <ul>
     *   <li>{@code level} is incremented by 1.</li>
     *   <li>{@code exp} is reduced modulo 100, carrying over any remainder.</li>
     * </ul>
     * </p>
     *
     * <h3>Progression Rules:</h3>
     * <ul>
     *   <li>Initial XP starts at 0.</li>
     *   <li>Each call adds 25 XP.</li>
     *   <li>Level increases once XP ≥ 100.</li>
     *   <li>XP resets to the remainder after leveling up.</li>
     * </ul>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * SkillManager cookingSkill = new SkillManager();
     * cookingSkill.earnXP(); // exp = 25, level = 1
     * cookingSkill.earnXP(); // exp = 50, level = 1
     * cookingSkill.earnXP(); // exp = 75, level = 1
     * cookingSkill.earnXP(); // exp = 100 → level = 2, exp = 0
     * </pre>
     *
     * <p>
     * This method provides a simple progression mechanic where skills
     * advance steadily as XP is earned through repeated actions.
     * </p>
     */
    public void earnXP()
    {
        exp += 25;
        if(exp >= 100)
        {
            exp %= 100;
            level ++;
        }
    }
}
