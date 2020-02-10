package Lamport;

import CorbaApp.LamportPOA;
import CorbaApp.Lamport;
import CorbaApp.LamportHelper;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class ProcLamport extends LamportPOA {
    private int id;
    private int h;
    private int[] hj=new int[3];

    //1 : req // 2: ack // 3: rel
    private int[] m=new int[3];
    private int[]neighbors;

    public ProcLamport(int id, int[] neighbors) {
        this.id=id;
        this.h=0;
        this.neighbors = neighbors;
        for (int i=1;i>3;i++){
            hj[i]=0;
            m[i]=0;
        }
        System.out.println("message de "+id+" ["+m[0]+","+m[1]+","+m[2]+"]");
        System.out.println("horloges de "+id+" ["+hj[0]+","+hj[1]+","+hj[2]+"]");
    }


    public int getId() {
        return id;
    }

    @Override
    public void sendRequest(int id2Send, int h) {
        try {
            org.omg.CORBA.Object objRef =   Server.orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            Lamport addobj = (Lamport) LamportHelper.narrow(ncRef.resolve_str("Processus"+id2Send));
            System.out.println("Send REQ to Processus "+id2Send);
            //1 : req // 2: ack // 3: rel
            addobj.receiveMessage(id,1,this.h);
        }
        catch (Exception e) {
            System.out.println("Hello Client exception: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void sendACK(int id2Send) {
        try {
            org.omg.CORBA.Object objRef =  Server.orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            Lamport addobj = (Lamport) LamportHelper.narrow(ncRef.resolve_str("Processus"+id2Send));
            System.out.println("Send ACK to Processus "+id2Send);
            this.h++;
            hj[id]=h;
            m[id]=2;
            //1 : req // 2: ack // 3: rel
            addobj.receiveMessage(id,2,this.h);
        }
        catch (Exception e) {
            System.out.println("Lamport exception: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void sendREL(int id2Send) {
        try {
            org.omg.CORBA.Object objRef =   Server.orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            Lamport addobj = (Lamport) LamportHelper.narrow(ncRef.resolve_str("Processus"+id2Send));
            System.out.println("Send REL to Processus "+id2Send);
            this.h++;
            hj[id]=h;
            m[id]=3;
            //1 : req // 2: ack // 3: rel
            addobj.receiveMessage(id,3,this.h);
        }
        catch (Exception e) {
            System.out.println("Lamport exception: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessage(int id_rec, int type, int h_rec) {
        this.h=Math.max(h_rec,this.h)+1;
        System.out.println("recoit message de "+id_rec+" de type "+type+" de h : "+h_rec);
        System.out.println("types : 1-REQ // 2-ACK // 3-REL");
        //1 : req // 2: ack // 3: rel
        switch (type) {
            case 1:
                this.h=Math.max(h_rec,h)+1;
                this.hj[id_rec]=h_rec;
                this.m[id_rec]=type;
                if (m[id]==1&&h_rec>hj[id]) break;
                else if (m[id]==1&&h_rec==hj[id]&&id<id_rec) break;
                else if (m[id]==1&&h_rec==hj[id]&&id>id_rec) {sendACK(id_rec); break;}
                else if (m[id]==1&&h_rec<hj[id]) {sendACK(id_rec); break;}
                else if (m[id]!=1) {sendACK(id_rec); break;}
                break;
            case 2:
                this.h=Math.max(h_rec,h)+1;
                this.hj[id_rec]=h_rec;
                this.m[id_rec]=type;
                for (int i=0; i<3;i++){
                    if(i!=id&&i!=id_rec)
                        if (m[i]==2||m[i]==3)
                            enterSC();
                }
                break;
            case 3:
                this.h=Math.max(h_rec,h)+1;
                this.hj[id_rec]=h_rec;
                this.m[id_rec]=type;
                break;
        }
        System.out.println("H locale : "+h);


    }
    @Override
    public void demandeSC() {
        h++;
        hj[id]=h;
        m[id]=1;
        for (int i=0;i<3;i++){
            if (i!=this.id)
                sendRequest(i,h);
        }
    }
    @Override
    public void enterSC() {
        try {
            Thread.sleep(2 * 1000);
            System.out.println("processus "+ id +" est dans la SC");
            leftSC();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void leftSC() {
        System.out.println("processus "+ id +" a sortie de la SC");
        for (int i=0;i<3;i++){
            if (i!=this.id)
                sendREL(i);
        }
        try{
        Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        System.out.println("processus "+ id +" a fini avec h="+h);
    }

    @Override
    public void shutdown() {

    }
}
