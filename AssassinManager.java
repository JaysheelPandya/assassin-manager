// Jaysheel Pandya
// CSE 143 DD with Xunmei Liu
// Homework 3
// The AssassinManager class helps a game administrator
// to manage of a game of assassin, keeping track of who
// is stalking whom and who killed whom.

import java.util.*;

public class AssassinManager {
   
   // reference to first killer
   AssassinNode killRingFront;   

   // reference to most recently dead player
   AssassinNode graveyardFront;
   
   // Given names must be non-empty (throws
   // IllegalArgumentException otherwise).
   // Creates a kill ring of players in order of the given names
   public AssassinManager(List<String> names) {
      if (names.isEmpty()) {
         throw new IllegalArgumentException();
      }
      for (int i = names.size() - 1; i >= 0; i--) {
         killRingFront = new AssassinNode(names.get(i), killRingFront);
      }
   }
   
   // Prints the list of players in the kill ring
   public void printKillRing() {
      AssassinNode current = killRingFront;
      while (current.next != null) {
         System.out.println("    " + current.name + " is stalking " + current.next.name);
         current = current.next;
      }
      System.out.println("    " + current.name + " is stalking " + killRingFront.name);
   }
   
   // Prints the list of players in the graveyard
   // Starts with most recently killed, ends with earliest killed
   public void printGraveyard() {
      AssassinNode current = graveyardFront;
      while (current != null) {
         System.out.println("    " + current.name + " was killed by " + current.killer);
         current = current.next;
      }
   }      
   
   // Returns true if the given name is a player in the kill ring
   // Ignores the case of the names
   public boolean killRingContains(String name) {
      AssassinNode current = killRingFront;
      while (current != null) {
         if (current.name.toLowerCase().equals(name.toLowerCase())) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
   // Returns true if the given name is a player in the graveyard
   // Ignores the case of the names
   public boolean graveyardContains(String name) {
      AssassinNode current = graveyardFront;
      while (current != null) {
         if (current.name.toLowerCase().equals(name.toLowerCase())) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
 
   // Returns true if there is only one player left standing
   public boolean gameOver() {
      return (killRingFront.next == null);
   }
   
   // If the game is over, returns the name of the player
   // left standing (the winner)
   // Returns null otherwise
   public String winner() {
      if (!gameOver()) {
         return null;
      }
      return killRingFront.name;
   }
   
   // The given name must be a player in the kill ring (throws
   // IllegalArgumentException otherwise).
   // The game must not already be over (throws 
   // IllegalStateException otherwise).
   // Kills the player in the kill ring matching the given name
   // Removes the player from the kill ring and sends them to
   // the graveyard
   // Assigns killer to the dead player
   // Ignores the case of the name
   public void kill(String name) {
      if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      if (gameOver()) {
         throw new IllegalStateException();
      }
      AssassinNode current = killRingFront;
      AssassinNode temp = graveyardFront;
      if (current.name.toLowerCase().equals(name.toLowerCase())) {
         graveyardFront = killRingFront;
         while (current.next != null) {
            current = current.next;
         }
         graveyardFront.killer = current.name;
         killRingFront = killRingFront.next;
         graveyardFront.next = temp;
      }
      else {
         while (!current.next.name.toLowerCase().equals(name.toLowerCase())) {
            current = current.next;
         }
         graveyardFront = current.next;
         graveyardFront.killer = current.name;
         current.next = current.next.next;
         graveyardFront.next = temp;
      }
   }
}