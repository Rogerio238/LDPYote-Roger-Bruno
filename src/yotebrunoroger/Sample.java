/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yotebrunoroger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *Classe simples que representa um objeto genérico que implmenta a interface Serializable
 * @author senho
 */

public class Sample implements Serializable
{
int posArray;
int posArrayVermelha;
int posX;
int posY;
int posXAnterior;
int posYAnterior;
boolean come;
String nomePlayer;

}


