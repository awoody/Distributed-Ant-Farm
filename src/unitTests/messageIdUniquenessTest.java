package unitTests;

import java.util.HashMap;
import java.util.Map;

import utilities.A;

import communication.MessageId;
import communication.NodeId;

public class messageIdUniquenessTest 
{
	private static Map<MessageId, String> testMap;
	
	public static void main(String [] args)
	{
		NodeId testId = new NodeId(4,4);
		testMap = new HashMap<MessageId, String>();
		
		
		
		int outerBounds = 1000;
		int testBounds = 100000;
		boolean isFailed = false;
		
		int[] allTrials = new int[outerBounds];
		
		for(int j = 0; j < outerBounds; j++)
		{
			for(int i = 0; i < testBounds; i++)
			{
				MessageId testMessage = new MessageId(testId);
				isFailed = !searchForEqualAndAdd(testMessage);
			
				if(isFailed)
				{	
					//A.say("Found a duplicate message id after " + i + " iterations.");
					allTrials[j] = i;
					break;
				}
			}
			
			testMap.clear();
		}
		
		double average = A.average(allTrials);
		double stdDev = A.standardDeviation(allTrials, average);
		
		A.say("The average time to failure is: " + average + " and the standard deviation is: " + stdDev);
	}
	
	
	public static boolean searchForEqualAndAdd(MessageId id)
	{		
		if(testMap.containsKey(id))
		{	
//			A.say("Failed on .contains");
//			A.say("m is: " + m + " and id is: " + id);
			return false;
		}
		
		
		//allGeneratedMessages.add(id);
		testMap.put(id, "Test");
		return true;
	}
}
