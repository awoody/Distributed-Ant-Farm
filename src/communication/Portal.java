package communication;

public interface Portal 
{
	public void dispatchPackage(AbstractPackage aPackage);
	
	public void processClientPackage(AbstractPackage aPackage);
	
	public void processServerPackage(AbstractPackage aPackage);
}
