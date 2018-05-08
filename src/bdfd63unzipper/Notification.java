/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdfd63unzipper;

/**
 *
 * @author Ben
 */
@FunctionalInterface
public interface Notification {
    public void handle(int percentComplete, Status status);
}
