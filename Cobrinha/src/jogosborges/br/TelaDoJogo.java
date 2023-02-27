package jogosborges.br;

import javax.swing.JFrame;

public class TelaDoJogo extends JFrame {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TelaDoJogo(){
		
		this.add(new PainelDoJogo());
		this.setTitle("Cobrinha");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
	}
}
