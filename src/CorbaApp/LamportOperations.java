package CorbaApp;


/**
* CorbaApp/LamportOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Processus.idl
* Sunday, 12 January 2020 15:02:16 o'clock WEST
*/

public interface LamportOperations 
{
  void sendRequest (int id, int h);
  void sendACK (int id2Send);
  void sendREL (int id2Send);
  void receiveMessage (int id_rec, int type, int h_rec);
  void enterSC ();
  void leftSC ();
  void demandeSC ();
  void shutdown ();
} // interface LamportOperations
