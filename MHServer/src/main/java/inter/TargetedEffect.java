package inter;

public abstract class TargetedEffect extends Effect {

    @Override
    public void effect() {}

    public abstract void effect(Target myTarget);
}
