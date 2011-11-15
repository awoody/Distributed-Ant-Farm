package rpc;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that a thread should
 * be blocked until a return value can be obtained.
 * If a method is annotated with @synchronous, then
 * any thread that enters it will be held by the
 * framework until a return value has been received.
 * When that value is received, the thread will be
 * resumed and the value returned as if this were a
 * normal method call.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Synchronous 
{
	
}
