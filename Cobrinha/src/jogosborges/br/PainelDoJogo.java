 package jogosborges.br;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PainelDoJogo extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int LARGURA_TELA = 600;
	static final int ALTURA_TELA = 600;
	static final int TAMANHO_UNIDADE = 25;
	static final int UNIDADE_JOGO = (LARGURA_TELA*ALTURA_TELA)/TAMANHO_UNIDADE;
	static final int ATRASO = 75;
	final int x[] = new int[UNIDADE_JOGO];
	final int y[] = new int[UNIDADE_JOGO];
	int partesDoCorpo = 6;
	int frutasComidas;
	int frutaX;
	int frutaY;
	char direction = 'D';
	boolean andando = false;
	Timer timer;
	Random random;
	
	PainelDoJogo(){
		random = new Random();
		this.setPreferredSize(new Dimension(LARGURA_TELA,ALTURA_TELA));
		this.setBackground(Color.black);
		this.setFocusable(true); 
		this.addKeyListener(new MeuAdaptadorDeTeclado());
		comecarJogo();
	}
	
	public void comecarJogo() {
		novaFruta();
		andando = true;
		timer = new Timer(ATRASO,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		desenhar(g);
	}
	public void desenhar(Graphics g) {
		
		if(andando) {
		g.setColor(Color.blue);
		g.fillOval(frutaX, frutaY, TAMANHO_UNIDADE, TAMANHO_UNIDADE);
		
		for(int i = 0; i<partesDoCorpo;i++) {
			if(i == 0) {
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], TAMANHO_UNIDADE, TAMANHO_UNIDADE);
			}
			else {
				g.setColor(new Color(45,180,0));
				g.fillRect(x[i], y[i], TAMANHO_UNIDADE, TAMANHO_UNIDADE);
			}
		}
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Pontos: "+frutasComidas, (LARGURA_TELA - metrics.stringWidth("Pontos: "+frutasComidas))/2, getFont().getSize());
	}
		else {
			gameOver(g);
		}
}
	
	public void novaFruta() {
		frutaX = random.nextInt((int)(LARGURA_TELA/TAMANHO_UNIDADE))*TAMANHO_UNIDADE;
		frutaY = random.nextInt((int)(ALTURA_TELA/TAMANHO_UNIDADE))*TAMANHO_UNIDADE;
	}
		
	public void mover() {
		for(int i = partesDoCorpo;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'W':
				y[0] = y[0] - TAMANHO_UNIDADE;
				break;
		case 'S':
			y[0] = y[0] + TAMANHO_UNIDADE;
			break;
		case 'A':
			x[0] = x[0] - TAMANHO_UNIDADE;
			break;
		case 'D':
			x[0] = x[0] + TAMANHO_UNIDADE;
			break;	
		}
		
	}
	public void verificaFruta() {
		if((x[0] == frutaX) && (y[0] == frutaY)) {
			partesDoCorpo++;
			frutasComidas++;
			novaFruta();
		}
	}
	public void verificaColisao() {
		for(int i = partesDoCorpo;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				andando = false;
			}
		}
		if(x[0] < 0) {
			andando = false;
		}
		if(x[0] > LARGURA_TELA) {
			andando = false;
		}
		if(y[0] < 0) {
			andando = false;
		}
		if(y[0] > ALTURA_TELA) {
			andando = false;
		}
		if(!andando) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Pontos: "+frutasComidas, (LARGURA_TELA - metrics.stringWidth("Pontos: "+frutasComidas))/2, getFont().getSize() );
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Faiô!", (LARGURA_TELA - metrics2.stringWidth("Faiô!"))/2, ALTURA_TELA/2 );
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (andando) {
			mover();
			verificaFruta();
			verificaColisao();
		}
		repaint();
		
	}
	public class MeuAdaptadorDeTeclado extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'D') {
					direction = 'A';
				} 
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'A') {
					direction = 'D';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'S') {
					direction = 'W';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'W') {
					direction = 'S';
				}
				break;
			}
		}
		
	}
}
