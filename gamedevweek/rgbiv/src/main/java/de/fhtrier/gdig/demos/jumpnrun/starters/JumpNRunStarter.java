/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.fhtrier.gdig.demos.jumpnrun.starters;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;

/**
 *
 * @author roessger
 */
public class JumpNRunStarter {

    public static void main(String args[]) {

        if (args.length > 0) {
            String mode = args[0];
            args = Arrays.copyOfRange(args, 1, args.length);

            try {
                if (mode.equals("server")) {
                    JumpNRunServer.main(args);
                } else if (mode.equals("client")) {
                    JumpNRunClient.main(args);
                }
            } catch (NumberFormatException ex) {
                Logger.getLogger(JumpNRunStarter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SlickException ex) {
                Logger.getLogger(JumpNRunStarter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JumpNRunStarter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
