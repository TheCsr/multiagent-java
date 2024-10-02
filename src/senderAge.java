/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thecsr
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thecsr
 */

/**
   This example shows a minimal agent that just prints "Hello     
   World!" 
   The Agent also returns its name
   and then terminates.
 */


import jade.core.*;
import jade.core.behaviours.*;

import jade.gui.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;






public class senderAge extends GuiAgent {
    
    private agentGui S1;
    private SendMessage sm;
    private ReceiveMessage rm;
    
    String receiverName;
    String dropd;
    String content;

    @Override
    protected void setup() {

        /** Registration with the DF */
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        
        sd.setType("SenderAgent");
        sd.setName(getName());
        sd.setOwnership("ExampleReceiversOfJADE");
        sd.addOntologies("SenderAgent");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
            DFService.register(this,dfd);
        } catch (FIPAException ex) {
            doDelete();
        }
        
        S1 = new agentGui();
        S1.setVisible(true);
        S1.setAgent(this);


        System.out.println("Hello World! My name is " + getLocalName());
        sm = new SendMessage();
        rm = new ReceiveMessage();
    }
    
    @Override
    protected void onGuiEvent(GuiEvent ge) {
        if(ge.getType() == 1) {
            dropd = (String)ge.getParameter(0);
            receiverName = (String)ge.getParameter(1);
            content = (String)ge.getParameter(2);
            addBehaviour(sm);
            addBehaviour(rm); 
        }
        
    }

    public class SendMessage extends OneShotBehaviour {

       @Override
       public void action() {
           System.out.println("receiverName" + receiverName);
            System.out.println("content" + content);
            System.out.println("dropd" + dropd);
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(new AID(receiverName, AID.ISLOCALNAME));
            msg.setLanguage("English");
            msg.setContent(content);
            send(msg);
            S1.setSendMessage(getLocalName()+": " + msg.getContent());
            System.out.println("****I Sent Message to::> Agent Siri *****"+"\n"+
                                "The Content of My Message is::>"+ msg.getContent());
        }
    }

    public class ReceiveMessage extends CyclicBehaviour {

       // Variable to Hold the content of the received Message
        private String Message_Performative;
        private String Message_Content;
        private String SenderName;
        private String MyPlan;
        private String myName;


        @Override
        public void action() {
            ACLMessage msg = receive();
            if(msg != null) {

                Message_Performative = ACLMessage.getPerformative(msg.getPerformative());
                Message_Content = msg.getContent();
                myName = getLocalName();
                SenderName = msg.getSender().getLocalName();
                if (msg.getContent() != null) {
                S1.receiveMsg(SenderName + ": " + msg.getContent());
                }
                System.out.println(" ****I Received a Message***" +"\n"+
                        "The Sender Name is::>"+ SenderName+"\n"+
                        "The Content of the Message is::> " + Message_Content + "\n"+
                        "My name is ::> " + myName + "\n"+
                        "::: And Performative is::> " + Message_Performative + "\n");
                System.out.println("ooooooooooooooooooooooooooooooooooooooo");
                

            }

        }
    }

    





}


