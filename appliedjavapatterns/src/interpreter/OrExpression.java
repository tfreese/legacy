package interpreter;

/**
 * @author Thomas Freese
 */
public class OrExpression extends CompoundExpression
{
    /**
     * Creates a new {@link OrExpression} object.
     * 
     * @param expressionA {@link ComparisonExpression}
     * @param expressionB {@link ComparisonExpression}
     */
    public OrExpression(final ComparisonExpression expressionA, final ComparisonExpression expressionB)
    {
        super(expressionA, expressionB);
    }

    /**
     * @see interpreter.Expression#interpret(interpreter.Context)
     */
    @Override
    public void interpret(final Context c)
    {
        this.expressionA.interpret(c);
        this.expressionB.interpret(c);
        Boolean result = ((Boolean) c.get(this.expressionA)).booleanValue() || ((Boolean) c.get(this.expressionB)).booleanValue();

        c.addVariable(this, result);
    }
}
