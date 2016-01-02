/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.mvc;

/**
 *
 * @author mezestic
 */
public class Controller {
    
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    
    public void run() {
        String choice = "";
        while(!choice.equalsIgnoreCase("Q")) {
            this.view.cleanScreen();
            this.view.printMenu();
            choice = this.view.requestChoice();
            
            this.executeChoice(choice);
        }
    }

    private void executeChoice(String choice) {
        switch (choice) {
            case "":
                
                break;
            default:
                
        }
    }
    
    
}
