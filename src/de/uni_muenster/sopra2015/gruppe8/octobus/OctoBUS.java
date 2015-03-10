package de.uni_muenster.sopra2015.gruppe8.octobus;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.FrameMain;

import javax.swing.*;

/**
 * Main entry point for the OctoBUS application
 *
 * @author Michael Biech
 */
public class OctoBUS
{
	public static void main(String[] args)
	{
		// For thread safety and adhering to best practices
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new EntryPoint("TabLine"); //for debugging purposes, see EntryPoint.java for possible Values
			}
		});
		drawASCII();
	}

	public static void drawASCII()
	{
		System.out.println("               ,@@#+''''+#@+               ");
		System.out.println("             @';;;;;;;;;;;;;;@,            ");
		System.out.println("           `+;;;';:......,;';;;#           ");
		System.out.println("           +;;,..............;;;`          ");
		System.out.println("          .;;.................;;@@.        ");
		System.out.println("          .;;.................;;@;;`     # ");
		System.out.println("         '.;;............:::..;;@,';`  `;' ");
		System.out.println(" #      ;;+;;...........:::::.;;@  +;`.;;  ");
		System.out.println(" ;'    ';+';;...........,:::,.;;@   #;;;`  ");
		System.out.println(" `;;` ';` :;;............,:,..;;@    #+.   ");
		System.out.println("  .;;''   .;;...........,:::,.;;@          ");
		System.out.println("   ;;#    .;;..........,:::::,;;@;;+`      ");
		System.out.println("          .;;';;;;;;;;;;;;;;;;;;@:+;+      ");
		System.out.println("       ##'';;;;;;;;;;;;;;;;;;;;;@ @;;      ");
		System.out.println("       ;;#;;;;;;;;;;;;;;;;;;;;;;@ @;.      ");
		System.out.println("       ;; .;;;;;;;;;;;;;;;;;;;;;@ @;.      ");
		System.out.println("       ;; .;;;;;;;;;;;;;;;;;;;;;@ @;.      ");
		System.out.println("       ;; .;;;;;;;;;;;;;;;;;;;;;@ +;`      ");
		System.out.println("       ;; .+,,';;;;;;;;;;;;;;,,:@ ';.      ");
		System.out.println("       ;; .',,#;;;;;;;;;;;;;;,,+@  ;;;'@,  ");
		System.out.println("       ;' .;;;;;''''''''''';;;;;@  ;';;;;;+");
		System.out.println("   '#';;@ .;;;;;. : .,:. : ;;;;;@      .+#`");
		System.out.println(" ;;;;;;#  .;;;;;;;;;;;;;;;;;;;;;@          ");
		System.out.println(" +@'.     .@@@@@@@@@@@@@@@@@@@@@@          ");
		System.out.println("            +@@+           ;@@+            ");
		System.out.println("            +@@+           ;@@+            ");
		System.out.println("            .@@.           `@@:");
	}
}
