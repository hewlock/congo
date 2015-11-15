package congo;

public interface Assembler<S, T>
{
	T assemble(S source);
}
