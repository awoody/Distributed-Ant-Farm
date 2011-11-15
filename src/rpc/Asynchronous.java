package rpc;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method should be performed
 * asynchronously, meaning that the thread executing the
 * method will not block until a return value is obtained
 * from the network.  This method call will return as soon
 * as the parameters have been sent over the output stream
 * to the destination.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Asynchronous 
{
	
}