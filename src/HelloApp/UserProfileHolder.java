package HelloApp;

/**
* HelloApp/UserProfileHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Hello.idl
* Wednesday, September 25, 2019 4:04:40 PM CEST
*/

public final class UserProfileHolder implements org.omg.CORBA.portable.Streamable
{
  public HelloApp.UserProfile value = null;

  public UserProfileHolder ()
  {
  }

  public UserProfileHolder (HelloApp.UserProfile initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = HelloApp.UserProfileHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    HelloApp.UserProfileHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return HelloApp.UserProfileHelper.type ();
  }

}