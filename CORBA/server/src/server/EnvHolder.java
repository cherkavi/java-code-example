package server;
/**
* EnvHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Env.idl
* ������, 14 ������ 2008 �. 19:03:35 EET
*/

public final class EnvHolder implements org.omg.CORBA.portable.Streamable
{
  public Env value = null;

  public EnvHolder ()
  {
  }

  public EnvHolder (Env initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = EnvHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    EnvHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return EnvHelper.type ();
  }

}
